package maids.springboot.library.validators;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.exception.DeleteBookException;
import maids.springboot.library.repositories.BorrowingRecordRepository;
import maids.springboot.library.service.BorrowingRecordService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BorrowingRecordRepository borrowingRecordRepository;

    public void validateBookNotBorrowedBeforeDelete(Long bookId) {
        if (borrowingRecordRepository.existsByBookId(bookId)) {
            throw new DeleteBookException("Cannot delete book as it is being borrowed.");
        }
    }


}
