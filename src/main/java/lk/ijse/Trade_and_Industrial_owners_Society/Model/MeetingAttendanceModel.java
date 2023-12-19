package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.FamilyMemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.MeetingAttendanceDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingAttendanceTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MeetingAttendanceModel {
    public ArrayList<String> getAllGeneralMeetingId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT gen_meeting_id FROM general_attendance ORDER BY LENGTH(gen_meeting_id),gen_meeting_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public boolean isSavedGeneralMeetingAttendance(MeetingAttendanceDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO general_attendance VALUES(?, ?, ?, ?, ?)",
                dto.getMeeting_id(),
                dto.getMember_id(),
                dto.getMember_name(),
                dto.getDate(),
                dto.getTime()
        );
    }

    public MeetingAttendanceTm getGeneralData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM general_attendance WHERE gen_meeting_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        MeetingAttendanceTm attendanceTm = new MeetingAttendanceTm();

        if(resultSet.next()){
            attendanceTm.setMeeting_id(resultSet.getString(1));
            attendanceTm.setMember_id(resultSet.getString(2));
            attendanceTm.setName(resultSet.getString(3));
        }
        return attendanceTm;
    }

    public boolean deleteGeneralMeetingAttendance(String meetingId, String memberId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM general_attendance WHERE gen_meeting_id = ? AND member_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, meetingId);
        preparedStatement.setString(2, memberId);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateGeneralMeetingAttendance(FamilyMemberDto memberDto) throws SQLException, ClassNotFoundException {
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

    public ArrayList<String> getAllMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT member_id FROM member ORDER BY LENGTH(member_id),member_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String getTodayGeneralMeetingId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String id = null;
        LocalDate date = LocalDate.now();

        String sql ="SELECT gen_meeting_id FROM general_meeting WHERE date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, String.valueOf(date));

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            id = resultSet.getString(1);
        }
        return id;
    }

    public String getMemberName(String memberId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String name = null;

        String sql = "SELECT name_with_initials FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,memberId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }
}
