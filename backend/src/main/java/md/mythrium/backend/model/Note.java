package md.mythrium.backend.model;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.id.UUIDGenerator;

import java.util.UUID;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "url")
    private String url;

    @Column(name = "filename")
    private String filename;

    @Column(name = "type")
    private Integer type;


    @Column(name = "shared")
    private Boolean shared;

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getUrl() {
        return url;
    }

    public String getFilename() {
        return filename;
    }

    public Integer getType() {
        return type;
    }

    public Boolean getShared() {
        return shared;
    }
}
