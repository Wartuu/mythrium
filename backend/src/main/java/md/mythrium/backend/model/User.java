package md.mythrium.backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
