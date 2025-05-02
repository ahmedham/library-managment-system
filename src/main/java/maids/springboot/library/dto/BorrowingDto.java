package maids.springboot.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class BorrowingDto  implements Serializable {

    @Serial
    private static final long serialVersionUID = -1349495035118659495L;

    private Book book;

    private Patron patron;

    private LocalDate borrowDate;

}
