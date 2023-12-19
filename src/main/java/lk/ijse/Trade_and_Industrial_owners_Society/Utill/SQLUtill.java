package lk.ijse.Trade_and_Industrial_owners_Society.Utill;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtill {
    public static <T>T execute(String sql, Object...arg) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < arg.length; i++) {
            statement.setObject((i+1),arg[i]);
        }
        if (sql.startsWith("SELECT") || sql.startsWith("select")){
            return (T) statement.executeQuery();
        }else {
            return(T)(Boolean)(statement.executeUpdate()>0);
        }
    }
}
