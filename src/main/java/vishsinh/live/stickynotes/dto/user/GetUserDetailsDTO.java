package vishsinh.live.stickynotes.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class GetUserDetailsDTO {

    @NotNull(message = "User ID Hash cannot be empty")
    @JsonProperty("user_id_hash")
    public String userIdHash;

    @JsonProperty("to_find_user_id_hash")
    public String toFindUserIdHash;
}
