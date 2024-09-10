package maids.springboot.library.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class LoginResponse {
    private String token;

    private long expiresIn;

}

