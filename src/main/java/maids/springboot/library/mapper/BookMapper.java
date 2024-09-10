package maids.springboot.library.mapper;

import maids.springboot.library.dto.BookDto;
import maids.springboot.library.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookDto mapToBookDto(Book book);

    Book mapToBookEntity(BookDto bookDto);

    List<BookDto> mapToBookDtoList(List<Book> books);

    List<Book> mapToBookEntityList(List<BookDto> bookDtos);


}
