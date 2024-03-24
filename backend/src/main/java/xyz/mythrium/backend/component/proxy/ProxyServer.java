package xyz.mythrium.backend.component.proxy;


// public free to use VPN/proxy-server for everyone
// it will have connection limit for everyone instead of trusted (premium) users

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ProxyServer {

    private final Logger logger = LoggerFactory.getLogger(ProxyServer.class);

    private final ProxyConfig config;
    private ServerSocket serverSocket;
    private boolean running = false;

    private int connectionsMade = 0;

    @Autowired
    public ProxyServer(ProxyConfig config) throws IOException {
        this.config = config;

        if(config.isEnabled()) {
            this.serverSocket = new ServerSocket(config.getPort());
            logger.info("custom proxy started at: 127.0.0.1:" + config.getPort());
            logger.info("custom proxy daily limit set at: " + config.getDailyLimit() + " connections");

            running = true;
            new Thread(this::proxyWorker).start();
        }
    }

    public void proxyWorker() {
        try {

            // testing proxy can be done with browser or using curl:
            // curl -x http://127.0.0.1:7000 google.com
            while(running) {
                Socket socket = serverSocket.accept();

                new Thread(
                        () -> {
                            try {
                                handleConnection(socket);
                            } catch (IOException e) {
                                logger.error(e.getLocalizedMessage());
                            }
                        }
                ).start();

                connectionsMade++;

                if(connectionsMade >= config.getDailyLimit())
                    running = false;
            }



        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void handleConnection(Socket socket) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {

            if(line.isEmpty() || line.isBlank())
                break;

            builder.append(line).append("\n");
        }

        String requestData = builder.toString();
        String[] requestLine = requestData.split("\n")[0].split(" ");
        String[] connection = requestLine[1].split(":");
        String host = connection[0];
        int port = Integer.parseInt(connection[1]);

        Socket request = new Socket(host, port);

        writer.println("HTTP/1.1 200 Connection established");
        writer.println("Proxy-Agent: Mythrium/1.0");
        writer.println();
        writer.flush();

        Thread clientToServer = new Thread(() -> {
            try {
                transferData(socket.getInputStream(), request.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread serverToClient = new Thread(() -> {
            try {
                transferData(request.getInputStream(), socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        clientToServer.start();
        serverToClient.start();

    }

    private void transferData(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }


        } catch(IOException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean isRunning() {
        return running;
    }

    public int getConnectionsMade() {
        return connectionsMade;
    }
}
