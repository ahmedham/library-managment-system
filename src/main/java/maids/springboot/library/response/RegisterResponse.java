package maids.springboot.library.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class RegisterResponse {

    private Long id;

    private String fullName;

    private String email;

}

