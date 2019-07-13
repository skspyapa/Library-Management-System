package utilTM;

public class Book {
    private String bookId;
    private String bookName;
    private String author;
    private String edition;
    private String category;
    private String pages;
    private int qty;

    public Book() {
    }

    public Book(String bookId, String bookName, String author, String edition, String category, String pages, int qty) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.edition = edition;
        this.category = category;
        this.pages = pages;
        this.qty = qty;
    }

    public Book(String bookId, String bookName, String author, String category, String edition, int qty) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.edition = edition;
        this.category = category;
        this.qty = qty;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
