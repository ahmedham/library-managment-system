package maids.springboot.library.service;

import maids.springboot.library.dto.BorrowingDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.repositories.BookRepository;
import maids.springboot.library.repositories.BorrowingRecordRepository;
import maids.springboot.library.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public BorrowingRecord borrowBook(Long bookId, Long patronId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RecordNotFoundException("Book not found"));
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new RecordNotFoundException("Patron not found"));

        if(borrowingRecordRepository.BookAlreadyBorrowed(bookId) != null){
            throw new RuntimeException("The book is already borrowed");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord()
                .setBook(book)
                .setPatron(patron)
                .setBorrowDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);

    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {

        List<BorrowingRecord> activeRecords = borrowingRecordRepository
                .findActiveBorrowingRecords(bookId, patronId);

        if (activeRecords.isEmpty()) {
            throw new RuntimeException("No active borrowing records found");
        }

        BorrowingRecord borrowingRecord = activeRecords.stream()
                .max(Comparator.comparing(BorrowingRecord::getBorrowDate))
                .orElseThrow(() -> new RuntimeException("No valid borrowing record found"));

        borrowingRecord.setReturnDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord insert(BorrowingDto dto) {

        return borrowingRecordRepository.save(convertToEntity(dto));
    }

    public Boolean existsByBookId(Long id){
        return borrowingRecordRepository.existsByBookId(id);
    }

    public Boolean existsByPatronId(Long id){
        return borrowingRecordRepository.existsByPatronId(id);
    }


    protected BorrowingRecord convertToEntity(BorrowingDto dto){

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(dto.getBook());
        borrowingRecord.setPatron(dto.getPatron());
        borrowingRecord.setBorrowDate(dto.getBorrowDate());

        return borrowingRecord;

    }

}
