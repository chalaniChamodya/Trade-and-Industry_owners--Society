package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.MembershipFeeDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MembershipDueTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MembershipFeeModel {
    public static String generateNextDueId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT member_fee_id FROM member_fee ORDER BY member_fee_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentMemberFeeId = null;

        if(resultSet.next()){
            currentMemberFeeId = resultSet.getString(1);
            return splitMemberFeeId(currentMemberFeeId);
        }
        return splitMemberFeeId(null);
    }

    private static String splitMemberFeeId(String currentMemberFeeId) {
        if(currentMemberFeeId != null){
            String[] split = currentMemberFeeId.split("MF");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "MF00" + id;
            }else if(id < 100){
                id++;
                return "MF0" + id;
            }else{
                id++;
                return "MF"+id;
            }
        }
        return "MF001";
    }

    public static boolean isSaved(MembershipFeeDto membershipFeeDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO member_fee VALUES (?,?,?,?,?)",
                membershipFeeDto.getMember_fee_id(),
                membershipFeeDto.getMember_id(),
                membershipFeeDto.getMember_name(),
                membershipFeeDto.getDate(),
                membershipFeeDto.getAmount()
        );
    }

    public boolean deleteMembershipFee(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM member_fee WHERE member_fee_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateMembershipFee(final MembershipFeeDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE member_fee SET member_id = ?, member_name = ?, date = ?, amount = ? WHERE member_fee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMember_id());
        pstm.setString(2, dto.getMember_name());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4, dto.getAmount());
        pstm.setString(5, dto.getMember_fee_id());

        return pstm.executeUpdate() > 0;
    }

    public MembershipDueTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member_fee WHERE member_fee_id = ?";
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

    public ArrayList<String> getMembershipFeeId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT member_fee_id FROM member_fee ORDER BY LENGTH(member_fee_id),member_fee_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public MembershipFeeDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member_fee WHERE member_fee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();
        MembershipFeeDto feeDto = new MembershipFeeDto();

        if(resultSet.next()){
            feeDto.setMember_fee_id(resultSet.getString(1));
            feeDto.setMember_id(resultSet.getString(2));
            feeDto.setMember_name(resultSet.getString(3));
            feeDto.setDate(resultSet.getString(4));
            feeDto.setAmount(resultSet.getString(5));
        }
        return feeDto;
    }

    public String getMailAddress(String member_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,member_id);

        ResultSet resultSet = pstm.executeQuery();

        String email = null;

        if(resultSet.next()){
            email = resultSet.getString(9);
        }
        return email;
    }
}
