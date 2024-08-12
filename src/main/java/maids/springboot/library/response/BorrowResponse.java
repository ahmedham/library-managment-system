package maids.springboot.library.response;

import org.springframework.stereotype.Component;

public class BorrowResponse{

    private String message;

    public BorrowResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public BorrowResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
