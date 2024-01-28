package md.mythrium.backend.entity.media;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private Long author_id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "password")
    private String password;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "burn_after_read", nullable = false)
    private boolean isBurnAfterRead;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "view_count", nullable = false)
    private int view_count;

}
