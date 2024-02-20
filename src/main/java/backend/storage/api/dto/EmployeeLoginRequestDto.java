package backend.storage.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeLoginRequestDto {
    @NotNull
    @Email(message = "Email invalid")
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;
}
