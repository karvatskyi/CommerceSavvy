package backend.storage.api.controller;

import backend.storage.api.dto.EmployeeRequestDto;
import backend.storage.api.dto.EmployeeResponseDto;
import backend.storage.api.mapper.EmployeeMapper;
import backend.storage.api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @PostMapping("/save")
    public EmployeeResponseDto save(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeMapper.toResponseFromEntity(
                employeeRepository.save(employeeMapper.toEntityFromRequest(employeeRequestDto)));
    }

    @GetMapping("/all")
    public List<EmployeeResponseDto> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponseFromEntity)
                .toList();
    }

    @GetMapping("/online/employee")
    public List<EmployeeResponseDto> getAllPresentEmployee() {
        return employeeRepository.findAllIsPresentIsTrue()
                .stream()
                .map(employeeMapper::toResponseFromEntity)
                .toList();
    }
}
