package entity;

public class Books extends SuperEntity {
    private int bookId;
    private String name;
    private String authorId;
    private int categoryId;
    private String edition;
    private int qty;

    public Books() {
    }

    public Books(int bookId, String name, String authorId, int categoryId, String edition, int qty) {
        this.bookId = bookId;
        this.name = name;
        this.authorId = authorId;
        this.categoryId = categoryId;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", authorId='" + authorId + '\'' +
                ", categoryId=" + categoryId +
                ", edition='" + edition + '\'' +
                ", qty=" + qty +
                '}';
    }
}
