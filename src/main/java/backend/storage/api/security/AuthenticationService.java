package backend.storage.api.security;

import backend.storage.api.dto.EmployeeLoginRequestDto;
import backend.storage.api.dto.EmployeeLoginResponseDto;
import backend.storage.api.dto.EmployeeRegistrationRequestDto;
import backend.storage.api.dto.EmployeeResponseDto;
import backend.storage.api.mapper.EmployeeMapper;
import backend.storage.api.model.Employee;
import backend.storage.api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public static final int DEFAULT_PRODUCTIVITY = 0;

    private final JwtUtil jwtUtil;

    private final EmployeeMapper employeeMapper;

    private final PasswordEncoder passwordEncoder;

    private final EmployeeRepository employeeRepository;

    private final AuthenticationManager authenticationManager;

    public EmployeeLoginResponseDto authenticate(EmployeeLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        EmployeeLoginResponseDto employeeLoginResponseDto = new EmployeeLoginResponseDto();
        employeeLoginResponseDto.setToken(token);
        return employeeLoginResponseDto;
    }

    public EmployeeResponseDto register(EmployeeRegistrationRequestDto requestDto) {
        if (employeeRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Employee already registered");
        }
        Employee employee = new Employee();
        employee.setName(requestDto.getName());
        employee.setEmail(requestDto.getEmail());
        employee.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        employee.setRole(requestDto.getRole());
        employee.setPresent(true);
        employee.setProductivityThisMonth(DEFAULT_PRODUCTIVITY);
        employeeRepository.save(employee);
        return employeeMapper.toResponseFromEntity(employee);
    }
}
