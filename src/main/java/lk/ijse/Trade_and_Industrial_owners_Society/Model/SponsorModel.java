package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.SponsorDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.SponsorTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SponsorModel {
    public String generateNextSponsorId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT sponsor_id FROM sponsor ORDER BY sponsor_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentSponsorId = null;

        if(resultSet.next()){
            currentSponsorId = resultSet.getString(1);
            return splitSponsorId(currentSponsorId);
        }
        return splitSponsorId(null);
    }

    private String splitSponsorId(String currentSponsorId) {
        if(currentSponsorId != null){
            String[] split = currentSponsorId.split("S");
            int id = Integer.parseInt(split[1]);

            if(id < 10){
                id++;
                return "S00" + id;
            }else if(id < 100){
                id++;
                return "S0" + id;
            }else{
                id++;
                return "S" + id;
            }
        }
        return "S001";
    }

    public boolean isSaved(SponsorDto sponsorDto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO sponsor VALUES (?, ?, ?, ?, ?, ?)",
                sponsorDto.getSponsor_id(),
                sponsorDto.getProgram_id(),
                sponsorDto.getSponsor_name(),
                sponsorDto.getDescription(),
                sponsorDto.getDate(),
                sponsorDto.getAmount()
        );
    }

    public boolean deleteSponsor(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM sponsor WHERE sponsor_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateSponsor(final SponsorDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE sponsor SET program_id = ?, name = ?, description = ?, date = ?, amount = ? WHERE sponsor_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProgram_id());
        pstm.setString(2, dto.getSponsor_name());
        pstm.setString(3, dto.getDescription());
        pstm.setString(4, String.valueOf(dto.getDate()));
        pstm.setString(5, dto.getAmount());
        pstm.setString(6, dto.getSponsor_id());

        return pstm.executeUpdate() > 0;
    }

    public ArrayList<String> getAllSponsorId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT sponsor_id FROM sponsor ORDER BY LENGTH(sponsor_id),sponsor_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public SponsorTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM sponsor WHERE sponsor_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();
        SponsorTm sponsorTm = new SponsorTm();

        if(resultSet.next()){
            sponsorTm.setSponsor_id(resultSet.getString(1));
            sponsorTm.setProgram_id(resultSet.getString(2));
            sponsorTm.setSponsor_name(resultSet.getString(3));
        }
        return sponsorTm;
    }

    public ArrayList<String> getAllProgramId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        ArrayList<String> programId = new ArrayList<>();

        String sql = "SELECT program_id FROM funding_program";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            programId.add(resultSet.getString(1));
        }

        return programId;
    }
}
