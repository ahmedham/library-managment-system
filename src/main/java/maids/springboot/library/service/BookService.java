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
    public List<BookDto> findAll() {

        List<Book> books = bookRepository.findAll();

        return bookMapper.mapToBookDtoList(books);

    }

    @Cacheable(value = "findBookById", key = "#root.methodName")
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        ()-> new RecordNotFoundException("Book not found")
                );

        return bookMapper.mapToBookDto(book);
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public BookDto insert(BookDto dto) {

        if(bookRepository.findByTitle(dto.getTitle()).isPresent() || bookRepository.findByIsbn(dto.getIsbn()).isPresent()){
            throw new DuplicateRecordException("This book already exists");
        }

        Book book = bookMapper.mapToBookEntity(dto);

        Book savedBook = bookRepository.save(book);

        return bookMapper.mapToBookDto(savedBook);
    }


    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public BookDto update(Long id,BookDto dto) {

        Book existingEntity = bookRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Book not found")
        );

        bookMapper.updateBookFromDto(dto,existingEntity);

        Book savedBook = bookRepository.save(existingEntity);

        return bookMapper.mapToBookDto(savedBook);
    }

    @Transactional
    @CacheEvict(value= {"findBookById", "findAllBooks"}, key = "#root.methodName", allEntries=true)
    public void deleteById(Long id) {

        bookValidator.validateBookNotBorrowedBeforeDelete(id);

        bookRepository.deleteById(id);
    }
}
