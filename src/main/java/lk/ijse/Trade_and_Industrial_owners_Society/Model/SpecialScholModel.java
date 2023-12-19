package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.SpecialScholDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.DonationTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpecialScholModel {
    public Map<String,Double> calculateMeetingAttendance() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        Map<String, Double> meetingAttendance = new HashMap<>();

        String sql = "SELECT COUNT(*) AS total_meetings FROM general_meeting";

        try(Statement statement = connection.createStatement()) {
            ResultSet totalMeetingResultSet = statement.executeQuery(sql);

            int totalMeeting = 0;
            if(totalMeetingResultSet.next()){
                totalMeeting = totalMeetingResultSet.getInt("total_meetings");
                System.out.println("Total meetings " + totalMeeting);
            }

            ResultSet attendanceResultSet = getAttendance();
            if (attendanceResultSet.next()){
                String member_id = attendanceResultSet.getString("member_id");
                int member_meetings = attendanceResultSet.getInt("total_meetings");
                System.out.println(member_id + "  " + member_meetings);

                double attendancePercentage = (double) member_meetings/totalMeeting * 100;
                System.out.println(attendancePercentage);
                meetingAttendance.put(member_id, attendancePercentage);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return meetingAttendance;
    }

    private ResultSet getAttendance() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql1 = "SELECT member_id, COUNT(*) AS total_meetings FROM general_attendance GROUP BY member_id";
        PreparedStatement pstm = connection.prepareStatement(sql1);

        ResultSet attendanceResultSet = pstm.executeQuery();
        return attendanceResultSet;
    }

    public Map<String, LocalDate> calculateMemberDuration() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        Map<String, LocalDate> memberJoinDates = new HashMap<>();

        String sql = "SELECT member_id, joined_date FROM member";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()){
            String MemberId = resultSet.getString("member_id");
            LocalDate joinedDate = resultSet.getDate("joined_date").toLocalDate();
            memberJoinDates.put(MemberId, joinedDate);
        }
        return memberJoinDates;
    }

    public DonationTm getScholarshipData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM special_schol WHERE schol_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        DonationTm donationTm = new DonationTm();

        if(resultSet.next()){
            donationTm.setId(resultSet.getString("schol_id"));
            donationTm.setDate(resultSet.getString("date"));
            donationTm.setAmount(resultSet.getString("amount"));
        }
        return donationTm;
    }

    public String generateNextScholId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT schol_id FROM special_schol ORDER BY schol_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentDonationId = null;

        if(resultSet.next()){
            currentDonationId = resultSet.getString(1);
            return splitScholId(currentDonationId);
        }
        return splitScholId(null);
    }

    private String splitScholId(String currentSScholId) {
        if(currentSScholId != null){
            String[] split = currentSScholId.split("SS");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "SS00" + id;
            }else if(id < 100){
                id++;
                return "SS0" + id;
            }else{
                id++;
                return "SS"+id;
            }
        }
        return "SS001";
    }

    public ArrayList<String> getAllScholId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT schol_id FROM special_schol ORDER BY LENGTH(schol_id),schol_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public boolean isSaved(SpecialScholDto scholDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO special_schol VALUES(?, ?, ?, ?, ?)",
                scholDto.getSchol_id(),
                scholDto.getMember_id(),
                scholDto.getMember_name(),
                scholDto.getDate(),
                scholDto.getAmount()
        );
    }
}
