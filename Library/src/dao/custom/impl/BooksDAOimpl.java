package dao.custom.impl;

import dao.CrudUtil;
import dao.SearchType;
import dao.custom.BooksDAO;
import entity.Books;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksDAOimpl implements BooksDAO {

    public <T> List<Books> searchBy(SearchType type, T parameter) throws SQLException {
        ResultSet rst;
        if (type == SearchType.BOOK_NAME) {
            rst = CrudUtil.execute("SELECT * FROM books b where b.name like ?", (parameter + "%"));
        } else if (type == SearchType.BOOK_AUTHOR) {
            rst = CrudUtil.execute("SELECT * FROM books b where b.authorId like ?", (parameter + "%"));
        } else {
            rst = CrudUtil.execute("SELECT * from books b,category c where b.categoryId=c.categoryId AND c.categoryId like ?", (parameter + "%"));
        }
        List<Books> books = new ArrayList<>();
        while (rst.next()) {
            books.add(new Books(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getInt(6)));
        }
        return books;
    }

    @Override
    public int findMaximumId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(bookId) FROM books");
        return rst.next() ? rst.getInt(1) : 0;
    }

    @Override
    public boolean save(Books books) throws SQLException {
        return CrudUtil.execute("INSERT INTO books VALUES (?,?,?,?,?,?)", books.getBookId(), books.getName(), books.getAuthorId(), books.getCategoryId(), books.getEdition(), books.getQty());
    }

    @Override
    public boolean update(Books books) throws SQLException {
        return CrudUtil.execute("UPDATE books SET name=?,authorId=?,categoryId=?,edition=?,qty=? WHERE bookId=?", books.getName(), books.getAuthorId(), books.getCategoryId(), books.getEdition(), books.getQty(), books.getBookId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM books WHERE bookId=? ", id);
    }

    @Override
    public Books find(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM books WHERE bookId=?", id);
        return rst.next() ? new Books(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getInt(6)) : null;
    }

    @Override
    public List<Books> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM books");
        List<Books> books = new ArrayList<>();
        while (rst.next()) {
            books.add(new Books(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getString(5), rst.getInt(6)));
        }
        return books;
    }

    @Override
    public String findName(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT name FROM books where bookId=?", id);
        return rst.next() ? rst.getString(1) : null;
    }
}
