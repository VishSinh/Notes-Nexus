package vishsinh.live.stickynotes.dto.notes;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class FetchNotesDTO {

    @JsonProperty("user_id_hash")
    @NotNull(message = "User ID Hash is required")
    public String userIdHash;

    @Min(value = 0, message = "Page cannot be less than 0")
    public int page = 0;

    @JsonProperty("rows_per_page")
    @Min(value = 10, message = "Rows per page cannot be less than 10")
    @Max(value = 100, message = "Rows per page cannot be more than 100")
    public int rowsPerPage = 10;

    @Max(value = 2, message = "Type cannot be more than 2")
    public int type = 0; // 0 - All, 1 - Public, 2 - Private
}
