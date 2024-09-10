package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import maids.springboot.library.base.BaseDto;
import maids.springboot.library.base.BaseEntity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity<Long> {

    private String title;

    private String author;

    private String publicationYear;

    private String isbn;
}
