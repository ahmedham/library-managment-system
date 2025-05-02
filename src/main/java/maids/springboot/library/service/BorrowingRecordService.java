package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.dto.BorrowingDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.exception.BorrowingException;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.mapper.BorrowingMapper;
import maids.springboot.library.mapper.PatronMapper;
import maids.springboot.library.repositories.BorrowingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    private final BookService bookService;

    private final PatronService patronService;

    private final BorrowingMapper borrowingMapper;

    private final BookMapper bookMapper;

    private final PatronMapper patronMapper;

    public BorrowingRecord borrowBook(Long bookId, Long patronId){
        BookDto bookDto = bookService.findById(bookId);
        PatronDto patronDto = patronService.findById(patronId);

        Book book = bookMapper.mapToBookEntity(bookDto);
        Patron patron = patronMapper.mapToPatronEntity(patronDto);


        validateBookNotAlreadyBorrowed(bookId);

        BorrowingRecord borrowingRecord = new BorrowingRecord()
                .setBook(book)
                .setPatron(patron)
                .setBorrowDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);

    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {

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

}
