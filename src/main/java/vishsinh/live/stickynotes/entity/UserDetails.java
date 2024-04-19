package vishsinh.live.stickynotes.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "user_id_hash", nullable = false)
    private String userIdHash;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "profile_picture")
    private String profilePicture;


    // Constructors
    public UserDetails() {
    }

    public UserDetails(String userIdHash, String name, int age, String bio) {
        this.userIdHash = userIdHash;
        this.name = name;
        this.age = age;
        this.bio = bio;
        this.profilePicture = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
    }


}
