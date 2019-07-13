package business.custom;

import business.QueryType;
import business.SuperBO;
import dto.BorrowDTO;
import dto.QueryDTO;

import java.sql.SQLException;
import java.util.List;

public interface BorrowBO extends SuperBO<BorrowDTO, Integer> {
    int getMaximumBorrowId() throws SQLException;

    List<QueryDTO> getBorrowDetails(Integer id) throws SQLException;

    boolean placeBorrow(QueryType queryType, BorrowDTO borrowDTO) throws Exception;

}
