package maids.springboot.library.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowResponse{

    private String message;

    public BorrowResponse(String message) {
        this.message = message;
    }
}
