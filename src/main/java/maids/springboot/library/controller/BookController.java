package maids.springboot.library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        List<Book> books = bookService.findAll();
        List<BookDto> bookDtos = bookMapper.mapToBookDtoList(books);

        return ResponseEntity.ok(bookDtos);

    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        Book book = bookService.findById(id);

        BookDto bookDto = bookMapper.mapToBookDto(book);

        return ResponseEntity.ok(bookDto);
    }


    @PostMapping
    public ResponseEntity<BookDto> insert(@Valid @RequestBody BookDto book) {

        Book savedBook = bookService.insert(book);

        BookDto bookDto = bookMapper.mapToBookDto(savedBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @Valid @RequestBody BookDto book) {
        Book updatedBook =  bookService.update(id, book);

        BookDto bookDto = bookMapper.mapToBookDto(updatedBook);

        return  ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
