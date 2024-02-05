package xyz.mythrium.backend.dto;

public class OpenGraphDTO {
    public final String title;
    public final String description;
    public final String type;
    public final String image;
    public final String url;


    public OpenGraphDTO(String title, String description, String type, String image, String url) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.image = image;
        this.url = url;
    }



}
