package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import maids.springboot.library.base.BaseDto;
import maids.springboot.library.base.BaseEntity;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class BorrowingRecord extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowingRecord(Book book, Patron patron, LocalDate borrowDate) {
        this.book = book;
        this.patron = patron;
        this.borrowDate = borrowDate;
    }

}
