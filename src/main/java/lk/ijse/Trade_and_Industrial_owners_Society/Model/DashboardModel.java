package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingTm;

import java.sql.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashboardModel {
    public int  generalMeetingAttendanceCount () throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        int attendanceCount = 0;

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String sql = "SELECT COUNT(*) AS Attendance_count FROM general_attendance WHERE DATE_FORMAT(date, '%Y-%m') = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,currentMonth.format(formatter));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            attendanceCount = resultSet.getInt("Attendance_count");
            System.out.println("Monthly Attendance" + attendanceCount);
        }
        return attendanceCount;
    }

    public int  committeeMeetingAttendanceCount () throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        int attendanceCount = 0;

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String sql = "SELECT COUNT(*) AS Attendance_count FROM committee_attendance WHERE DATE_FORMAT(date, '%Y-%m') = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,currentMonth.format(formatter));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            attendanceCount = resultSet.getInt("Attendance_count");
            System.out.println("Monthly Attendance" + attendanceCount);
        }
        return attendanceCount;
    }

    public int unPaidSubscriptionFeeCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        int unPaidCount = 0;
        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String sql = "SELECT COUNT(*) AS unpaid_count FROM member WHERE member_id NOT IN (SELECT member_id FROM subscription_fee WHERE DATE_FORMAT(date, '%Y-%m') = ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, currentMonth.format(formatter));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            unPaidCount = resultSet.getInt("unpaid_count");
            System.out.println("Number of members with unpaid Subscription fee : "+ unPaidCount);
        }
        return unPaidCount;
    }

    public int unPaidMembershipFeeCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        int unpaidCount = 0;
        int currentYear = Year.now().getValue();

        String sql = "SELECT COUNT(*) AS unpaid_count FROM member WHERE member_id NOT IN (SELECT member_id FROM member_fee WHERE YEAR(date) = ?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, String.valueOf(currentYear));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            unpaidCount = resultSet.getInt("unpaid_count");
            System.out.println("Member count of unpaid membership fee : "+ unpaidCount);
        }
        return unpaidCount;
    }

    public ArrayList<String> getAllUnpaidSubscriptionFeeId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String date = currentMonth.format(formatter);

        String sql = "SELECT m.member_id FROM member m LEFT JOIN subscription_fee sf ON m.member_id = sf.member_id WHERE sf.date IS NULL OR DATE_FORMAT(sf.date,'%Y-%m') != ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public ObservableList<PieChart.Data> getFundDataForPieChart() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        ObservableList<PieChart.Data> fundData = FXCollections.observableArrayList();

        Statement subscriptionStatement = connection.createStatement();
        String subSql ="SELECT SUM(amount) AS total_subscription_fee FROM subscription_fee";
        ResultSet subscriptionResult = subscriptionStatement.executeQuery(subSql);
        if(subscriptionResult.next()){
            Double subscriptionTotal = subscriptionResult.getDouble(1);
            fundData.add(new PieChart.Data("Subscription Fees", subscriptionTotal));
        }

        Statement membershipStatement = connection.createStatement();
        String memSql ="SELECT SUM(amount) AS total_membership_fee FROM member_fee";
        ResultSet membershipResult = membershipStatement.executeQuery(memSql);
        if(membershipResult.next()){
            Double membershipTotal = membershipResult.getDouble(1);
            fundData.add(new PieChart.Data("Membership Fees", membershipTotal));
        }

        Statement programStatement = connection.createStatement();
        String proSql ="SELECT SUM(income) AS total_income FROM funding_program";
        ResultSet programResult = programStatement.executeQuery(proSql);
        if(programResult.next()){
            Double totalIncome = programResult.getDouble(1);
            fundData.add(new PieChart.Data("Funding Program", totalIncome));
        }

        return fundData;
    }

    public ObservableList<XYChart.Data<String, Number>> monthlyFund() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String currentMonthYear = currentMonth.format(monthYearFormatter);

        ObservableList<XYChart.Data<String, Number>> fundData = FXCollections.observableArrayList();

        //Statement subStatement = connection.createStatement();

        String subsql = "SELECT SUM(amount) AS total_subscription_fee FROM subscription_fee WHERE DATE_FORMAT(date, '%Y-%m') = ?";
        PreparedStatement subState = connection.prepareStatement(subsql);
        subState.setString(1, currentMonthYear);
        ResultSet subResult = subState.executeQuery();
        if(subResult.next()){
            fundData.add(new XYChart.Data<>("Subscription Fee", subResult.getDouble(1)));
        }

        String memsql = "SELECT SUM(amount) AS total_membership_fee FROM member_fee WHERE DATE_FORMAT(date, '%Y-%m') = ?";
        PreparedStatement memState = connection.prepareStatement(memsql);
        memState.setString(1, currentMonthYear);
        ResultSet memResult = subState.executeQuery();
        if(memResult.next()){
            fundData.add(new XYChart.Data<>("Membership Fee", memResult.getDouble(1)));
        }


//        ResultSet fundResult = statement.executeQuery("SELECT SUM(subscription_fee.amount), " +
//                "SUM(member_fee.amount), SUM(member.admission_fee) " +
//                "FROM subscription_fee, member_fee, funding_program, member " +
//                "WHERE subscription_fee.member_id = member.member_id AND " +
//                "member_fee.member_id = member.member_id AND " +
//                "subscription_fee.date = '" + currentMonthYear + "'");
//        if (fundResult.next()) {
//            fundData.addAll(
//                    new XYChart.Data<>("Subscription Fees", fundResult.getDouble(1)),
//                    new XYChart.Data<>("Membership Fees", fundResult.getDouble(2)),
//                    new XYChart.Data<>("Funding Program Income", fundResult.getDouble(3)),
//                    new XYChart.Data<>("Admission Fees", fundResult.getDouble(4))
//            );
//        }
        return fundData;
    }

    public ObservableList<XYChart.Data<String, Number>> monthlyExpense() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String currentMonthYear = currentMonth.format(monthYearFormatter);

        ObservableList<XYChart.Data<String, Number>> expenseData = FXCollections.observableArrayList();

        String deathsql = "SELECT SUM(amount) AS total_death_benefit FROM death_benefit WHERE DATE_FORMAT(date, '%Y-%m') = ?";
        PreparedStatement deathState = connection.prepareStatement(deathsql);
        deathState.setString(1, currentMonthYear);
        ResultSet deathResult = deathState.executeQuery();
        if(deathResult.next()){
            expenseData.add(new XYChart.Data<>("Death Benefit", deathResult.getDouble(1)));
        }

//        Statement statement = connection.createStatement();
//
//        ResultSet expenseResult = statement.executeQuery("SELECT SUM(death_benefit.amount), " +
//                "SUM(scholarship.amount), SUM(special_schol.amount) " +
//                "FROM death_benefit, scholarship, special_schol, member, member_family " +
//                "WHERE death_benefit.family_mem_id = member_family.family_mem_id AND " +
//                "scholarship.family_mem_id = member_family.family_mem_id AND " +
//                "special_schol.member_id = member.member_id AND " +
//                "death_benefit.date = '" + currentMonthYear + "'");
//        if (expenseResult.next()) {
//            expenseData.addAll(
//                    new XYChart.Data<>("Death Benefit", expenseResult.getDouble(1)),
//                    new XYChart.Data<>("Scholarships", expenseResult.getDouble(2)),
//                    new XYChart.Data<>("Special Scholarships", expenseResult.getDouble(3))
//            );
//        }
        return expenseData;
    }

    public ArrayList<String> getAllUnpaidMembershipFeeId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String date = currentMonth.format(formatter);

        String sql = "SELECT m.member_id FROM member m LEFT JOIN member_fee sf ON m.member_id = sf.member_id WHERE sf.date IS NULL OR DATE_FORMAT(sf.date,'%Y-%m') != ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public MeetingTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        if(id.startsWith("G")){
            String sql = "SELECT * FROM general_meeting WHERE gen_meeting_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();
            MeetingTm meetingTm = new MeetingTm();

            if(resultSet.next()){
                meetingTm.setMeeting_id(resultSet.getString(1));
                meetingTm.setDate(resultSet.getString(2));
                meetingTm.setLocation(resultSet.getString(3));
            }
            return meetingTm;
        }else{
            String sql = "SELECT * FROM committee_meeting WHERE com_meeting_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();
            MeetingTm meetingTm = new MeetingTm();

            if(resultSet.next()){
                meetingTm.setMeeting_id(resultSet.getString(1));
                meetingTm.setDate(resultSet.getString(2));
                meetingTm.setLocation(resultSet.getString(3));
            }
            return meetingTm;
        }
    }

    public ArrayList<String> getUpComingMeetingId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        ArrayList<String> list = new ArrayList<>();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String date = currentMonth.format(formatter);

        try {
            String sql1 = "SELECT * FROM general_meeting WHERE DATE_FORMAT(date, '%Y-%m') = ?";
            PreparedStatement pstm1 = connection.prepareStatement(sql1);
            pstm1.setString(1, date);

            ResultSet resultSet1 = pstm1.executeQuery();
            while (resultSet1.next()){
                list.add(resultSet1.getString(1));
            }
        }catch (Exception e){

        }

        try {
            String sql = "SELECT * FROM committee_meeting WHERE DATE_FORMAT(date, '%Y-%m') = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, date);

            ResultSet resultSet2 = pstm.getResultSet();
            while (resultSet2.next()){
                list.add(resultSet2.getString(1));
            }
        }catch (Exception e){}

        return list;
    }
}
