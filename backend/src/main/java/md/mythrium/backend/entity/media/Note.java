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
    private int viewCount;

    @Column(name = "content", nullable = false)
    private String content;


    public Long getId() {
        return id;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isBurnAfterRead() {
        return isBurnAfterRead;
    }

    public void setBurnAfterRead(boolean burnAfterRead) {
        isBurnAfterRead = burnAfterRead;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
