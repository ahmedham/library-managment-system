package maids.springboot.library.validators;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.exception.DeleteBookException;
import maids.springboot.library.exception.DeletePatronException;
import maids.springboot.library.repositories.BorrowingRecordRepository;
import maids.springboot.library.service.BorrowingRecordService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatronValidator {

    private final BorrowingRecordRepository borrowingRecordRepository;

    public void validatePatronNotBorrowedBeforeDelete(Long patronId) {
        if (borrowingRecordRepository.existsByPatronId(patronId)) {
            throw new DeletePatronException("Cannot delete patron as they have active borrowing records.");
        }
    }


}
