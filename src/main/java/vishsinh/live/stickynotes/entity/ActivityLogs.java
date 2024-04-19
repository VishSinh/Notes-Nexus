package vishsinh.live.stickynotes.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "activity_logs")
public class ActivityLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_log_id", nullable=false, columnDefinition = "BINARY(16)")
    private UUID activityLogId;

    @Column(name = "activity_by_user_id_hash", nullable = false)
    private String activityByUserIdHash;

    @Column(name = "activity", nullable = false)
    private String activity;

    @Column(name = "details")
    private String details;

    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;


    // Constructors

    public ActivityLogs() {}

    public ActivityLogs(String activityByUserIdHash, String activity, String details) {
        this.activityByUserIdHash = activityByUserIdHash;
        this.activity = activity;
        this.details = details;
        this.createDateTime = new Date();
    }

}
