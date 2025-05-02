package maids.springboot.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class RegisterUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 281871415606058758L;

    @NotEmpty(message = "Full Name is mandatory")
    private String fullName;

    @NotEmpty(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    private String password;
}
