package maids.springboot.library.service;

import maids.springboot.library.base.BaseService;
import maids.springboot.library.entity.Book;
import maids.springboot.library.entity.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatronService extends BaseService<Patron,Long> {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @Override
    @Transactional
    public void deleteById(Long bookId) {

        if (borrowingRecordService.existsByPatronId(bookId)) {
            throw new RuntimeException("Cannot delete patron as they have active borrowing records.");
        }

        super.deleteById(bookId);
    }

    protected void updateEntityProperties(Patron existingEntity, Patron newEntity) {
        existingEntity.setName(newEntity.getName());
        existingEntity.setEmail(newEntity.getEmail());
        existingEntity.setPhone(newEntity.getPhone());
    }

}
