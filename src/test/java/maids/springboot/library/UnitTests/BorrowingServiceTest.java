package maids.springboot.library.UnitTests;

import maids.springboot.library.dto.BookDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.mapper.BorrowingMapper;
import maids.springboot.library.mapper.PatronMapper;
import maids.springboot.library.repositories.BorrowingRecordRepository;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.BorrowingRecordService;
import maids.springboot.library.service.PatronService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class BorrowingServiceTest {


    @InjectMocks
    private BorrowingRecordService borrowingService;

    @Mock
    private PatronService patronService;

    @Mock
    private BookService bookService;

    @Mock
    private BorrowingRecordRepository borrowingRepository;

    @Mock
    private BorrowingMapper borrowingMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private PatronMapper patronMapper;

    @Test
    public void borrowBook_saveARecord() {

        // Arrange
        long bookId = 1L;
        long patronId = 1L;

        BookDto bookDto = new BookDto();
        PatronDto patronDto = new PatronDto();

        Book book = new Book();
        Patron patron = new Patron();

        BorrowingRecord saved = new BorrowingRecord();
        saved.setId(10L);
        saved.setBook(book);
        saved.setPatron(patron);
        saved.setBorrowDate(LocalDate.now());

        // Mock
        when(bookService.findById(bookId)).thenReturn(bookDto);
        when(patronService.findById(patronId)).thenReturn(patronDto);

        when(bookMapper.mapToBookEntity(any(BookDto.class))).thenReturn(book);
        when(patronMapper.mapToPatronEntity(any(PatronDto.class))).thenReturn(patron);

        when(borrowingRepository.save(any(BorrowingRecord.class))).thenReturn(saved);

        // Act

        BorrowingRecord result = borrowingService.borrowBook(bookId, patronId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getBorrowDate());

        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());

        verify(bookService).findById(bookId);
        verify(patronService).findById(patronId);
        verify(bookMapper).mapToBookEntity(bookDto);
        verify(patronMapper).mapToPatronEntity(patronDto);
        verify(borrowingRepository).save(any(BorrowingRecord.class));

    }


    @Test
    public void ReturnBook_shouldSetReturnDate() {

        // Arrange
        long bookId = 1L;
        long patronId = 1L;

        BorrowingRecord record = new BorrowingRecord();
        record.setId(10L);
        record.setBook(new Book());
        record.setPatron(new Patron());
        record.setBorrowDate(LocalDate.now());

        // Mock

        when(borrowingRepository.findLatestActiveBorrowingRecord(bookId, patronId))
                .thenReturn(Optional.of(record));

        when(borrowingRepository.save(any(BorrowingRecord.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        // Act

        BorrowingRecord result = borrowingService.returnBook(bookId, patronId);

        // Assert
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getReturnDate());

        verify(borrowingRepository).findLatestActiveBorrowingRecord(bookId, patronId);
        verify(borrowingRepository).save(record);

    }

}
