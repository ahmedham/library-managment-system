package maids.springboot.library.exception;

import java.io.Serial;

public class DeleteBookException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8958890897394512878L;

    public DeleteBookException() {
        super();
    }

    public DeleteBookException(String message) {
        super(message);
    }
}
