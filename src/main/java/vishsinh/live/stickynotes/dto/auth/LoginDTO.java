package vishsinh.live.stickynotes.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDTO {

    @NotNull(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    public String username;

    @NotNull(message = "Password cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and digits")
    public String password;
}
