package dao.custom;

import dao.CrudDAO;
import dao.SearchType;
import entity.Books;

import java.sql.SQLException;
import java.util.List;

public interface BooksDAO extends CrudDAO<Books, Integer> {
    String findName(Integer id) throws SQLException;

    <T> List<Books> searchBy(SearchType type, T parameter) throws SQLException;

    int findMaximumId() throws SQLException;
}
