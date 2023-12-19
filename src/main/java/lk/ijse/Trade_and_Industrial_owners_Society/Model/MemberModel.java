package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.MemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MemberTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MemberModel {
    public boolean deleteMember(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM member WHERE member_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean saveMember(MemberDto memberDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                memberDto.getMember_id(),
                memberDto.getName_with_initials(),
                memberDto.getFull_name(),
                memberDto.getBusiness_address(),
                memberDto.getPersonal_address(),
                memberDto.getBusiness_type(),
                memberDto.getNic(),
                memberDto.getEmail(),
                memberDto.getDate_of_birth(),
                memberDto.getPersonal_contact_num(),
                memberDto.getBusiness_contact_num(),
                memberDto.getAdmission_fee(),
                memberDto.getJoined_date()
        );
    }


    public MemberTm searchMember(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member WHERE member_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        MemberTm memberTm = null;

        if(resultSet.next()){
            String member_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String personal_contact_num = resultSet.getString(10);
            String business_type = resultSet.getString(6);
            String nic = resultSet.getString(7);

            memberTm = new MemberTm(member_id, name, personal_contact_num, business_type, nic);
        }
        return memberTm;
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

    public MemberTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        MemberTm memberTm = new MemberTm();

        if(resultSet.next()){
            memberTm.setMember_id(resultSet.getString(1));
            memberTm.setName(resultSet.getString(3));
            memberTm.setPersonal_contact_num(resultSet.getString(10));
            memberTm.setBusiness_type(resultSet.getString(6));
            memberTm.setNic(resultSet.getString(7));
        }
        return memberTm;
    }

    public String generateNextMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT member_id FROM member ORDER BY member_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentMemberId = null;

        if(resultSet.next()){
            currentMemberId = resultSet.getString(1);
            return splitMemberId(currentMemberId);
        }
        return splitMemberId(null);
    }

    private String splitMemberId(String currentMemberId) {
        if(currentMemberId != null){
            String[] split = currentMemberId.split("M");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "M00" + id;
            }else if(id < 100){
                id++;
                return "M0" + id;
            }else{
                id++;
                return "M"+id;
            }
        }
        return "M001";
    }

    public boolean updateMember(MemberDto memberDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE member SET name_with_initials =?, full_name = ?, business_address = ?, personal_address = ?, business_type = ?, nic = ?, email = ?, date_of_birth =?, personal_contact_num = ?, business_contact_num =?, admission_fee= ?, joined_date = ? WHERE member_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,memberDto.getName_with_initials());
        pstm.setString(2,memberDto.getFull_name());
        pstm.setString(3,memberDto.getBusiness_address());
        pstm.setString(4,memberDto.getPersonal_address());
        pstm.setString(5,memberDto.getBusiness_type());
        pstm.setString(6,memberDto.getNic());
        pstm.setString(7,memberDto.getEmail());
        pstm.setString(8, String.valueOf(memberDto.getDate_of_birth()));
        pstm.setString(9,memberDto.getBusiness_contact_num());
        pstm.setString(10,memberDto.getBusiness_contact_num());
        pstm.setString(11,memberDto.getAdmission_fee());
        pstm.setString(12,memberDto.getMember_id());
        pstm.setString(13,memberDto.getJoined_date());

        return pstm.executeUpdate() > 0;
    }

    public MemberDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        MemberDto memberDto = new MemberDto();

        if(resultSet.next()){
            memberDto.setMember_id(id);
            memberDto.setName_with_initials(resultSet.getString(2));
            memberDto.setFull_name(resultSet.getString(3));
            memberDto.setBusiness_address(resultSet.getString(4));
            memberDto.setPersonal_address(resultSet.getString(5));
            memberDto.setBusiness_type(resultSet.getString(6));
            memberDto.setNic(resultSet.getString(7));
            memberDto.setEmail(resultSet.getString(8));
            memberDto.setDate_of_birth(String.valueOf(resultSet.getDate(9)));
            memberDto.setPersonal_contact_num(resultSet.getString(10));
            memberDto.setBusiness_contact_num(resultSet.getString(11));
            memberDto.setAdmission_fee(resultSet.getString(12));
            memberDto.setJoined_date(resultSet.getString(13));
        }
        return memberDto;
    }

    public String getEmailAddress(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        String email = null;

        if(resultSet.next()){
            email = resultSet.getString(8);
        }
        return email;
    }

    public ArrayList<String> getAllEmailAddress() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String date = currentMonth.format(formatter);

        String sql = "SELECT m.email FROM member m LEFT JOIN subscription_fee sf ON m.member_id = sf.member_id WHERE sf.date IS NULL OR DATE_FORMAT(sf.date,'%Y-%m') != ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }


    public MemberTm getSearchData(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM member WHERE name_with_initials LIKE ?";
        String SearchName = name +"%";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,SearchName);

        ResultSet resultSet = pstm.executeQuery();

        MemberTm memberTm = new MemberTm();

        if(resultSet.next()){
            memberTm.setMember_id(resultSet.getString(1));
            memberTm.setName(resultSet.getString(3));
            memberTm.setPersonal_contact_num(resultSet.getString(10));
            memberTm.setBusiness_type(resultSet.getString(6));
            memberTm.setNic(resultSet.getString(7));
        }
        return memberTm;
    }

    public ArrayList<String> getAllMemberEmailAddress() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        YearMonth currentMonth = YearMonth.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String date = currentMonth.format(formatter);

        String sql = "SELECT m.email FROM member m LEFT JOIN member_fee mf ON m.member_id = mf.member_id WHERE mf.date IS NULL OR DATE_FORMAT(mf.date,'%Y-%m') != ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }
}
