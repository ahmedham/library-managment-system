package maids.springboot.library.repositories;

import maids.springboot.library.base.BaseRepository;
import maids.springboot.library.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User,Long> {
    Optional<User> findByEmail(String email);

}
