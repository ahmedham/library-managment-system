package maids.springboot.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import maids.springboot.library.base.BaseDto;

@Setter
@Getter
@Accessors(chain = true)
public class PatronDto extends BaseDto<Long> {

    @NotEmpty(message = "Title is mandatory")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotEmpty(message= "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Phone is mandatory")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phone;
}
