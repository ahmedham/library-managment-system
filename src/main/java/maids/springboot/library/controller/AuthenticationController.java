package maids.springboot.library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.LoginUserDto;
import maids.springboot.library.dto.RegisterUserDto;
import maids.springboot.library.entity.User;
import maids.springboot.library.response.LoginResponse;
import maids.springboot.library.response.RegisterResponse;
import maids.springboot.library.service.AuthenticationService;
import maids.springboot.library.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        RegisterResponse registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        LoginResponse authenticatedUser = authenticationService.authenticate(loginUserDto);

        return ResponseEntity.ok(authenticatedUser);
    }
}

