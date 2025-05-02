package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.exception.DeletePatronException;
import maids.springboot.library.exception.DuplicateRecordException;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.mapper.PatronMapper;
import maids.springboot.library.repositories.PatronRepository;
import maids.springboot.library.validators.PatronValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepository patronRepository;

    private final PatronMapper patronMapper;

    private final PatronValidator patronValidator;

    @Cacheable(value = "findAllPatrons",key="#root.methodName")
    public List<PatronDto> findAll() {

        List<Patron> patrons = patronRepository.findAll();

        return patronMapper.mapToPatronDtoList(patrons);
    }

    @Cacheable(value = "findPatronById",key="#root.methodName")
    public PatronDto findById(Long id) {

        Patron patron = patronRepository.findById(id)
                .orElseThrow(
                        ()-> new RecordNotFoundException("Patron not found")
                );

        return patronMapper.mapToPatronDto(patron);

    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public PatronDto insert(PatronDto dto) {

        if(
                patronRepository.findByEmail(dto.getEmail()).isPresent() ||
                        patronRepository.findByPhone(dto.getPhone()).isPresent()
        ){
            throw new DuplicateRecordException("This Patron is already exists");
        }

        Patron patron = patronMapper.mapToPatronEntity(dto);

        Patron savedPatron = patronRepository.save(patron);

        return patronMapper.mapToPatronDto(savedPatron);
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public PatronDto update(Long id,PatronDto dto) {

        Patron existingEntity = patronRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Patron not found")
        );

        patronMapper.updatePatronFromDto(dto, existingEntity);

        Patron savedPatron = patronRepository.save(existingEntity);

        return patronMapper.mapToPatronDto(savedPatron);
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public void deleteById(Long id) {

        patronValidator.validatePatronNotBorrowedBeforeDelete(id);

        patronRepository.deleteById(id);
    }
}
