package utilTM;

import javafx.scene.control.Button;

import java.time.LocalDate;

public class Custom {
    private String bookId;
    private String name;
    private String authorName;
    private String categoryName;
    private String edition;
    private int qty;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String borrowId;
    private Button btnReturn;

    public Custom() {
    }

    public Custom(String borrowId, String bookId, String name, LocalDate borrowDate, LocalDate dueDate) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.name = name;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Custom(String bookId, String name, LocalDate borrowDate) {
        this.bookId = bookId;
        this.name = name;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(7);
    }

    public Custom(String bookId, String name, String authorName, String categoryName, String edition, int qty) {
        this.bookId = bookId;
        this.name = name;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.edition = edition;
        this.qty = qty;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public Button getBtnReturn() {
        return btnReturn;
    }

    public void setBtnReturn(Button btnReturn) {
        this.btnReturn = btnReturn;
    }
}
