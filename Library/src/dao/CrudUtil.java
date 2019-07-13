package dao;

import dbLibrary.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... params) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return (sql.trim().startsWith("SELECT")) ? (T) stmt.executeQuery() : (T) (Boolean) (stmt.executeUpdate() > 0);
    }

}
