package backend.storage.api.service;

import backend.storage.api.dto.EmployeeRegistrationRequestDto;
import backend.storage.api.dto.EmployeeResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    int incrementProductivityThisMonth(int point, Long userId);
}
