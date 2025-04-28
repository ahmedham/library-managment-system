package maids.springboot.library.exception;

import java.io.Serial;

public class DeletePatronException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8958890897394512878L;

    public DeletePatronException() {
        super();
    }

    public DeletePatronException(String message) {
        super(message);
    }
}
