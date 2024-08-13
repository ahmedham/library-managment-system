package maids.springboot.library.controller;

import jakarta.validation.Valid;
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
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        RegisterResponse registerResponse = new RegisterResponse()
                .setId(registeredUser.getId())
                .setFullName(registeredUser.getFullName())
                .setEmail(registeredUser.getEmail());


        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}

