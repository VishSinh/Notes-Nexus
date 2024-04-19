package vishsinh.live.stickynotes.dto.notes;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class DeleteNoteDTO {

    @NotNull(message = "User ID Hash cannot be empty")
    @JsonProperty("user_id_hash")
    public String userIdHash;

    @NotNull(message = "Note ID cannot be empty")
    @JsonProperty("note_id")
    public String noteId;
}
