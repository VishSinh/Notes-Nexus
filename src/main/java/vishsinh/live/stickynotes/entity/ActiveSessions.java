package vishsinh.live.stickynotes.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "active_sessions")
public class ActiveSessions {

    @Id
    @Column(name = "user_id_hash", nullable = false)
    private String userIdHash;

    @Column(nullable = false)
    private String token;

    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    public ActiveSessions() {
    }

    public ActiveSessions(String userIdHash, String token) {
        this.userIdHash = userIdHash;
        this.token = token;
        this.createDateTime = new Date();
    }
}
