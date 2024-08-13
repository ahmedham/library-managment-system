package maids.springboot.library;

import maids.springboot.library.config.TestSecurityConfig;
import maids.springboot.library.controller.BorrowController;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.service.BorrowingRecordService;
import maids.springboot.library.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class)
@WebMvcTest(BorrowController.class)
public class BorrowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @MockBean
    private JwtService jwtService;

    private BorrowingRecord borrowingRecord;

    private Book book;

    private Patron patron;

    @BeforeEach
    void setUp(){

        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setPublicationYear("2020");
        book.setIsbn("978-0134685991");

        patron = new Patron();
        patron.setId(1L);
        patron.setName("samy");
        patron.setEmail("samy@test.com");
        patron.setPhone("01144319655");


        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);

        borrowingRecord.setBorrowDate(LocalDate.now());
    }

    @Test
    void borrowBook_ShouldReturnSuccessMessage() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        when(borrowingRecordService.borrowBook(bookId, patronId))
                .thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book borrowed successfully"));

        verify(borrowingRecordService).borrowBook(bookId, patronId);
    }

    @Test
    void returnBook_ShouldReturnSuccessMessage() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book returned successfully"));

        verify(borrowingRecordService).returnBook(bookId, patronId);
    }

}
