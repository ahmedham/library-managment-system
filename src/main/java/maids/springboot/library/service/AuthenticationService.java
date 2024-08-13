package maids.springboot.library.service;

import maids.springboot.library.dto.LoginUserDto;
import maids.springboot.library.dto.RegisterUserDto;
import maids.springboot.library.entity.User;
import maids.springboot.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto data) {
        User user = new User()
                .setFullName(data.getFullName())
                .setEmail(data.getEmail())
                .setPassword(passwordEncoder.encode(data.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto data) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getEmail(),
                        data.getPassword()
                )
        );
        return userRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

