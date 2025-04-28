package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BorrowingDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.exception.BorrowingException;
import maids.springboot.library.mapper.BorrowingMapper;
import maids.springboot.library.repositories.BorrowingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    private final BookService bookService;

    private final PatronService patronService;

    private final BorrowingMapper borrowingMapper;

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public BorrowingRecord borrowBook(Long bookId, Long patronId){
        Book book = bookService.findById(bookId);
        Patron patron = patronService.findById(patronId);

        validateBookNotAlreadyBorrowed(bookId);

        BorrowingRecord borrowingRecord = new BorrowingRecord()
                .setBook(book)
                .setPatron(patron)
                .setBorrowDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);

    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        // Assuming findLatestActiveBorrowingRecord is a method that fetches only the most recent active record
        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findLatestActiveBorrowingRecord(bookId, patronId)
                .orElseThrow(() -> new BorrowingException("No valid borrowing record found"));

        borrowingRecord.setReturnDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }


    @Transactional
    public BorrowingRecord insert(BorrowingDto dto) {
        return borrowingRecordRepository.save(borrowingMapper.mapToBorrowingEntity(dto));
    }

    private void validateBookNotAlreadyBorrowed(Long bookId) {
        if (borrowingRecordRepository.BookAlreadyBorrowed(bookId) != null) {
            throw new BorrowingException("The book is already borrowed");
        }
    }


    public Boolean existsByBookId(Long id){
        return borrowingRecordRepository.existsByBookId(id);
    }

    public Boolean existsByPatronId(Long id){
        return borrowingRecordRepository.existsByPatronId(id);
    }

}
