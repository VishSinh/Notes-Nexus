package vishsinh.live.stickynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vishsinh.live.stickynotes.entity.ActiveSessions;

import java.util.UUID;

public interface ActiveSessionsRepository extends JpaRepository<ActiveSessions, UUID> {
    boolean existsByUserIdHash(String userIdHash);

    void deleteByUserIdHash(String userIdHash);

    boolean existsByUserIdHashAndToken(String uuid, String substring);
}
