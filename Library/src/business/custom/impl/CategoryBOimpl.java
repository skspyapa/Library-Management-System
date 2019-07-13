package business.custom.impl;

import business.custom.CategoryBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.CategoryDAO;
import dto.CategoryDTO;
import entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryBOimpl implements CategoryBO {
    private CategoryDAO categoryDAO = DAOFactory.getInstance().getDAO(DAOTypes.CATEGORY);

    @Override
    public List<CategoryDTO> getAll() throws SQLException {

        List<Category> all = categoryDAO.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : all) {
            categoryDTOS.add(new CategoryDTO(category.getCategoryId(), category.getName()));
        }
        return categoryDTOS;
    }

    @Override
    public boolean save(CategoryDTO dto) throws SQLException {
        return categoryDAO.save(new Category(dto.getCategoryId(),dto.getName()));
    }

    @Override
    public boolean remove(Integer dtoId) throws SQLException {
        return categoryDAO.delete(dtoId);
    }

    @Override
    public boolean update(CategoryDTO dto) throws SQLException {
        return categoryDAO.update(new Category(dto.getCategoryId(),dto.getName()));
    }

    @Override
    public CategoryDTO get(Integer dtoId) throws SQLException {
        Category category = categoryDAO.find(dtoId);

        return new CategoryDTO(category.getCategoryId(),category.getName());
    }

    @Override
    public int getMaximumBorrowId() throws SQLException {
        int maxId = categoryDAO.maximumBorrowId();
        return maxId + 1;
    }
}
