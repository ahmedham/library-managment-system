package maids.springboot.library.base;

import jakarta.persistence.MappedSuperclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@MappedSuperclass
public class BaseService<T extends BaseEntity<ID>, ID> {

    @Autowired
    private BaseRepository<T, ID> baseRepository;


    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Transactional

    public T insert(T entity) {

        if (entity.getId() != null) {
            throw new RuntimeException(entity.getClass().getSimpleName() + " already exist");
        }

        return baseRepository.save(entity);
    }

    @Transactional
    public List<T> insert(List<T> entities) {
        return baseRepository.saveAll(entities);
    }

    @Transactional
    public T update(ID id,T entity) {

        T existingEntity = baseRepository.findById(id).orElseThrow(
                () -> new RuntimeException(entity.getClass().getSimpleName() + " not found")
        );

        updateEntityProperties(existingEntity, entity);

        return baseRepository.save(existingEntity);
    }

    @Transactional
    public void deleteById(ID id) {
        baseRepository.deleteById(id);
    }

    protected void updateEntityProperties(T existingEntity, T newEntity) {

    }
}
