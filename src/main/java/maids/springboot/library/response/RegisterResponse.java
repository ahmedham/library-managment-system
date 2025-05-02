package maids.springboot.library.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
public class RegisterResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -511085427504314292L;

    private Long id;

    private String fullName;

    private String email;

}

