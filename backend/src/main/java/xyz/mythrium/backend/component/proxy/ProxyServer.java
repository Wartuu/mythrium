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
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProxyServer {

    private final Logger logger = LoggerFactory.getLogger(ProxyServer.class);

    private final ProxyConfig config;
    private ServerSocket serverSocket;
    private boolean running = false;
    private AtomicLong totalBytesTransferred = new AtomicLong(0);
    private AtomicLong bytesTransferredThisSecond = new AtomicLong(0);
    private double bytesPerSecond = 0.0;
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

        if(request == null)
            return;

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

        if(requestData.isBlank() || requestData.isEmpty() || requestData.split("\n").length == 0)
            return null;

        String[] requestLine = requestData.split("\n")[0].split(" ");

        String domain = requestLine[1];


        if (!domain.startsWith("http://") && !domain.startsWith("https://")) {
            domain = "http://" + domain;
        }

        URI uri;
        try {
            uri = new URI(domain);
        } catch (URISyntaxException ignored) {
            return null;
        }


        String host = uri.getHost();
        int port = 80;


        if(uri.getPort() != -1) {
            port = uri.getPort();
        }


        Socket request = new Socket(host, port);
        return request;
    }

    private void transferData(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();

                bytesTransferredThisSecond.addAndGet(bytesRead);
                totalBytesTransferred.addAndGet(bytesRead);
            }



        } catch(IOException ignored) {}
    }

    public void calculateNetworkUsage() {
        long lastUpdateTime = System.currentTimeMillis();
        long lastMinute = System.currentTimeMillis();
        while (running) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;

            if (elapsedTime >= 1000) {
                bytesPerSecond = bytesTransferredThisSecond.getAndSet(0);
                lastUpdateTime = currentTime;
            } else {
                try {
                    Thread.sleep(1000 - elapsedTime);
                } catch (InterruptedException ignored) {}
            }
        }
    }


    public ProxyConfig getConfig() {
        return config;
    }

    public boolean isRunning() {
        return running;
    }

    public long getTotalBytesTransferred() {
        return totalBytesTransferred.get();
    }

    public double getBytesPerSecond() {
        return bytesPerSecond;
    }

    public int getConnectionsMade() {
        return connectionsMade;
    }
}
