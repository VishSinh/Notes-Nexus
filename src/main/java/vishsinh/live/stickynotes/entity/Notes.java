package vishsinh.live.stickynotes.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID noteId;

    @Column(name = "user_id_hash", nullable = false)
    private String userIdHash;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private boolean publicVisibility;

    @Column(nullable = false)
    private boolean updated;

    @Column(name = "updated_by" )
    private String updatedBy;

    @Column(nullable = false)
    private boolean deleted;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    @Column(name = "last_update_date_time")
    private Date lastUpdateDateTime;

    @Column(name = "delete_date_time")
    private Date deleteDateTime;


    // Constructors

    public Notes() {
    }

    public Notes(String userIdHash, String title, String description, Boolean publicVisibility) {
        this.userIdHash = userIdHash;
        this.title = title;
        this.description = description;
        this.publicVisibility = publicVisibility;
        this.updated = false;
        this.deleted = false;
        this.createDateTime = new Date();
    }

}
