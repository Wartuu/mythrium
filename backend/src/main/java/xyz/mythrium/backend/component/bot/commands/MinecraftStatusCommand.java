package xyz.mythrium.backend.component.bot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.util.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.mythrium.backend.json.input.discord.MinecraftStatusInput;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Component
public class MinecraftStatusCommand implements Command {
    @Override
    public String getName() {
        return "minecraft";
    }

    // ! THIS WILL ONLY WORK FOR SERVERS >= 1.17
    private MinecraftStatusInput getServerData() {
        long ping;

        try (Socket clientSocket = new Socket()) {
            // Connect to the server
            long startTime = System.currentTimeMillis();
            clientSocket.connect(new InetSocketAddress("mc.mythrium.xyz", 25565), 5000);
            ping = System.currentTimeMillis() - startTime;

            // Prepare the handshake
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream payload = new DataOutputStream(baos);
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            payload.writeByte(0x00);               // handshake packet
            sendVarInt(payload, 0x00);             // protocol version
            sendVarInt(payload, "127.0.0.1".length()); // address length
            payload.writeBytes("127.0.0.1");           // address
            payload.writeShort(25565);              // port
            sendVarInt(payload, 0x01);             // state

            sendVarInt(dos, baos.size());          // send payload size
            dos.write(baos.toByteArray());         // send payload
            dos.writeByte(0x01);                   // size
            dos.writeByte(0x00);                   // ping packet

            // Get the response
            int totalLength = recvVarInt(dis);     // total response size
            int packetID = recvVarInt(dis);        // packet ID
            int jsonLength = recvVarInt(dis);      // JSON response size
            byte[] rawData = new byte[jsonLength]; // store JSON data
            dis.readFully(rawData);                // fill byte array with JSON data

            String jsonString = new String(rawData);

            ObjectMapper mapper = new ObjectMapper();
            MinecraftStatusInput serverData = mapper.readValue(jsonString, MinecraftStatusInput.class);

            serverData.setPing(ping);
            return serverData;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void sendVarInt(DataOutputStream out, int paramInt) throws IOException {
        while ((paramInt & -128) != 0) {
            out.writeByte(paramInt & 127 | 128);
            paramInt >>>= 7;
        }
        out.writeByte(paramInt);
    }

    private static int recvVarInt(DataInputStream in) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = in.readByte();
            int value = (read & 0x7F);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) throw new IOException("VarInt is too big");
        } while ((read & 0x80) != 0);
        return result;
    }

    @Override
    public InteractionApplicationCommandCallbackReplyMono execute(ApplicationCommandInteractionEvent event) {
        MinecraftStatusInput statusInput = getServerData();
        EmbedCreateSpec embed;
        if(statusInput == null) {
            embed = EmbedCreateSpec.builder()
                    .title("Minecraft server status")
                    .author("mc.mythrium.xyz", "https://mythrium.xyz", null)
                    .color(Color.of(0xff6666))
                    .addField("Status", "offline", false)
                    .build();

            return event.reply().withEmbeds(embed);
        }

        embed = EmbedCreateSpec.builder()
                .title("Minecraft server status")
                .author("mc.mythrium.xyz", "https://mythrium.xyz", null)
                .color(Color.of(0x82d782))
                .addField("Status", "online", true)
                .addField("Ping", statusInput.getPing() + "ms", true)
                .addField("", "", false)
                .addField("Version", statusInput.getVersion().getName() + " (" + statusInput.getVersion().getProtocol() + ")" , true)
                .addField("Players online", statusInput.getPlayers().getOnline() + " / " + statusInput.getPlayers().getMax(), true)
                .footer("DiscordMythrium 0.1", null)
                .timestamp(Instant.now())
                .build();

        return event.reply().withEmbeds(embed);

    }

    @Override
    public ApplicationCommandRequest initializeCommand() {
        return ApplicationCommandRequest.builder()
                .name(getName())
                .description("minecraft server info")
                .build();
    }
}
