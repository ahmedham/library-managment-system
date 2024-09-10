package maids.springboot.library.mapper;

import maids.springboot.library.dto.BorrowingDto;
import maids.springboot.library.dto.PatronDto;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.entity.Patron;
import org.mapstruct.Mapper;

@Mapper
public interface BorrowingMapper {

    BorrowingDto mapToBorrowingDto(BorrowingRecord borrowingRecord);
    
    BorrowingRecord mapToBorrowingEntity(BorrowingDto borrowingDto);

}
