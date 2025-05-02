package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.LoginUserDto;
import maids.springboot.library.dto.RegisterUserDto;
import maids.springboot.library.entity.User;
import maids.springboot.library.exception.DuplicateRecordException;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.repositories.UserRepository;
import maids.springboot.library.response.LoginResponse;
import maids.springboot.library.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public RegisterResponse signup(RegisterUserDto data) {

        // check email exists
        if(userRepository.findByEmail(data.getEmail()).isPresent()){
            throw new DuplicateRecordException("The email provided already exists!");
        }

        User user = new User()
                .setFullName(data.getFullName())
                .setEmail(data.getEmail())
                .setPassword(passwordEncoder.encode(data.getPassword()));

        User registeredUser = userRepository.save(user);

        return new RegisterResponse()
                .setId(registeredUser.getId())
                .setFullName(registeredUser.getFullName())
                .setEmail(registeredUser.getEmail());
    }

    public LoginResponse authenticate(LoginUserDto data) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getEmail(),
                        data.getPassword()
                )
        );

        User loggedInUser =  userRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new RecordNotFoundException("User not found"));


        String jwtToken = jwtService.generateToken(loggedInUser);

        return new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());

    }
}

