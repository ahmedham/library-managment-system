package maids.springboot.library.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class BorrowResponse  implements Serializable {

    @Serial
    private static final long serialVersionUID = 6125040778720897116L;

    private String message;

    public BorrowResponse(String message) {
        this.message = message;
    }
}
