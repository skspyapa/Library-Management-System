package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CategoryDAO;
import entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOimpl implements CategoryDAO {

    @Override
    public boolean save(Category category) throws SQLException {
        return CrudUtil.execute("INSERT INTO books VALUES (?,?)", category.getCategoryId(), category.getName());
    }

    @Override
    public boolean update(Category category) throws SQLException {
        return CrudUtil.execute("UPDATE category SET name=? WHERE categoryId=?", category.getName(), category.getCategoryId());
    }

    @Override
    public boolean delete(Integer categoryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM category WHERE categoryId=?", categoryId);
    }

    @Override
    public Category find(Integer categoryId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM category WHERE categoryId=?", categoryId);
        return rst.next() ? new Category(rst.getInt(1), rst.getString(2)) : null;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM category");
        List<Category> categories = new ArrayList<>();
        while (rst.next()) {
            categories.add(new Category(rst.getInt(1), rst.getString(2)));
        }
        return categories;
    }

    @Override
    public int maximumBorrowId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(categoryId) FROM category");
        return rst.next() ? rst.getInt(1) : 0;
    }
}
