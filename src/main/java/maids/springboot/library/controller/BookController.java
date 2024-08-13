package maids.springboot.library.controller;

import jakarta.validation.Valid;
import maids.springboot.library.entity.Book;
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
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping
    @Cacheable(value = "books")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "books", key = "#id")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }


    @PostMapping
    @CacheEvict(value="books", allEntries=true)
    public ResponseEntity<Book> insert(@Valid @RequestBody Book book) {
        Book savedBook = bookService.insert(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("{id}")
    @CacheEvict(value="books", allEntries=true)
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book updatedBook =  bookService.update(id, book);

        return  ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value="books", allEntries=true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
