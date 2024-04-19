package vishsinh.live.stickynotes.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FetchActivityLogsDTO {

    @JsonProperty("user_id_hash")
    @NotNull(message = "User ID Hash is required")
    public String userIdHash;

    @Min(value = 0, message = "Page cannot be less than 0")
    public int page = 0;

    @JsonProperty("rows_per_page")
    @Min(value = 10, message = "Rows per page cannot be less than 10")
    @Max(value = 100, message = "Rows per page cannot be more than 100")
    public int rowsPerPage = 10;
}
