package dao.custom.impl;


import dao.CrudUtil;
import dao.custom.QueryDAO;
import entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOimpl implements QueryDAO {


    public List<CustomEntity> borrowdetails(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT distinct b.borrowId,b.bookId,bk.name,b.borrowDate FROM borrow b LEFT JOIN bookbrought bb ON (b.borrowId =bb.borrowId) INNER JOIN books bk ON (b.bookId=bk.bookId) WHERE bb.borrowId IS NULL AND b.studentId=? ORDER BY b.borrowId", (id + "%"));
        List<CustomEntity> borrowDetails = new ArrayList<>();
        while (rst.next()) {
            borrowDetails.add(new CustomEntity(rst.getInt(1), rst.getInt(2), rst.getString(3), rst.getDate(4).toLocalDate()));
        }
        return borrowDetails;
    }

    public List<CustomEntity> borrowDetailReport(Integer id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT distinct b.borrowId,b.bookId,bk.name,b.borrowDate FROM borrow b LEFT JOIN bookbrought bb ON (b.borrowId =bb.borrowId) INNER JOIN books bk ON (b.bookId=bk.bookId) WHERE bb.borrowId IS NULL ORDER BY b.borrowId");
        List<CustomEntity> borrowDetails = new ArrayList<>();
        while (rst.next()) {
            borrowDetails.add(new CustomEntity(rst.getInt(1), rst.getInt(2), rst.getString(3), rst.getDate(4).toLocalDate()));
        }
        return borrowDetails;
    }
}
