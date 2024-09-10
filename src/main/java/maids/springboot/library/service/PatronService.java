package maids.springboot.library.service;

import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.exception.DuplicateRecordException;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    private PatronRepository patronRepository;

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

        return patronRepository.save(convertToEntity(dto));
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

        updateEntityProperties(existingEntity, convertToEntity(dto));

        return patronRepository.save(existingEntity);
    }

    @Transactional
    @CacheEvict(value= {"findPatronById", "findAllPatrons"}, key = "#root.methodName", allEntries=true)
    public void deleteById(Long id) {

        if (borrowingRecordService.existsByPatronId(id)) {
            throw new RuntimeException("Cannot delete patron as they have active borrowing records.");
        }

        patronRepository.deleteById(id);
    }

    protected void updateEntityProperties(Patron existingEntity, Patron newEntity) {
        existingEntity.setName(newEntity.getName());
        existingEntity.setEmail(newEntity.getEmail());
        existingEntity.setPhone(newEntity.getPhone());
    }

    public Patron convertToEntity(PatronDto dto){

        Patron patron = new Patron();
        patron.setName(dto.getName());
        patron.setEmail(dto.getEmail());
        patron.setPhone(dto.getPhone());

        return patron;

    }

}
