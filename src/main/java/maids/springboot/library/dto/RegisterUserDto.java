package maids.springboot.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserDto {

    @NotEmpty(message = "Full Name is mandatory")
    private String fullName;

    @NotEmpty(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    private String password;
}
