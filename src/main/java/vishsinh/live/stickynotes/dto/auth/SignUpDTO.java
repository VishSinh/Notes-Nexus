package vishsinh.live.stickynotes.dto.auth;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class SignUpDTO {

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    public String email;

    @NotNull(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    public String username;

    @NotNull(message = "Password cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and digits")
    public String password;
}
