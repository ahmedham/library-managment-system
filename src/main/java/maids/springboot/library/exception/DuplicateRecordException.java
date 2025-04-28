package maids.springboot.library.exception;

import java.io.Serial;

public class DuplicateRecordException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2936115812354926441L;

    public DuplicateRecordException() {
        super();
    }

    public DuplicateRecordException(String message) {
        super(message);
    }


}
