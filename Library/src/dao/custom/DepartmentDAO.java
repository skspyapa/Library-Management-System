package dao.custom;

import dao.CrudDAO;
import entity.Department;

import java.sql.SQLException;

public interface DepartmentDAO extends CrudDAO<Department, Integer> {
    int maximumBorrowId() throws SQLException;
}
