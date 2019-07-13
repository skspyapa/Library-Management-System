package dao.custom;

import dao.CrudDAO;
import entity.Students;

import java.sql.SQLException;

public interface StudentsDAO extends CrudDAO<Students, Integer> {
    String findName(Integer id) throws SQLException;

    int findMaximumId() throws SQLException;
}
