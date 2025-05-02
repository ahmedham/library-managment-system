package maids.springboot.library.UnitTests;

import maids.springboot.library.dto.BookDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.repositories.BookRepository;
import maids.springboot.library.service.BookService;
import maids.springboot.library.validators.BookValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private  BookRepository bookRepository;

    @Mock
    private  BookMapper bookMapper;

    @Mock
    private  BookValidator bookValidator;



    @Test
    public void findAll_ReturnsListOfBooks() {

        // Mocks
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(new Book()));
        when(bookMapper.mapToBookDtoList(anyList())).thenReturn(Collections.singletonList(new BookDto()));

        // Act
        List<BookDto> bookDtos = bookService.findAll();

        // Assert
        assertNotNull(bookDtos);
    }


    @Test
    public void findById_ReturnsBook() {
        // Arrange
        long bookId = 1;

        // Mocks
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(new BookDto());

        // Act
        BookDto bookDto = bookService.findById(bookId);

        // Assert
        assertNotNull(bookDto);
    }

    @Test
    public void update_ReturnsUpdatedBook(){
        // Arrange

        long bookId = 1;

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle("Updated Title");

        BookDto expectedDto = new BookDto();
        expectedDto.setTitle("Updated Title");

        // Mocks
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        doAnswer(invocation -> {
            BookDto dto = invocation.getArgument(0);
            Book book = invocation.getArgument(1);
            book.setTitle(dto.getTitle()); // simulate mapping
            return null;
        }).when(bookMapper).updateBookFromDto(any(BookDto.class), any(Book.class));

        when(bookRepository.save(existingBook)).thenReturn(updatedBook);

        when(bookMapper.mapToBookDto(updatedBook)).thenReturn(expectedDto);

        // Act
        BookDto result = bookService.update(bookId, expectedDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getTitle(), result.getTitle());

    }

    @Test
    public void insert_ReturnsNewBook(){

        // Arrange
        BookDto inputDto = new BookDto();
        inputDto.setTitle("New Book");
        inputDto.setIsbn("1234567890");

        Book mappedBook = new Book();
        mappedBook.setTitle("New Book");
        mappedBook.setIsbn("1234567890");

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("New Book");
        savedBook.setIsbn("1234567890");

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle("New Book");
        expectedDto.setIsbn("1234567890");

        // Mocks
        when(bookRepository.findByTitle(inputDto.getTitle())).thenReturn(Optional.empty());
        when(bookRepository.findByIsbn(inputDto.getIsbn())).thenReturn(Optional.empty());
        when(bookMapper.mapToBookEntity(inputDto)).thenReturn(mappedBook);
        when(bookRepository.save(mappedBook)).thenReturn(savedBook);
        when(bookMapper.mapToBookDto(savedBook)).thenReturn(expectedDto);

        // Act
        BookDto result = bookService.insert(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getTitle(), result.getTitle());
        assertEquals(expectedDto.getIsbn(), result.getIsbn());

    }

    @Test
    public void delete_ReturnsNothing(){

        // Arrange
        long bookId = 1;

        // Act
        bookService.deleteById(bookId);

        // Assert
        verify(bookValidator).validateBookNotBorrowedBeforeDelete(bookId);
        verify(bookRepository).deleteById(bookId);

    }

}
