package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.DepartmentDAO;
import entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOimpl implements DepartmentDAO {

    @Override
    public boolean save(Department department) throws SQLException {
        return CrudUtil.execute("INSERT INTO department VALUES (?,?)", department.getDepartmentId(), department.getName());
    }

    @Override
    public boolean update(Department department) throws SQLException {
        return CrudUtil.execute("UPDATE department SET name=? WHERE departmentId=?", department.getName(), department.getDepartmentId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM department WHERE departmentId=?", id);
    }

    @Override
    public Department find(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM category WHERE categoryId=?", id);
        return rst.next() ? new Department(rst.getInt(1), rst.getString(2)) : null;
    }

    @Override
    public List<Department> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM department");
        List<Department> departments = new ArrayList<>();
        while (rst.next()) {
            departments.add(new Department(rst.getInt(1), rst.getString(2)));
        }
        return departments;
    }

    @Override
    public int maximumBorrowId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(departmentId) FROM department");
        return rst.next() ? rst.getInt(1) : 0;
    }
}
