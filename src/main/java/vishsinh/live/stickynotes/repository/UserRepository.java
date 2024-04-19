package vishsinh.live.stickynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vishsinh.live.stickynotes.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{


    User findByUsername(String username);

    User findByUserIdHash(String userIdHash);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
