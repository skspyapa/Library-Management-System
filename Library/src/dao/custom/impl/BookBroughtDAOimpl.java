package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.BookBroughtDAO;
import entity.BookBrought;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBroughtDAOimpl implements BookBroughtDAO {
    @Override
    public boolean save(BookBrought bookBrought) throws SQLException {
        return CrudUtil.execute("INSERT INTO bookbrought VALUES(?,?)", bookBrought.getBorrowId(), bookBrought.getReturnDate());
    }

    @Override
    public boolean update(BookBrought bookBrought) throws SQLException {
        return CrudUtil.execute("UPDATE bookbrought SET returnDate=? WHERE borrowId=?", bookBrought.getReturnDate(), bookBrought.getBorrowId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM bookBrought WHERE borrowId=?", id);
    }

    @Override
    public BookBrought find(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM bookbrought WHERE borrowId=?", id);
        return rst.next() ? new BookBrought(rst.getInt(1), rst.getDate(2).toLocalDate()) : null;
    }

    @Override
    public List<BookBrought> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM bookbrought");
        List<BookBrought> bookBroughts = new ArrayList<>();
        while (rst.next()) {
            bookBroughts.add(new BookBrought(rst.getInt(1), rst.getDate(2).toLocalDate()));
        }
        return bookBroughts;
    }
}
