package entity;

import java.time.LocalDate;

public class CustomEntity extends SuperEntity {
    private int bookId;
    private String name;
    private String authorName;
    private String categoryName;
    private String edition;
    private int qty;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private int borrowId;

    public CustomEntity() {
    }

    public CustomEntity(int borrowId, int bookId, String name, LocalDate borrowDate) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.name = name;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(7);
    }

    public CustomEntity(int bookId, String name, String authorName, String categoryName, String edition, int qty) {
        this.bookId = bookId;
        this.name = name;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.edition = edition;
        this.qty = qty;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
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

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", edition='" + edition + '\'' +
                ", qty=" + qty +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", borrowId=" + borrowId +
                '}';
    }
}
