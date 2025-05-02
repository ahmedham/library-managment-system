package maids.springboot.library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.mapper.PatronMapper;
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
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    private final PatronMapper patronMapper;


    @GetMapping
    public ResponseEntity<List<PatronDto>> findAll() {
        List<PatronDto> patronDtos = patronService.findAll();

        return ResponseEntity.ok(patronDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<PatronDto> findById(@PathVariable Long id) {
        PatronDto patronDto = patronService.findById(id);

        return ResponseEntity.ok(patronDto);
    }

    @PostMapping
    public ResponseEntity<PatronDto> insert(@RequestBody @Valid PatronDto patron) {
        PatronDto patronDto = patronService.insert(patron);

        return ResponseEntity.status(HttpStatus.CREATED).body(patronDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<PatronDto> update(@PathVariable Long id, @Valid @RequestBody PatronDto patron) {

        PatronDto patronDto = patronService.update(id, patron);

        return ResponseEntity.ok(patronDto);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void>  deleteById(@PathVariable Long id) {
        patronService.deleteById(id);

        return ResponseEntity.noContent().build();

    }

}
