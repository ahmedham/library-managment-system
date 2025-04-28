package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.exception.DeleteBookException;
import maids.springboot.library.exception.DuplicateRecordException;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.repositories.BookRepository;
import maids.springboot.library.validators.BookValidator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final  BookValidator bookValidator;

    @Cacheable(value = "findAllBooks",key="#root.methodName")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "findBookById", key = "#root.methodName")
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(
                        ()-> new RecordNotFoundException("Book not found")
                );
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public Book insert(BookDto dto) {

        if(bookRepository.findByTitle(dto.getTitle()).isPresent() || bookRepository.findByIsbn(dto.getIsbn()).isPresent()){
            throw new DuplicateRecordException("This Book is already exists");
        }

        return bookRepository.save(bookMapper.mapToBookEntity(dto));
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public List<Book> insert(List<Book> entities) {
        return bookRepository.saveAll(entities);
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public Book update(Long id,BookDto dto) {

        Book existingEntity = bookRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Book not found")
        );

        bookMapper.updateBookFromDto(dto,existingEntity);

        return bookRepository.save(existingEntity);
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public void deleteById(Long id) {

        bookValidator.validateBookNotBorrowedBeforeDelete(id);

        bookRepository.deleteById(id);
    }
}
