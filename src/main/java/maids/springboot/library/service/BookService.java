package maids.springboot.library.service;

import maids.springboot.library.base.BaseService;
import maids.springboot.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookService extends BaseService<Book,Long> {

    @Autowired
    private BorrowingRecordService borrowingRecordService;


    @Override
    @Transactional
    public void deleteById(Long bookId) {

        if (borrowingRecordService.existsByBookId(bookId)) {
            throw new RuntimeException("Cannot delete book as it is being borrowed.");
        }

        super.deleteById(bookId);
    }


    @Override
    protected void updateEntityProperties(Book existingEntity, Book newEntity) {
        existingEntity.setTitle(newEntity.getTitle());
        existingEntity.setAuthor(newEntity.getAuthor());
        existingEntity.setIsbn(newEntity.getIsbn());
        existingEntity.setPublicationYear(newEntity.getPublicationYear());
    }
}
