package maids.springboot.library.exception;

import java.io.Serial;

public class BorrowingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8958890897394512878L;

    public BorrowingException() {
        super();
    }

    public BorrowingException(String message) {
        super(message);
    }
}
