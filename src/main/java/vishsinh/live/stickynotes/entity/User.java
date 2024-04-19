package vishsinh.live.stickynotes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId = UUID.randomUUID();

    @Column(name = "user_id_hash")
    private String userIdHash;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "admin", nullable = false)
    private boolean admin;

    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    // Constructors
    public User() {
    }

    public User(String password, String email, String username, boolean admin) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.admin = admin;
        this.createDateTime = new Date();
    }
}
