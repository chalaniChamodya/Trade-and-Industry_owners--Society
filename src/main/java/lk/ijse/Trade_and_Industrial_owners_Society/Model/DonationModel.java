package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.DonationDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.ScholarshipDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.DonationTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonationModel {

    FamilyMemberModel familyMemberModel = new FamilyMemberModel();

    public String generateNextDeathBenefitId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT death_benefit_id FROM death_benefit ORDER BY death_benefit_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentDonationId = null;

        if(resultSet.next()){
            currentDonationId = resultSet.getString(1);
            return splitDeathBenefitId(currentDonationId);
        }
        return splitDeathBenefitId(null);
    }

    private String splitDeathBenefitId(String currentDonationId) {
        if(currentDonationId != null){
            String[] split = currentDonationId.split("DB");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "DB00" + id;
            }else if(id < 100){
                id++;
                return "DB0" + id;
            }else{
                id++;
                return "DB"+id;
            }
        }
        return "DB001";
    }

    public boolean isSaved(DonationDto donationDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        boolean result = false;
        try {
            connection.setAutoCommit(false);

            boolean isSavedDonation = saveDonation(donationDto);
            System.out.println(isSavedDonation);
            connection.commit();
            if(isSavedDonation){
                boolean isUpdated = familyMemberModel.updateIsAlive(donationDto.getFamily_member_id());
                if(isUpdated){
                    connection.commit();
                    result = true;
                }
            }
        }catch (SQLException e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    public boolean saveDonation(DonationDto donationDto) throws SQLException {
        System.out.println(donationDto);

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO death_benefit VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,donationDto.getDonation_id());
            pstm.setString(2,donationDto.getDate());
            pstm.setString(3,donationDto.getAmount());
            //pstm.setString(4,donationDto.getMember_id());
            pstm.setString(4,donationDto.getFamily_member_id());

            //System.out.println(pstm.executeUpdate());
            return pstm.executeUpdate() > 0;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean deleteDeathBenefit(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM death_benefit WHERE death_benefit_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateDeathBenefit(final DonationDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE death_benefit SET date = ?, amount = ?, family_mem_id = ? WHERE death_benefit_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(dto.getDate()));
        preparedStatement.setString(2, dto.getAmount());
      //  preparedStatement.setString(3,dto.getMember_id());
        preparedStatement.setString(3, dto.getFamily_member_id());
        preparedStatement.setString(4, dto.getDonation_id());

        return preparedStatement.executeUpdate() > 0;
    }

    public DonationTm getDeathBenefitData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM death_benefit WHERE death_benefit_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        DonationTm donationTm = new DonationTm();

        if(resultSet.next()){
            donationTm.setId(resultSet.getString(1));
            donationTm.setDate(resultSet.getString(2));
            donationTm.setAmount(resultSet.getString(3));
        }
        return donationTm;
    }

    public ArrayList<String> getAllDeathBenefitId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT death_benefit_id FROM death_benefit ORDER BY LENGTH(death_benefit_id),death_benefit_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public String generateNextScholId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT scholarship_id FROM scholarship ORDER BY scholarship_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentScholId = null;

        if(resultSet.next()){
            currentScholId = resultSet.getString(1);
            return splitScholId(currentScholId);
        }
        return splitScholId(null);
    }

    private String splitScholId(String currentScholId) {
        if(currentScholId != null){
            String[] split = currentScholId.split("S");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "S00" + id;
            }else if(id < 100){
                id++;
                return "S0" + id;
            }else{
                id++;
                return "S"+id;
            }
        }
        return "S001";
    }

    public ArrayList<String> getAllScholarshipId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT scholarship_id FROM scholarship ORDER BY LENGTH(scholarship_id),scholarship_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public DonationTm geScholarshipData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM scholarship WHERE scholarship_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        DonationTm donationTm = new DonationTm();

        if(resultSet.next()){
            donationTm.setId(resultSet.getString(1));
            donationTm.setDate(resultSet.getString(2));
            donationTm.setAmount(resultSet.getString(3));
        }
        return donationTm;
    }

    public boolean isSavedSchol(ScholarshipDto scholarshipDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO scholarship VALUES(?, ?, ?, ?)",
                scholarshipDto.getDonation_id(),
                scholarshipDto.getDate(),
                scholarshipDto.getAmount(),
                scholarshipDto.getFamily_member_id()
        );
    }

    public boolean deleteScholarship(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM scholarship WHERE scholarship_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateScholarship(final DonationDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE scholarship SET date = ?, amount = ?, family_mem_id = ? WHERE scholarship_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, dto.getDate());
        preparedStatement.setString(2, dto.getAmount());
        preparedStatement.setString(3, dto.getFamily_member_id());
        preparedStatement.setString(4, dto.getDonation_id());

        return preparedStatement.executeUpdate() > 0;
    }

    public ArrayList<String> getAllFamMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        ArrayList<String> famMemId = new ArrayList<>();

        String sql = "SELECT family_mem_id FROM member_family WHERE isAlive = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        String isAlive = "yes";
        pstm.setString(1,isAlive);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            famMemId.add(resultSet.getString(1));
        }

        return famMemId;
    }

    public ArrayList<String> getAllFamlyMemberId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        ArrayList<String> famMemId = new ArrayList<>();

        String sql = "SELECT family_mem_id FROM member_family";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            famMemId.add(resultSet.getString(1));
        }

        return famMemId;
    }
}
