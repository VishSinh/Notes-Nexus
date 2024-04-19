package vishsinh.live.stickynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vishsinh.live.stickynotes.entity.Notes;

import java.util.List;
import java.util.UUID;

public interface NotesRepository extends JpaRepository<Notes, UUID> {
    List<Notes> findAllByOrderByCreateDateTimeDesc();

    List<Notes> findByPublicVisibilityOrderByCreateDateTimeDesc(boolean publicVisibility);

    List<Notes> findByUserIdHashAndDeletedOrderByCreateDateTimeDesc(String userIdHash, boolean deleted);

    Notes findByNoteId(UUID noteId);
}
