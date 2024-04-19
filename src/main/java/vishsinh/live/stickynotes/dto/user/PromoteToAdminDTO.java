package vishsinh.live.stickynotes.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class PromoteToAdminDTO {

    @JsonProperty("user_id_hash")
    @NotNull(message = "User ID Hash is required")
    public String userIdHash;

    @JsonProperty("to_promote_user_id_hash")
    @NotNull(message = "User ID Hash to promote is required")
    public String toPromoteUserIdHash;
}
