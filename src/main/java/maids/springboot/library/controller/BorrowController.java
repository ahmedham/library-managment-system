package maids.springboot.library.controller;

import lombok.RequiredArgsConstructor;
import maids.springboot.library.entity.BorrowingRecord;
import maids.springboot.library.response.BorrowResponse;
import maids.springboot.library.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowingRecordService borrowingRecordService;

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowResponse> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(
                new BorrowResponse("Book borrowed successfully")
        );
    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowResponse> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok(
                new BorrowResponse("Book returned successfully")
        );
    }

}
