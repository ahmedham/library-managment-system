package maids.springboot.library;

import maids.springboot.library.config.TestSecurityConfig;
import maids.springboot.library.controller.PatronController;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.service.JwtService;
import maids.springboot.library.service.PatronService;
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
@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @MockBean
    private JwtService jwtService;


    private Patron patron;

    @BeforeEach
    void setUp() {

        patron = new Patron();
        patron.setId(1L);
        patron.setName("samy");
        patron.setEmail("samy@test.com");
        patron.setPhone("01144319655");
    }

    @Test
    void findAll_ReturnsListOfPatrons() throws Exception {
        when(patronService.findAll()).thenReturn(List.of(patron));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("samy"));
    }

    @Test
    void findById_ExistingId_ReturnsPatron() throws Exception {
        when(patronService.findById(1L)).thenReturn(Optional.of(patron).get());

        mockMvc.perform(get("/api/patrons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("samy"));
    }



    @Test
    void findById_ReturnsNotFoundWhenPatronDoesNotExist() throws Exception {
        when(patronService.findById(1L)).thenThrow(new RuntimeException("Patron not found"));

        mockMvc.perform(get("/api/patrons/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void insert_ValidBook_ReturnsCreatedPatron() throws Exception {
        when(patronService.insert(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"samy\", \"email\": \"samy@gmail.com\", \"phone\":\"01144319655\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("samy"));
    }


    @Test
    void update_ExistingId_ReturnsUpdatedPatron() throws Exception {
        when(patronService.update(eq(1L), any(Patron.class))).thenReturn(patron);

        mockMvc.perform(put("/api/patrons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"samy\", \"email\": \"samy@gmail.com\", \"phone\":\"01144319655\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("samy"));
    }


    @Test
    void deleteById_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/patrons/{id}", 1L))
                .andExpect(status().isNoContent());
    }


}
