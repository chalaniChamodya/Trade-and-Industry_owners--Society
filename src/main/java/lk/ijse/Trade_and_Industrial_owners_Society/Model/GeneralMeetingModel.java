package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.GeneralMeetingDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneralMeetingModel {
    public static String generateNextGeneralMeetingID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT gen_meeting_id FROM general_meeting ORDER BY gen_meeting_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentGeneralMeetingId = null;

        if(resultSet.next()){
            currentGeneralMeetingId = resultSet.getString(1);
            return splitGeneralMeetingId(currentGeneralMeetingId);
        }
        return splitGeneralMeetingId(null);
    }

    private static String splitGeneralMeetingId(String currentGeneralMeetingId) {
        if(currentGeneralMeetingId != null){
            String[] split = currentGeneralMeetingId.split("G");
            int id = Integer.parseInt(split[1]);
            if(id<10){
                id++;
                return "G00" + id;
            }else if(id<100){
                id++;
                return "G0" + id;
            }else{
                id++;
                return "G" + id;
            }
        }
        return "G001";
    }

    public static boolean isSaved(GeneralMeetingDto meetingDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO general_meeting VALUES (?, ?, ?, ?, ?)",
                meetingDto.getGeneral_meeting_id(),
                meetingDto.getDate(),
                meetingDto.getTime(),
                meetingDto.getDescription(),
                meetingDto.getLocation()
        );
    }

    public boolean deleteGeneralMeeting(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM general_meeting WHERE gen_meeting_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateGeneralMeeting(final GeneralMeetingDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE general_meeting SET date = ?, time = ?, description = ?, location = ? WHERE gen_meeting_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(dto.getDate()));
        preparedStatement.setString(2, String.valueOf(dto.getTime()));
        preparedStatement.setString(3, dto.getDescription());
        preparedStatement.setString(4, dto.getLocation());
        preparedStatement.setString(5, dto.getGeneral_meeting_id());

        return preparedStatement.executeUpdate() > 0;
    }

    public ArrayList<String> getAllMeetingId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT gen_meeting_id FROM general_meeting ORDER BY LENGTH(gen_meeting_id),gen_meeting_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
            System.out.println(resultSet.getString(1));
        }
        return list;
    }

    public MeetingTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM general_meeting WHERE gen_meeting_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        MeetingTm meetingTm = new MeetingTm();

        if(resultSet.next()){
            meetingTm.setMeeting_id(resultSet.getString(1));
            meetingTm.setDate(resultSet.getString(2));
            //meetingTm.setTime(resultSet.getString(4));
            meetingTm.setLocation(resultSet.getString(5));
        }
        return meetingTm;
    }

    public GeneralMeetingDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM general_meeting WHERE gen_meeting_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        GeneralMeetingDto meetingDto = new GeneralMeetingDto();

        if(resultSet.next()){
            meetingDto.setGeneral_meeting_id(id);
            meetingDto.setDate(resultSet.getString(2));
            meetingDto.setTime(resultSet.getString(3));
            meetingDto.setDescription(resultSet.getString(4));
            meetingDto.setLocation(resultSet.getString(5));
        }
        return meetingDto;
    }

    public ArrayList<String> getMailAddress() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT email FROM member";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        //System.out.println(resultSet);

        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
            System.out.println(resultSet.getString(1));
        }
        return list;
    }
}
