package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.StudentsDAO;
import entity.Students;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAOimpl implements StudentsDAO {

    @Override
    public boolean save(Students students) throws SQLException {
        return CrudUtil.execute("INSERT INTO students VALUES (?,?,?,?,?)", students.getStudentId(), students.getName(), students.getBirthDay(), students.getGender(), students.getDepartmentId());
    }

    @Override
    public boolean update(Students students) throws SQLException {
        return CrudUtil.execute("UPDATE students SET name=?,birthDay=?,gender=?,departmentId=? WHERE studentId=?", students.getName(), students.getBirthDay(), students.getGender(), students.getDepartmentId(), students.getStudentId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM students WHERE studentId=? ", id);
    }

    @Override
    public Students find(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM students WHERE studentsId=?", id);
        return rst.next() ? new Students(rst.getInt(1), rst.getString(2), rst.getDate(3).toLocalDate(), rst.getString(4), rst.getInt(5)) : null;
    }

    @Override
    public List<Students> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM students");
        List<Students> students = new ArrayList<>();
        while (rst.next()) {
            students.add(new Students(rst.getInt(1), rst.getString(2), rst.getDate(3).toLocalDate(), rst.getString(4), rst.getInt(5)));
        }
        return students;
    }

    @Override
    public String findName(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT name FROM students WHERE studentId=?", id);
        return rst.next() ? rst.getString(1) : null;
    }

    @Override
    public int findMaximumId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT MAX(studentId) FROM students");
        return rst.next() ? rst.getInt(1) : 0;
    }
}
