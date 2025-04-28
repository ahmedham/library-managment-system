package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.PatronDto;
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
    public List<Patron> findAll() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "findPatronById",key="#root.methodName")
    public Patron findById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(
                        ()-> new RecordNotFoundException("Patron not found")
                );
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public Patron insert(PatronDto dto) {

        if(
                patronRepository.findByEmail(dto.getEmail()).isPresent() ||
                        patronRepository.findByPhone(dto.getPhone()).isPresent()
        ){
            throw new DuplicateRecordException("This Patron is already exists");
        }

        return patronRepository.save(patronMapper.mapToPatronEntity(dto));
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public List<Patron> insert(List<Patron> entities) {
        return patronRepository.saveAll(entities);
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public Patron update(Long id,PatronDto dto) {

        Patron existingEntity = patronRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Patron not found")
        );

        patronMapper.updatePatronFromDto(dto, existingEntity);

        return patronRepository.save(existingEntity);
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public void deleteById(Long id) {

        patronValidator.validatePatronNotBorrowedBeforeDelete(id);

        patronRepository.deleteById(id);
    }
}
