package lk.ijse.Trade_and_Industrial_owners_Society.Model;

import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.FundingProgramDto;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.FundingProgramTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.SQLUtill;

import java.sql.*;
import java.util.ArrayList;

public class FundingProgramModel {
    public String generateNextFundingProgramId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT program_id FROM funding_program ORDER BY program_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentFundingProgramId = null;

        if(resultSet.next()){
            currentFundingProgramId = resultSet.getString(1);
            return splitFundingProgramId(currentFundingProgramId);
        }
        return splitFundingProgramId(null);
    }

    private String splitFundingProgramId(String currentFundingProgramId) {
        if(currentFundingProgramId != null){
            String[] split = currentFundingProgramId.split("FP");
            int id = Integer.parseInt(split[1]);
            if(id < 10){
                id++;
                return "FP00" + id;
            }else if(id < 100){
                id++;
                return "FP0" + id;
            }else{
                id++;
                return "FP"+id;
            }
        }
        return "FP001";
    }

    public boolean isSaved(FundingProgramDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("INSERT INTO funding_program VALUES(?, ?, ?, ?, ?, ?, ?)",
                dto.getProgram_id(),
                dto.getProgram_name(),
                dto.getDescription(),
                dto.getDate(),
                dto.getLocation(),
                dto.getIncome(),
                dto.getExpenditure()
        );
    }

    public boolean deleteFundingProgram(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM funding_program WHERE program_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateFundingProgram(final FundingProgramDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE funding_program SET name =?, description = ?, date = ?, location = ?, income =?, expenditure = ? WHERE program_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, dto.getProgram_name());
        preparedStatement.setString(2, dto.getDescription());
        preparedStatement.setDate(3, Date.valueOf(dto.getDate()));
        preparedStatement.setString(4, dto.getLocation());
        preparedStatement.setString(5, dto.getIncome());
        preparedStatement.setString(6, dto.getExpenditure());
        preparedStatement.setString(7, dto.getProgram_id());

        return preparedStatement.executeUpdate() > 0;
    }

    public ArrayList<String> getAllProgramId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT program_id FROM funding_program ORDER BY LENGTH(program_id),program_id";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public FundingProgramTm getData(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM funding_program WHERE program_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        FundingProgramTm programTm = new FundingProgramTm();

        if(resultSet.next()){
            programTm.setProgram_id(resultSet.getString(1));
            programTm.setProgram_name(resultSet.getString(2));
            programTm.setDate(resultSet.getString(4));
        }
        return programTm;
    }

    public FundingProgramDto getDataToUpdateForm(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM funding_program WHERE program_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();
        FundingProgramDto programDto = new FundingProgramDto();

        if(resultSet.next()){
            programDto.setProgram_id(id);
            programDto.setProgram_name(resultSet.getString(2));
            programDto.setDescription(resultSet.getString(3));
            programDto.setDate(String.valueOf(resultSet.getDate(4)));
            programDto.setLocation(resultSet.getString(5));
            programDto.setIncome(resultSet.getString(6));
            programDto.setExpenditure(resultSet.getString(7));
        }
        return programDto;
    }
}
