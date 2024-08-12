package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import maids.springboot.library.base.BaseEntity;

import java.time.LocalDate;

@Entity
public class BorrowingRecord extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowingRecord() {
    }

    public BorrowingRecord(Book book, Patron patron, LocalDate borrowDate) {
        this.book = book;
        this.patron = patron;
        this.borrowDate = borrowDate;
    }

    public Book getBook() {
        return book;
    }

    public BorrowingRecord setBook(Book book) {
        this.book = book;
        return this;
    }

    public Patron getPatron() {
        return patron;
    }

    public BorrowingRecord setPatron(Patron patron) {
        this.patron = patron;
        return this;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public BorrowingRecord setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BorrowingRecord setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }
}
