package business.custom;

import business.SuperBO;
import dto.CategoryDTO;

import java.sql.SQLException;

public interface CategoryBO extends SuperBO<CategoryDTO, Integer> {
    int getMaximumBorrowId() throws SQLException;
}
