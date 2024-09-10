package maids.springboot.library.repositories;

import maids.springboot.library.base.BaseRepository;
import maids.springboot.library.entity.Book;

import java.util.Optional;

public interface BookRepository extends BaseRepository<Book,Long> {

    Optional<Book> findByTitle(String name);
    Optional<Book> findByIsbn(String isbn);
}
