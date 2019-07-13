package dto;

import java.time.LocalDate;

public class BookBroughtDTO {
    private int borrowId;
    private LocalDate returnDate;

    public BookBroughtDTO() {
    }

    public BookBroughtDTO(int borrowId, LocalDate returnDate) {
        this.borrowId = borrowId;
        this.returnDate = returnDate;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
