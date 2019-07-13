package dao.custom;

import dao.CrudDAO;
import entity.Category;

import java.sql.SQLException;

public interface CategoryDAO extends CrudDAO<Category, Integer> {
    int maximumBorrowId() throws SQLException;
}
