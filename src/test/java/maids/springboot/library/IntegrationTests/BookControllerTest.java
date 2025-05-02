package maids.springboot.library.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import maids.springboot.library.config.TestSecurityConfig;
import maids.springboot.library.controller.BookController;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private BookMapper bookMapper;

    private ObjectMapper mapper;
    private Book book;
    private Book inputBook;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {


        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Effective Java");
        bookDto.setAuthor("Joshua Bloch");
        bookDto.setPublicationYear("2020");
        bookDto.setIsbn("0134685991");


        inputBook = new Book();
        inputBook.setTitle("Effective Java");
        inputBook.setAuthor("Joshua Bloch");
        inputBook.setPublicationYear("2020");
        inputBook.setIsbn("0134685991");

    }

    @Test
    void findAll_ReturnsListOfBooks() throws Exception {

        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setPublicationYear("2020");
        book.setIsbn("0134685991");

        when(bookService.findAll()).thenReturn(List.of(bookDto));
        when(bookMapper.mapToBookDtoList(List.of(book))).thenReturn(List.of(bookDto));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Effective Java"));
    }

    @Test
    void findById_ExistingId_ReturnsBook() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(bookDto).get());
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }


    @Test
    void findById_ReturnsNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.findById(1L)).thenThrow(new RecordNotFoundException("Book not found"));

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void insert_ValidBook_ReturnsCreatedBook() throws Exception {
        when(bookService.insert(any(BookDto.class))).thenReturn(bookDto);
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);

        mapper = new ObjectMapper();

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }


    @Test
    void update_ExistingId_ReturnsUpdatedBook() throws Exception {
        when(bookService.update(eq(1L), any(BookDto.class))).thenReturn(bookDto);
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);

        mapper = new ObjectMapper();


        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputBook))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }


    @Test
    void deleteById_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }


}
