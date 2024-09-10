package maids.springboot.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;

import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class BorrowingDto {

    private Book book;

    private Patron patron;

    private LocalDate borrowDate;

}
