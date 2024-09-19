package xyz.mythrium.backend.json.input.discord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MinecraftStatusInput {
    @JsonProperty("favicon")
    private String favicon;

    @JsonProperty("enforcesSecureChat")
    private boolean secureChat;

    @JsonProperty("players")
    private Players players;

    @JsonProperty("description")
    private Description description;

    @JsonProperty("version")
    private Version version;

    private long ping;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Description {
        @JsonProperty("text")
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Players {

        @JsonProperty("max")
        private int max;

        @JsonProperty("online")
        private int online;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Version {
        @JsonProperty("name")
        private String name;

        @JsonProperty("protocol")
        private int protocol;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getProtocol() {
            return protocol;
        }

        public void setProtocol(int protocol) {
            this.protocol = protocol;
        }
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public boolean isSecureChat() {
        return secureChat;
    }

    public void setSecureChat(boolean secureChat) {
        this.secureChat = secureChat;
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public long getPing() {
        return ping;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }
}
