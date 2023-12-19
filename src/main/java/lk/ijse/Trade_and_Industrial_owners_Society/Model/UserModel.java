package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.UserDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static String generateNextUserId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentUserId = null;

        if(resultSet.next()){
            currentUserId = resultSet.getString(1);
            return splitUserId(currentUserId);
        }
        return splitUserId(null);
    }

    private static String splitUserId(String currentUserId) {
        if(currentUserId != null){
            String[] split = currentUserId.split("U");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "U00" + id;
            }else if(id < 100){
                id++;
                return "U0" + id;
            }else{
                id++;
                return "U"+id;
            }
        }
        return "U001";
    }

    public String checkUsernameAndPassword(String userName, String password) throws SQLException, ClassNotFoundException {

        ResultSet set = SQLUtill.execute("SELECT role FROM user WHERE username=? AND password=?", userName, password);

        if (set.next()) {
            return set.getString(1);
        } else {
            return "No";
        }
    }

    public boolean save(UserDto userDTO) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO user VALUES (?, ?, ?, ?, ?)",
                userDTO.getUser_id(),
                userDTO.getCom_mem_id(),
                userDTO.getRole(),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
    }

    public boolean deleteUser(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM user WHERE user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }
}
