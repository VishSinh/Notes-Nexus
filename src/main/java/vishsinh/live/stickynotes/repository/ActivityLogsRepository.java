package vishsinh.live.stickynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vishsinh.live.stickynotes.entity.ActivityLogs;

import java.util.UUID;

public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, UUID> {
}
