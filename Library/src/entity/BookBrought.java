package entity;

import java.time.LocalDate;

public class BookBrought extends SuperEntity {
    private int borrowId;
    private LocalDate returnDate;

    public BookBrought() {
    }

    public BookBrought(int borrowId, LocalDate returnDate) {
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

    @Override
    public String toString() {
        return "BookBrought{" +
                "borrowId=" + borrowId +
                ", returnDate=" + returnDate +
                '}';
    }
}
