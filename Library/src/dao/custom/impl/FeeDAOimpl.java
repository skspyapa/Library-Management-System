package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.FeeDAO;
import entity.Fee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeeDAOimpl implements FeeDAO {

    @Override
    public boolean save(Fee fee) throws SQLException {
        return CrudUtil.execute("INSERT INTO fee VALUES(?,?)", fee.getBorrowId(), fee.getFine());
    }

    @Override
    public boolean update(Fee fee) throws SQLException {
        return CrudUtil.execute("UPDATE fee SET fine=? WHERE borrowId=?", fee.getFine(), fee.getBorrowId());
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM fee WHERE borrowId=?", id);
    }

    @Override
    public Fee find(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM fee WHERE borrowId=?", id);
        return rst.next() ? new Fee(rst.getInt(1), rst.getDouble(2)) : null;
    }

    @Override
    public List<Fee> findAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM fee WHERE borrowId=?");
        List<Fee> fees = new ArrayList<>();
        while (rst.next()) {
            fees.add(new Fee(rst.getInt(1), rst.getDouble(2)));
        }
        return fees;
    }
}
