package vishsinh.live.stickynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vishsinh.live.stickynotes.entity.UserDetails;

import java.util.UUID;

public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID>{
    UserDetails findByUserIdHash(String userIdHash);

    boolean existsByUserIdHash(String userIdHash);
}
