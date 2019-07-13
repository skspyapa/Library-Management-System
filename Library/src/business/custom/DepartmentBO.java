package business.custom;


import business.SuperBO;
import dto.DepartmentDTO;

import java.sql.SQLException;

public interface DepartmentBO extends SuperBO<DepartmentDTO, Integer> {
    int getMaximumBorrowId() throws SQLException;
}
