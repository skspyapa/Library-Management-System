package dao.custom;

import dao.CrudDAO;
import entity.Borrow;

import java.sql.SQLException;

public interface BorrowDAO extends CrudDAO<Borrow, Integer> {
    int maximumBorrowId() throws SQLException;

}
