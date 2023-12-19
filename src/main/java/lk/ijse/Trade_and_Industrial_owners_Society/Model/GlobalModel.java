package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;

import javax.naming.ldap.PagedResultsControl;
import java.awt.image.DataBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GlobalModel {
    public ArrayList<String> search(String searchTerm) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        ArrayList<String> searchList = new ArrayList<>();

        String sql = "SELECT * FROM member WHERE member_id = ? OR name_with_initials LIKE ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,searchTerm);
        pstm.setString(2,"%"+searchTerm+"%");

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String memberId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String joinedDate = resultSet.getString("joined_date");
            String businessType = resultSet.getString("business_type");

            searchList.add(memberId);
            searchList.add(name);
            searchList.add(joinedDate);
            searchList.add(businessType);
            searchList.add(resultSet.getString("business_contact_num"));
            searchList.add(resultSet.getString("business_address"));
        }
        return searchList;
    }
}
