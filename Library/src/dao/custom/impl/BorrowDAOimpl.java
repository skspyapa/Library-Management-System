package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.BorrowDAO;
import entity.Borrow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAOimpl implements BorrowDAO {

    @Override
    public boolean save(Borrow borrow) throws SQLException {
        return CrudUtil.execute("INSERT INTO borrow VALUES (?,?,?,?)", borrow.getBorrowId(), borrow.getBorrowDate(), borrow.getStudentId(), borrow.getBookId());
    }

    @Override
    public boolean update(Borrow borrow) throws SQLException {
        return CrudUtil.execute("UPDATE borrow SET borrowDate=?,studentId=?,bookId=? WHERE borrowId=?", borrow.getBorrowDate(), borrow.getStudentId(), borrow.getBookId(), borrow.getBorrowId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM borrow WHERE bookId=? ", id);
    }

    @Override
    public Borrow find(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM borrow WHERE borrowId=?", id);
        return rst.next() ? new Borrow(rst.getInt(1), rst.getDate(2).toLocalDate(), rst.getInt(3), rst.getInt(4)) : null;
    }

    @Override
    public List<Borrow> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM borrow");
        List<Borrow> borrow = new ArrayList<>();
        while (rst.next()) {
            borrow.add(new Borrow(rst.getInt(1), rst.getDate(2).toLocalDate(), rst.getInt(3), rst.getInt(4)));
        }
        return borrow;
    }

    public int maximumBorrowId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT max(borrowId) FROM borrow");
        return rst.next() ? rst.getInt(1) : 0;
    }
}
