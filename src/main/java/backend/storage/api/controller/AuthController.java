package backend.storage.api.controller;

import backend.storage.api.dto.EmployeeLoginRequestDto;
import backend.storage.api.dto.EmployeeLoginResponseDto;
import backend.storage.api.dto.EmployeeRegistrationRequestDto;
import backend.storage.api.dto.EmployeeResponseDto;
import backend.storage.api.security.AuthenticationService;
import backend.storage.api.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final EmployeeService employeeService;

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public EmployeeResponseDto register(@RequestBody @Valid EmployeeRegistrationRequestDto requestDto) {
        return authenticationService.register(requestDto);
    }

    @PostMapping("/login")
    public EmployeeLoginResponseDto login(@RequestBody EmployeeLoginRequestDto loginRequestDto) {
        return authenticationService.authenticate(loginRequestDto);
    }
}
