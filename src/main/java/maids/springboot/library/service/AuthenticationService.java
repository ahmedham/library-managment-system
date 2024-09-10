package maids.springboot.library.service;

import maids.springboot.library.dto.LoginUserDto;
import maids.springboot.library.dto.RegisterUserDto;
import maids.springboot.library.entity.User;
import maids.springboot.library.exception.DuplicateRecordException;
import maids.springboot.library.exception.RecordNotFoundException;
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

        // check email exists
        if(userRepository.findByEmail(data.getEmail()).isPresent()){
            throw new DuplicateRecordException("The email provided already exists!");
        }

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
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
    }
}

