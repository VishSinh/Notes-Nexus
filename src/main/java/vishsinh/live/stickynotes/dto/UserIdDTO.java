package vishsinh.live.stickynotes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UserIdDTO {

    @NotNull(message = "User ID Hash cannot be empty")
    @JsonProperty("user_id_hash")
    public String userIdHash;
}
