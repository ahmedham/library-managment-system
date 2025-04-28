package maids.springboot.library.mapper;

import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface PatronMapper {

    PatronDto mapToPatronDto(Patron patron);
    
    Patron mapToPatronEntity(PatronDto patronDto);

    List<PatronDto> mapToPatronDtoList(List<Patron> patrons);

    List<Patron> mapToPatronEntityList(List<PatronDto> patronDtos);

    @Mapping(target = "id", ignore = true)
    void updatePatronFromDto(PatronDto dto, @MappingTarget Patron entity);


}
