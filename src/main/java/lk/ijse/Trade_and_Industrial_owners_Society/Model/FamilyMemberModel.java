package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.FamilyMemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.FamilyMemberTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FamilyMemberModel {
    public FamilyMemberTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member_family WHERE family_mem_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        FamilyMemberTm memberTm = new FamilyMemberTm();

        if(resultSet.next()){
            memberTm.setMember_id(resultSet.getString(2));
            memberTm.setId(resultSet.getString(1));
            memberTm.setRelationship(resultSet.getString(4));
            memberTm.setIsAlive(resultSet.getString(7));
        }
        return memberTm;
    }

    public ArrayList<String> getAllFamilyMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT family_mem_id FROM member_family ORDER BY LENGTH(family_mem_id),family_mem_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String generateNextFamilyMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT family_mem_id FROM member_family ORDER BY family_mem_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentFamilyMemberId = null;

        if(resultSet.next()){
            currentFamilyMemberId = resultSet.getString(1);
            return splitFamilyMemberId(currentFamilyMemberId);
        }
        return splitFamilyMemberId(null);
    }

    private String splitFamilyMemberId(String currentFamilyMemberId) {
        if(currentFamilyMemberId != null){
            String[] split = currentFamilyMemberId.split("FM");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "FM00" + id;
            }else if(id < 100){
                id++;
                return "FM0" + id;
            }else{
                id++;
                return "FM"+id;
            }
        }
        return "FM001";
    }

    public boolean saveFamMember(FamilyMemberDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO member_family VALUES(?, ?, ?, ?, ?, ?, ?)",
                dto.getFamily_mem_id(),
                dto.getMember_id(),
                dto.getName(),
                dto.getRelationship(),
                dto.getOccupation(),
                dto.getDate_of_birth(),
                dto.getIsAlive()
        );
    }

    public boolean updateIsAlive(String family_member_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE member_family SET isAlive = ? WHERE family_mem_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        String isAlive = "No";

        pstm.setString(1,isAlive);
        pstm.setString(2,family_member_id);

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteFamilyMember(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM member_family WHERE family_mem_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateFamilyMember(FamilyMemberDto memberDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE member_family SET member_id =?, name = ?, relationship = ?, occupation = ?, date_of_birth = ?, isAlive = ? WHERE family_mem_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,memberDto.getMember_id());
        pstm.setString(2,memberDto.getName());
        pstm.setString(3, memberDto.getRelationship());
        pstm.setString(4,memberDto.getOccupation());
        pstm.setString(5,memberDto.getDate_of_birth());
        pstm.setString(6,memberDto.getIsAlive());
        pstm.setString(7,memberDto.getFamily_mem_id());

        return pstm.executeUpdate() > 0;
    }

    public FamilyMemberDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member_family WHERE family_mem_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        FamilyMemberDto memberDto = new FamilyMemberDto();
        if(resultSet.next()){
            memberDto.setFamily_mem_id(id);
            memberDto.setMember_id(resultSet.getString(2));
            memberDto.setName(resultSet.getString(3));
            memberDto.setRelationship(resultSet.getString(4));
            memberDto.setOccupation(resultSet.getString(5));
            memberDto.setDate_of_birth(resultSet.getString(6));
            memberDto.setIsAlive(resultSet.getString(7));
        }
        return memberDto;
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
}
