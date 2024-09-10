package maids.springboot.library.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import maids.springboot.library.base.BaseDto;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class BookDto extends BaseDto<Long> {

    @NotEmpty(message = "Title is mandatory")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotEmpty(message = "Author is mandatory")
    @Size(max = 255, message = "Author cannot exceed 255 characters")
    private String author;

    @NotEmpty(message = "Publication year is mandatory")
    private String publicationYear;

    @NotEmpty(message = "ISBN is mandatory")
    @Size(min = 10,max = 13, message = "ISBN must be between 10 and 13 characters")
    @Pattern(regexp = "^[0-9]{10,13}$", message = "Invalid ISBN format")
    private String Isbn;

}
