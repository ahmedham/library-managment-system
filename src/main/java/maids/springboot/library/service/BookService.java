package maids.springboot.library.service;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.exception.DuplicateRecordException;
import maids.springboot.library.exception.RecordNotFoundException;
import maids.springboot.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {

    @Lazy
    private final BorrowingRecordService borrowingRecordService;

    private final BookRepository bookRepository;

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

        return bookRepository.save(convertToEntity(dto));
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

        updateEntityProperties(existingEntity, convertToEntity(dto));

        return bookRepository.save(existingEntity);
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public void deleteById(Long id) {

        if (borrowingRecordService.existsByBookId(id)) {
            throw new RuntimeException("Cannot delete book as it is being borrowed.");
        }

        bookRepository.deleteById(id);
    }

    protected void updateEntityProperties(Book existingEntity, Book newEntity) {
        existingEntity.setTitle(newEntity.getTitle());
        existingEntity.setAuthor(newEntity.getAuthor());
        existingEntity.setIsbn(newEntity.getIsbn());
        existingEntity.setPublicationYear(newEntity.getPublicationYear());
    }

    public Book convertToEntity(BookDto dto){

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());

        return book;
    }

}
