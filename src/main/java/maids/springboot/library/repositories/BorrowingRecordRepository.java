package maids.springboot.library.repositories;

import maids.springboot.library.base.BaseRepository;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowingRecordRepository extends BaseRepository<BorrowingRecord,Long> {

    Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long PatronId);

    Boolean existsByBookId(Long bookId);
    Boolean existsByPatronId(Long patronId);

    @Query("SELECT borrowing FROM BorrowingRecord borrowing" +
            " WHERE borrowing.book.id = :bookId AND borrowing.returnDate IS NULL")
    BorrowingRecord BookAlreadyBorrowed(Long bookId);

    @Query("SELECT borrowing FROM BorrowingRecord borrowing" +
            " WHERE borrowing.book.id = :bookId AND borrowing.patron.id = :patronId" +
            " AND borrowing.returnDate IS NULL")
    List<BorrowingRecord> findActiveBorrowingRecords(Long bookId, Long patronId);


}
