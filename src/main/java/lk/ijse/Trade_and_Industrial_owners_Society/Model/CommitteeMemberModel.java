package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.CommitteeMemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.CommitteeMemberTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommitteeMemberModel {
    public CommitteeMemberTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM committee_member WHERE committee_mem_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        CommitteeMemberTm committeeMemberTm = new CommitteeMemberTm();

        if(resultSet.next()){
            committeeMemberTm.setCom_mem_id(resultSet.getString(1));
            committeeMemberTm.setName(resultSet.getString(3));
            committeeMemberTm.setPosition(resultSet.getString(4));
        }
        return committeeMemberTm;
    }

    public ArrayList<String> getAllCommitteeMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT committee_mem_id FROM committee_member ORDER BY LENGTH(committee_mem_id),committee_mem_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String generateNextCommitteMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT committee_mem_id FROM committee_member ORDER BY committee_mem_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCommitteeMemberId = null;

        if(resultSet.next()){
            currentCommitteeMemberId = resultSet.getString(1);
            return splitCommitteeMemberId(currentCommitteeMemberId);
        }
        return splitCommitteeMemberId(null);
    }

    private String splitCommitteeMemberId(String currentCommitteeMemberId) {
        if(currentCommitteeMemberId != null){
            String[] split = currentCommitteeMemberId.split("C");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "C00" + id;
            }else if(id < 100){
                id++;
                return "C0" + id;
            }else{
                id++;
                return "C"+id;
            }
        }
        return "C001";
    }

    public boolean saveCommitteeMember(CommitteeMemberDto committeeMemberDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO committee_member VALUES(?, ?, ?, ?, ?)",
                committeeMemberDto.getCom_mem_id(),
                committeeMemberDto.getMember_id(),
                committeeMemberDto.getName(),
                committeeMemberDto.getPosition(),
                committeeMemberDto.getDate()
        );
    }

    public boolean deleteCommitteeMember(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM commitee_member WHERE committee_mem_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateCommitteeMember(CommitteeMemberDto memberDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE committee_member SET member_id =?, name = ?, position = ?, date = ? WHERE committee_mem_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,memberDto.getMember_id());
        pstm.setString(2,memberDto.getName());
        pstm.setString(3,memberDto.getPosition());
        pstm.setString(4,memberDto.getDate());
        pstm.setString(5,memberDto.getCom_mem_id());

        return pstm.executeUpdate() > 0;
    }

    public CommitteeMemberDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM committee_member WHERE committee_mem_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        CommitteeMemberDto memberDto = new CommitteeMemberDto();

        if(resultSet.next()){
            memberDto.setCom_mem_id(id);
            memberDto.setMember_id(resultSet.getString(2));
            memberDto.setName(resultSet.getString(3));
            memberDto.setPosition(resultSet.getString(4));
            memberDto.setDate(resultSet.getString(5));
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

    public String getName(String id) throws SQLException {
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
