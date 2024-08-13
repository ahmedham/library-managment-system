package maids.springboot.library.controller;

import jakarta.validation.Valid;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    @Cacheable(value = "patrons")
    public List<Patron> findAll() {
        return patronService.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "patrons", key = "#id")
    public ResponseEntity<Patron> findById(@PathVariable Long id) {
        Patron patron = patronService.findById(id);

        return ResponseEntity.ok(patron);
    }

    @PostMapping
    @CacheEvict(value="patrons", allEntries=true)
    public ResponseEntity<Patron> insert(@RequestBody @Valid Patron patron) {

        Patron savedPatron = patronService.insert(patron);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }

    @PutMapping("{id}")
    @CacheEvict(value="patrons", allEntries=true)
    public Patron update(@PathVariable Long id, @Valid @RequestBody Patron patron) {
        return patronService.update(id, patron);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value="patrons", allEntries=true)
    public ResponseEntity<Void>  deleteById(@PathVariable Long id) {
        patronService.deleteById(id);

        return ResponseEntity.noContent().build();

    }
}
