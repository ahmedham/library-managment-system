package maids.springboot.library.repositories;

import maids.springboot.library.base.BaseRepository;
import maids.springboot.library.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends BaseRepository<Patron,Long> {
}
