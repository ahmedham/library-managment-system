package maids.springboot.library.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -3066606010306673924L;

    private String token;

    private long expiresIn;

}

