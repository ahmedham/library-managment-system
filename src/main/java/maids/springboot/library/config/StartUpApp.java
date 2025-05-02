package maids.springboot.library.config;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.dto.BookDto;
import maids.springboot.library.dto.BorrowingDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.mapper.BookMapper;
import maids.springboot.library.mapper.PatronMapper;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.BorrowingRecordService;
import maids.springboot.library.service.PatronService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StartUpApp implements CommandLineRunner {

    @Lazy
    private final BookService bookService;

    @Lazy
    private final PatronService patronService;

    @Lazy
    private final BorrowingRecordService borrowingRecordService;

    private final BookMapper bookMapper;

    private final PatronMapper patronMapper;

    private BookDto bookDto;

    private PatronDto patronDto;

    @Override
    public void run(String... args) throws Exception {

        bookDto = new BookDto()
                        .setTitle("Effective Java, 3E")
                        .setAuthor("Joshua Bloch")
                        .setPublicationYear("2008")
                        .setIsbn("0134685997");

        BookDto effectiveJava = bookService.insert(bookDto);

        bookDto = new BookDto()
                .setTitle("Java Illuminated")
                .setAuthor("Julie Anderson")
                .setPublicationYear("2020")
                .setIsbn("1284140997");

        BookDto javaIlluminated = bookService.insert(bookDto);

        bookDto = new BookDto()
                .setTitle("Clean Code")
                .setAuthor("Robert C. Martin")
                .setPublicationYear("2012")
                .setIsbn("9780136083238");

        BookDto cleanCode = bookService.insert(bookDto);

        // add patrons
        patronDto = new PatronDto()
                .setName("Ahmed")
                .setEmail("ahmed@gmail.com")
                .setPhone("01066361457");


        PatronDto ahmed =  patronService.insert(patronDto);

        patronDto = new PatronDto()
                .setName("Samy")
                .setEmail("samy@gmail.com")
                .setPhone("01066361455");

        PatronDto samy = patronService.insert(patronDto);

        patronDto = new PatronDto()
                .setName("Mona")
                .setEmail("mona@gmail.com")
                .setPhone("01066361452");

        PatronDto mona = patronService.insert(patronDto);


        // borrowing

        BorrowingDto borrowingJavaIlluminated = new BorrowingDto()
                .setBook(bookMapper.mapToBookEntity(javaIlluminated))
                .setPatron(patronMapper.mapToPatronEntity(ahmed))
                .setBorrowDate(LocalDate.now());

        borrowingRecordService.insert(borrowingJavaIlluminated);



        BorrowingDto borrowingEffectiveJava = new BorrowingDto()
                .setBook(bookMapper.mapToBookEntity(effectiveJava))
                .setPatron(patronMapper.mapToPatronEntity(samy))
                .setBorrowDate(LocalDate.now());

        borrowingRecordService.insert(borrowingEffectiveJava);

        BorrowingDto borrowingCleanCode = new BorrowingDto()
                .setBook(bookMapper.mapToBookEntity(cleanCode))
                .setPatron(patronMapper.mapToPatronEntity(mona))
                .setBorrowDate(LocalDate.now());

        borrowingRecordService.insert(borrowingCleanCode);

    }
}
