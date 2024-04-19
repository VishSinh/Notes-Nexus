package vishsinh.live.stickynotes.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class SaveUserDetailsDTO {

    @JsonProperty("user_id_hash")
    @NotNull(message = "User ID Hash cannot be empty")
    public String userIdHash;

    @NotNull(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot be more than 50 characters")
    public String name;

    @NotNull(message = "Age cannot be empty")
    @Min(value = 12, message = "Age cannot be less than 12")
    public int age;

    @NotNull(message = "Bio cannot be empty")
    @Size(max = 200, message = "Bio cannot be more than 200 characters")
    public String bio;

}
