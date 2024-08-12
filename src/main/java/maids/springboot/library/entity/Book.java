package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import maids.springboot.library.base.BaseEntity;

@Entity
public class Book extends BaseEntity<Long> {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @Min(value = 1000, message = "Publication year should not be less than 1000")
    @Max(value = 9999, message = "Publication year should not be greater than 9999")
    @NotBlank(message = "Publication year is mandatory")
    private String publicationYear;


    @NotBlank(message = "ISBN is mandatory")
    private String Isbn;

    public Book() {
        super();
    }

    public Book(String title, String author, String publicationYear, String ISBN) {
        super();
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.Isbn = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public Book setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
        return this;
    }

    public String getIsbn() {
        return Isbn;
    }

    public Book setIsbn(String isbn) {
        this.Isbn = isbn;
        return this;
    }
}
