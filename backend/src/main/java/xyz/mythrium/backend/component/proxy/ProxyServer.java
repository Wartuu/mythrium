package xyz.mythrium.backend.component.proxy;


// public free to use VPN/proxy-server for everyone
// it will have connection limit for everyone instead of trusted (premium) users
// every Exception will be ignored for even more privacy reasons
// proxy is given as is without any proof of working correctly (for now)

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProxyServer {

    private final Logger logger = LoggerFactory.getLogger(ProxyServer.class);

    private final ProxyConfig config;
    private ServerSocket serverSocket;
    private boolean running = false;
    private AtomicLong totalBytesTransferred = new AtomicLong(0);
    private AtomicLong bytesTransferredThisSecond = new AtomicLong(0);
    public double bytesPerSecond = 0.0;
    private long lastUpdateTime = System.currentTimeMillis();

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
            new Thread(this::calculateNetworkUsage).start();
        }
    }

    public void proxyWorker() {
        try {

            // testing proxy can be done with browser:
            while(running) {
                Socket socket = serverSocket.accept();

                new Thread(
                        () -> {
                            try {
                                handleConnection(socket);
                            } catch (IOException ignored) {}
                        }
                ).start();

                connectionsMade++;

                if(connectionsMade >= config.getDailyLimit())
                    running = false;
            }



        } catch (IOException ignored) {}
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

        if(requestData.isEmpty() || requestData.isBlank()) {
            writer.println("HTTP/1.1 500 Server error or wrong url");
            writer.println("Proxy-Agent: Mythrium/1.0");
            writer.println();
            writer.flush();
            writer.close();
            socket.close();
            return;
        }
        Socket request = getRequestSocket(requestData);

        writer.println("HTTP/1.1 200 Connection established");
        writer.println("Proxy-Agent: Mythrium/1.0");
        writer.println();
        writer.flush();

        Thread clientToServer = new Thread(() -> {
            try {
                transferData(socket.getInputStream(), request.getOutputStream());
            } catch (IOException ignored) {}
        });

        Thread serverToClient = new Thread(() -> {
            try {
                transferData(request.getInputStream(), socket.getOutputStream());
            } catch (IOException ignored) {}
        });


        clientToServer.start();
        serverToClient.start();

    }

    private static Socket getRequestSocket(String requestData) throws IOException {
        String[] requestLine = requestData.split("\n")[0].split(" ");


        String[] hostAndPort;

        if(requestLine[1].startsWith("http://")) {

            String[] data = requestLine[1].substring(7).split(":");
            hostAndPort = new String[]{
                    data[0],
                    data[1].split("/")[0]
            };

        } else if(requestLine[1].startsWith("https://")) {
            String[] data = requestLine[1].substring(8).split(":");
            hostAndPort = new String[]{
                    data[0],
                    data[1].split("/")[0]
            };
        } else hostAndPort = requestLine[1].split(":");


        String host = hostAndPort[0];
        int port = Integer.parseInt(hostAndPort[1]);


        Socket request = new Socket(host, port);
        return request;
    }

    private void transferData(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;

            long bytesTransferred = 0;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();

                bytesTransferred += bytesRead;
            }

            totalBytesTransferred.addAndGet(bytesTransferred);
            bytesTransferredThisSecond.addAndGet(bytesTransferred);

        } catch(IOException ignored) {}
    }

    public void calculateNetworkUsage() {
        while (running) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;

            if(elapsedTime >= 1000) {
                bytesPerSecond = bytesTransferredThisSecond.get() / (elapsedTime / 1000.0);
                bytesTransferredThisSecond.set(0);
                lastUpdateTime = currentTime;
            } else {
                try {
                    Thread.sleep(1000 - elapsedTime);
                } catch (Exception ignored) {}
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public int getConnectionsMade() {
        return connectionsMade;
    }

    public long getTotalBytesTransferred() {
        return totalBytesTransferred.get();
    }

    public double getBytesPerSecond() {
        return bytesPerSecond;
    }
}
