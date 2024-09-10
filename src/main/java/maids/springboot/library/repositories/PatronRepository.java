package maids.springboot.library.repositories;

import maids.springboot.library.base.BaseRepository;
import maids.springboot.library.entity.Patron;

import java.util.Optional;

public interface PatronRepository extends BaseRepository<Patron,Long> {

    Optional<Patron> findByEmail(String email);

    Optional<Patron> findByPhone(String email);
}
