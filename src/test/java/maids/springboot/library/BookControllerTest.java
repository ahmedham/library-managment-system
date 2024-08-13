package maids.springboot.library;

import maids.springboot.library.config.TestSecurityConfig;
import maids.springboot.library.controller.BookController;
import maids.springboot.library.entity.Book;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@Import(TestSecurityConfig.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtService jwtService;

    private Book book;

    @BeforeEach
    void setUp() {

        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setPublicationYear("2020");
        book.setIsbn("978-0134685991");
    }

    @Test
    void findAll_ReturnsListOfBooks() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Effective Java"));
    }

    @Test
    void findById_ExistingId_ReturnsBook() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(book).get());

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }



    @Test
    void findById_ReturnsNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.findById(1L)).thenThrow(new RuntimeException("Book not found"));

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void insert_ValidBook_ReturnsCreatedBook() throws Exception {
        when(bookService.insert(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Effective Java\", \"author\": \"Joshua Bloch\", \"isbn\": \"978-0134685991\", \"publicationYear\":\"2020\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }


    @Test
    void update_ExistingId_ReturnsUpdatedBook() throws Exception {
        when(bookService.update(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Effective Java\", \"author\": \"Joshua Bloch\", \"isbn\": \"978-0134685991\", \"publicationYear\":\"2020\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }


    @Test
    void deleteById_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }


}
