package backend.storage.api.service.impl;

import backend.storage.api.dto.EmployeeRegistrationRequestDto;
import backend.storage.api.dto.EmployeeResponseDto;
import backend.storage.api.mapper.EmployeeMapper;
import backend.storage.api.model.Employee;
import backend.storage.api.repository.EmployeeRepository;
import backend.storage.api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Override
    public int incrementProductivityThisMonth(int point, Long userId) {
        Employee employee = employeeRepository.getReferenceById(userId);
        employee.setProductivityThisMonth(employee.getProductivityThisMonth() + point);
        return employee.getProductivityThisMonth();
    }

}
