package vishsinh.live.stickynotes.dto.notes;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CreateNoteDTO {

    @NotNull(message = "User ID Hash is required")
    @JsonProperty("user_id_hash")
    public String userIdHash;

    @NotNull(message = "Title is required")
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    public String title;

    @NotNull(message = "Description is required")
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    public String description;

    @NotNull(message = "Public Visibility is required")
    @JsonProperty("public_visibility")
    public boolean publicVisibility;
}
