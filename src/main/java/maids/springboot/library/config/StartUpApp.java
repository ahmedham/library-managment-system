package maids.springboot.library.config;

import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import maids.springboot.library.service.BookService;
import maids.springboot.library.service.BorrowingRecordService;
import maids.springboot.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class StartUpApp implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @Override
    public void run(String... args) throws Exception {

        // add books
        Book effectiveJava = new Book("Effective Java, 3E","Joshua Bloch","2008","0134685997");
        bookService.insert(effectiveJava);

        Book javaIlluminated = new Book("Java Illuminated","Julie Anderson","2020","1284140997");
        bookService.insert(javaIlluminated);

        Book cleanCode = new Book("Clean Code","Robert C. Martin","2012","9780136083238");
        bookService.insert(cleanCode);

        // add patrons
        Patron ahmed = new Patron("Ahmed", "ahmed@gmail.com","01066361457");
        patronService.insert(ahmed);

        Patron samy = new Patron("Samy", "samy@gmail.com","01066361455");
        patronService.insert(samy);

        Patron mona = new Patron("Mona", "mona@gmail.com","01066361452");
        patronService.insert(mona);


        // borrowing

        BorrowingRecord borrowingJavaIlluminated = new BorrowingRecord(javaIlluminated, ahmed, LocalDate.now());
        borrowingRecordService.insert(borrowingJavaIlluminated);



        BorrowingRecord borrowingEffectiveJava = new BorrowingRecord(effectiveJava, samy, LocalDate.now());
        borrowingRecordService.insert(borrowingEffectiveJava);

        BorrowingRecord borrowingCleanCode = new BorrowingRecord(cleanCode, mona, LocalDate.now());
        borrowingRecordService.insert(borrowingCleanCode);

    }
}
