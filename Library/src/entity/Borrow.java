package entity;

import java.time.LocalDate;

public class Borrow extends SuperEntity {
    private int borrowId;
    private LocalDate borrowDate;
    private int studentId;
    private int bookId;

    public Borrow() {
    }

    public Borrow(int borrowId, LocalDate borrowDate, int studentId, int bookId) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.studentId = studentId;
        this.bookId = bookId;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "borrowId=" + borrowId +
                ", borrowDate=" + borrowDate +
                ", studentId=" + studentId +
                ", bookId=" + bookId +
                '}';
    }
}
