package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.CommitteeMeetingDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommitteeMeetingModel {
    public static String generateNextCommitteeMeetingId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT com_meeting_id FROM committee_meeting ORDER BY com_meeting_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCommitteeMeetingId = null;

        if(resultSet.next()){
            currentCommitteeMeetingId = resultSet.getString(1);
            return splitCommitteeMeetingId(currentCommitteeMeetingId);
        }
        return splitCommitteeMeetingId(null);
    }

    private static String splitCommitteeMeetingId(String currentCommitteeMeetingId) {
        if(currentCommitteeMeetingId != null){
            String[] split = currentCommitteeMeetingId.split("C");
            int id = Integer.parseInt(split[1]);

            if(id<10){
                id++;
                return "C00" + id;
            }else if(id<100){
                id++;
                return "C0" + id;
            }else{
                id++;
                return "C" + id;
            }
        }
        return "C001";
    }

    public static boolean isSaved(CommitteeMeetingDto meetingDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO committee_meeting VALUES (?, ?, ?, ?, ?)",
                meetingDto.getCommittee_meeting_id(),
                meetingDto.getDate(),
                meetingDto.getTime(),
                meetingDto.getDescription(),
                meetingDto.getLocation()
        );
    }

    public boolean deleteCommitteeMeeting(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM committee_meeting WHERE com_meeting_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateCommitteeMeeting(final CommitteeMeetingDto committeeMeetingDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE committee_meeting SET date = ?, time = ?, description = ?, location = ? WHERE com_meeting_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(committeeMeetingDto.getDate()));
        preparedStatement.setString(2, String.valueOf(committeeMeetingDto.getTime()));
        preparedStatement.setString(3, committeeMeetingDto.getDescription());
        preparedStatement.setString(4, committeeMeetingDto.getLocation());
        preparedStatement.setString(5, committeeMeetingDto.getCommittee_meeting_id());

        return preparedStatement.executeUpdate() > 0;
    }

    public ArrayList<String> getAllMeetingId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT com_meeting_id FROM committee_meeting ORDER BY LENGTH(com_meeting_id),com_meeting_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public MeetingTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM committee_meeting WHERE com_meeting_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        MeetingTm meetingTm = new MeetingTm();

        if(resultSet.next()){
            meetingTm.setMeeting_id(resultSet.getString(1));
            meetingTm.setDate(resultSet.getString(2));
            // meetingTm.setTime(resultSet.getString(4));
            meetingTm.setLocation(resultSet.getString(5));
        }
        return meetingTm;
    }

    public CommitteeMeetingDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM committee_meeting WHERE com_meeting_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        CommitteeMeetingDto meetingDto = new CommitteeMeetingDto();

        if(resultSet.next()){
            meetingDto.setCommittee_meeting_id(id);
            meetingDto.setDate(resultSet.getString(2));
            meetingDto.setTime(resultSet.getString(3));
            meetingDto.setDescription(resultSet.getString(4));
            meetingDto.setLocation(resultSet.getString(5));
        }
        return meetingDto;
    }

    public ArrayList<String> getMailAddress() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        ArrayList<String> list = new ArrayList<>();

        String sql = "SELECT m.email" +
                "FROM committee_member cm" +
                "JOIN member m ON cm.member_id = m.member_id";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }
}
