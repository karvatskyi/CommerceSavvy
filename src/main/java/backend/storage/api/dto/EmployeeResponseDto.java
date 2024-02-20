package backend.storage.api.dto;

import backend.storage.api.model.Role;
import lombok.Data;
import java.util.Set;

@Data
public class EmployeeResponseDto {
    private String name;
    private String email;
    private boolean isPresent;
    private Set<Role> role;
}
