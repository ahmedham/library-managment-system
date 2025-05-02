package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import maids.springboot.library.base.BaseDto;
import maids.springboot.library.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity<Long>  implements Serializable {

    @Serial
    private static final long serialVersionUID = 5978785322769660671L;

    private String title;

    private String author;

    private String publicationYear;

    private String isbn;
}
