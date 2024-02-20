package backend.storage.api.dto;

import backend.storage.api.model.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class EmployeeRegistrationRequestDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String repeatPassword;
    @NotEmpty
    private String name;
    @NotNull
    private Set<Role> role;
}
