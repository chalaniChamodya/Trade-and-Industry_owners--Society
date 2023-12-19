package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.SubscriptionFeeDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MembershipDueTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SubscriptionFeeModel {
    public static String generateNextDueId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT subscription_fee_id FROM subscription_fee ORDER BY subscription_fee_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentSubscriptionFeeId = null;

        if(resultSet.next()){
            currentSubscriptionFeeId = resultSet.getString(1);
            return splitSubscriptionFeeId(currentSubscriptionFeeId);
        }
        return splitSubscriptionFeeId(null);
    }

    private static String splitSubscriptionFeeId(String currentSubscriptionFeeId) {
        if(currentSubscriptionFeeId != null){
            String[] split = currentSubscriptionFeeId.split("SF");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "SF00" + id;
            }else if(id < 100){
                id++;
                return "SF0" + id;
            }else{
                id++;
                return "SF"+id;
            }
        }
        return "SF001";
    }

    public boolean isSaved(SubscriptionFeeDto subscriptionFeeDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO subscription_fee VALUES (?,?,?,?,?)",
                subscriptionFeeDto.getSubscription_fee_id(),
                subscriptionFeeDto.getMember_id(),
                subscriptionFeeDto.getMember_name(),
                subscriptionFeeDto.getDate(),
                subscriptionFeeDto.getAmount()
        );
    }
    public boolean deleteSubscriptionFee(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM subscription_fee WHERE subscription_fee_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateSubscriptionFee(final SubscriptionFeeDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE subscription_fee SET member_id = ?, member_name = ?, date = ?, amount = ? WHERE subscription_fee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMember_id());
        pstm.setString(2, dto.getMember_name());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4, dto.getAmount());
        pstm.setString(5, dto.getSubscription_fee_id());

        return pstm.executeUpdate() > 0;
    }


    public ArrayList<String> getSubscriptionFeeId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT subscription_fee_id FROM subscription_fee ORDER BY LENGTH(subscription_fee_id),subscription_fee_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public MembershipDueTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM subscription_fee WHERE subscription_fee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        MembershipDueTm dueTm = new MembershipDueTm();

        if (resultSet.next()) {
            dueTm.setId(resultSet.getString(1));
            dueTm.setMember_name(resultSet.getString(3));
            dueTm.setDate(resultSet.getString(4));
        }
        return dueTm;
    }

    public SubscriptionFeeDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM subscription_fee WHERE subscription_fee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();
        SubscriptionFeeDto feeDto = new SubscriptionFeeDto();

        if(resultSet.next()){
            feeDto.setSubscription_fee_id(resultSet.getString(1));
            feeDto.setMember_id(resultSet.getString(2));
            feeDto.setMember_name(resultSet.getString(3));
            feeDto.setDate(resultSet.getString(4));
            feeDto.setAmount(resultSet.getString(5));
        }
        return feeDto;
    }

    public ArrayList<String> getAllMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        ArrayList<String> memberId = new ArrayList<>();

        String sql = "SELECT member_id FROM member";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            memberId.add(resultSet.getString(1));
        }
        System.out.println(memberId);
        return memberId;
    }

    public String getMemberName(String id) throws SQLException {
        String name = "";
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT name_with_initials FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }
}
