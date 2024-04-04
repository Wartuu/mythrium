package xyz.mythrium.backend.component.StreamTcpTest;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class Streamer {
    ServerSocket socket;

    public Streamer() throws Exception {
        socket = new ServerSocket(7001);

        new Thread(
                () -> {
                    try {
                        listen();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }



    public void listen() throws Exception {
        while (1==1) {
            System.out.println("start");
            Socket s = socket.accept();

            System.out.println("connection..");

            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            System.out.println(builder.toString());
        }


    }


}
