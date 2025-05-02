package maids.springboot.library.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import maids.springboot.library.config.TestSecurityConfig;
import maids.springboot.library.controller.PatronController;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.mapper.PatronMapper;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class)
@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PatronMapper patronMapper;

    private ObjectMapper objectMapper;
    private Patron patron;
    private Patron inputPatron;
    private PatronDto patronDto;

    @BeforeEach
    void setUp() {

        patronDto = new PatronDto();
        patronDto.setId(1L);
        patronDto.setName("samy");
        patronDto.setEmail("samy@test.com");
        patronDto.setPhone("01144319655");


        inputPatron = new Patron();
        inputPatron.setName("samy");
        inputPatron.setEmail("samy@test.com");
        inputPatron.setPhone("01144319655");

    }

    @Test
    void findAll_ReturnsListOfPatrons() throws Exception {

        patron = new Patron();
        patron.setId(1L);
        patron.setName("samy");
        patron.setEmail("samy@test.com");
        patron.setPhone("01144319655");


        when(patronService.findAll()).thenReturn(List.of(patronDto));
        when(patronMapper.mapToPatronDtoList(List.of(patron))).thenReturn(List.of(patronDto));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("samy"));
    }

    @Test
    void findById_ExistingId_ReturnsPatron() throws Exception {
        when(patronService.findById(1L)).thenReturn(Optional.of(patronDto).get());
        when(patronMapper.mapToPatronDto(patron)).thenReturn(patronDto);

        mockMvc.perform(get("/api/patrons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("samy"));
    }



    @Test
    void findById_ReturnsNotFoundWhenPatronDoesNotExist() throws Exception {
        when(patronService.findById(1L)).thenThrow(new RecordNotFoundException("Patron not found"));

        mockMvc.perform(get("/api/patrons/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void insert_ValidBook_ReturnsCreatedPatron() throws Exception {
        when(patronService.insert(any(PatronDto.class))).thenReturn(patronDto);
        when(patronMapper.mapToPatronDto(patron)).thenReturn(patronDto);

        objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPatron)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("samy"));
    }


    @Test
    void update_ExistingId_ReturnsUpdatedPatron() throws Exception {
        when(patronService.update(eq(1L), any(PatronDto.class))).thenReturn(patronDto);
        when(patronMapper.mapToPatronDto(patron)).thenReturn(patronDto);

        objectMapper = new ObjectMapper();


        mockMvc.perform(put("/api/patrons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPatron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("samy"));
    }


    @Test
    void deleteById_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/patrons/{id}", 1L))
                .andExpect(status().isNoContent());
    }


}
