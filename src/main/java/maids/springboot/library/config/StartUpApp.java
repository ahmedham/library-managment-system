package maids.springboot.library.config;

import maids.springboot.library.dto.BookDto;
import maids.springboot.library.dto.BorrowingDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.BorrowingRecordService;
import maids.springboot.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StartUpApp implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    private  BookDto bookDto;

    private  PatronDto patronDto;

    @Override
    public void run(String... args) throws Exception {

        bookDto = new BookDto()
                        .setTitle("Effective Java, 3E")
                        .setAuthor("Joshua Bloch")
                        .setPublicationYear("2008")
                        .setIsbn("0134685997");

        Book effectiveJava = bookService.insert(bookDto);

        bookDto = new BookDto()
                .setTitle("Java Illuminated")
                .setAuthor("Julie Anderson")
                .setPublicationYear("2020")
                .setIsbn("1284140997");

        Book javaIlluminated = bookService.insert(bookDto);

        bookDto = new BookDto()
                .setTitle("Clean Code")
                .setAuthor("Robert C. Martin")
                .setPublicationYear("2012")
                .setIsbn("9780136083238");

        Book cleanCode = bookService.insert(bookDto);

        // add patrons
        patronDto = new PatronDto()
                .setName("Ahmed")
                .setEmail("ahmed@gmail.com")
                .setPhone("01066361457");


        Patron ahmed =  patronService.insert(patronDto);

        patronDto = new PatronDto()
                .setName("Samy")
                .setEmail("samy@gmail.com")
                .setPhone("01066361455");

        Patron samy = patronService.insert(patronDto);

        patronDto = new PatronDto()
                .setName("Mona")
                .setEmail("mona@gmail.com")
                .setPhone("01066361452");

        Patron mona = patronService.insert(patronDto);


        // borrowing

        BorrowingDto borrowingJavaIlluminated = new BorrowingDto()
                .setBook(javaIlluminated)
                .setPatron(ahmed)
                .setBorrowDate(LocalDate.now());

        borrowingRecordService.insert(borrowingJavaIlluminated);



        BorrowingDto borrowingEffectiveJava = new BorrowingDto()
                .setBook(effectiveJava)
                .setPatron(samy)
                .setBorrowDate(LocalDate.now());

        borrowingRecordService.insert(borrowingEffectiveJava);

        BorrowingDto borrowingCleanCode = new BorrowingDto()
                .setBook(cleanCode)
                .setPatron(mona)
                .setBorrowDate(LocalDate.now());

        borrowingRecordService.insert(borrowingCleanCode);

    }
}
