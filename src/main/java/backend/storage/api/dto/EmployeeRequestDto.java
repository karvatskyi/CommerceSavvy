package backend.storage.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String email;
    private String password;
    private Integer productivityThisMonth;
    private Boolean isPresent;
}
