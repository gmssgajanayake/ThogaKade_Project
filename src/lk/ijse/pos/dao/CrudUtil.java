package lk.ijse.pos.dao;

import lk.ijse.pos.db.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class CrudUtil {
    public static <T>T execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i=0; i < params.length; i++){
            preparedStatement.setObject(i+1,params[i]);
        }
        return sql.startsWith("SELECT") ? (T)preparedStatement.executeQuery()
                : (T) (Boolean)(preparedStatement.executeUpdate()>0);
    }
}
