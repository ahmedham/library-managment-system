package maids.springboot.library.UnitTests;

import maids.springboot.library.dto.BookDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.mapper.PatronMapper;
import maids.springboot.library.repositories.BookRepository;
import maids.springboot.library.repositories.PatronRepository;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.PatronService;
import maids.springboot.library.validators.BookValidator;
import maids.springboot.library.validators.PatronValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PatronServiceTest {


    @InjectMocks
    private PatronService patronService;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronMapper patronMapper;

    @Mock
    private PatronValidator patronValidator;

    @Test
    public void findAll_ReturnsListOfPatrons() {

        // Mocks
        when(patronRepository.findAll()).thenReturn(Collections.singletonList(new Patron()));
        when(patronMapper.mapToPatronDtoList(anyList())).thenReturn(Collections.singletonList(new PatronDto()));

        // Act
        List<PatronDto> patronDtos = patronService.findAll();

        // Assert
        assertNotNull(patronDtos);
    }


    @Test
    public void findById_ReturnsBook() {
        // Arrange
        long patronId = 1;

        // Mocks
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        when(patronMapper.mapToPatronDto(any(Patron.class))).thenReturn(new PatronDto());

        // Act
        PatronDto patronDto = patronService.findById(patronId);

        // Assert
        assertNotNull(patronDto);
    }

    @Test
    public void update_ReturnsUpdatedPatron(){
        // Arrange

        long patronId = 1;

        Patron existingPatron = new Patron();
        existingPatron.setId(patronId);
        existingPatron.setName("Old Name");

        Patron updatedPatron = new Patron();
        updatedPatron.setId(patronId);
        updatedPatron.setName("Updated Name");

        PatronDto expectedDto = new PatronDto();
        expectedDto.setName("Updated Name");

        // Mocks
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(existingPatron));
        doAnswer(invocation -> {
            PatronDto dto = invocation.getArgument(0);
            Patron patron = invocation.getArgument(1);
            patron.setName(dto.getName()); // simulate mapping
            return null;
        }).when(patronMapper).updatePatronFromDto(any(PatronDto.class), any(Patron.class));

        when(patronRepository.save(existingPatron)).thenReturn(updatedPatron);

        when(patronMapper.mapToPatronDto(updatedPatron)).thenReturn(expectedDto);

        // Act
        PatronDto result = patronService.update(patronId, expectedDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());

    }


    @Test
    public void insert_ReturnsNewBook(){

        // Arrange
        PatronDto inputDto = new PatronDto();
        inputDto.setName("New Name");
        inputDto.setEmail("email@test.com");

        Patron mappedPatron = new Patron();
        mappedPatron.setName("New Name");
        mappedPatron.setEmail("email@test.com");

        Patron savedPatron = new Patron();
        savedPatron.setId(1L);
        savedPatron.setName("New Name");
        savedPatron.setEmail("email@test.com");

        PatronDto expectedDto = new PatronDto();
        expectedDto.setId(1L);
        expectedDto.setName("New Name");
        expectedDto.setEmail("email@test.com");

        // Mocks
        when(patronRepository.findByEmail(inputDto.getEmail())).thenReturn(Optional.empty());
        when(patronRepository.findByPhone(inputDto.getPhone())).thenReturn(Optional.empty());
        when(patronMapper.mapToPatronEntity(inputDto)).thenReturn(mappedPatron);
        when(patronRepository.save(mappedPatron)).thenReturn(savedPatron);
        when(patronMapper.mapToPatronDto(savedPatron)).thenReturn(expectedDto);

        // Act
        PatronDto result = patronService.insert(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getEmail(), result.getEmail());

    }

    @Test
    public void delete_ReturnsNothing(){

        // Arrange
        long patronId = 1;

        // Act
        patronService.deleteById(patronId);

        // Assert
        verify(patronValidator).validatePatronNotBorrowedBeforeDelete(patronId);
        verify(patronRepository).deleteById(patronId);

    }

}
