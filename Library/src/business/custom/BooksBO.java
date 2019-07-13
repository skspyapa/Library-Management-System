package business.custom;

import business.SearchType;
import business.SuperBO;
import dto.BooksDTO;
import dto.QueryDTO;

import java.sql.SQLException;
import java.util.List;

public interface BooksBO extends SuperBO<BooksDTO, Integer> {

    String getBookName(Integer id) throws SQLException;

    <T> List<QueryDTO> searchBy(SearchType type, T parameter) throws SQLException;

    int getMaximumId() throws SQLException;
}
