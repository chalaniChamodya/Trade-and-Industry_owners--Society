package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.FundingProgramDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.FundingProgramModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FundingProgramUpdateFormController {
    private static String id;
    public AnchorPane pagingPane;
    public JFXButton btnSponsor;
    public JFXButton btnFundingProgram;
    public VBox vBox;
    public Label lblProgramId;
    public TextField txtProgramName;
    public DatePicker dateDate;
    public TextField txtLocation;
    public TextField txtIncome;
    public TextField txtExpenditure;
    public TextField txtDescription;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;
    FundingProgramModel programModel = new FundingProgramModel();

    public void initialize() throws SQLException {
        setData();
        setProgramId();
        getAllId();
    }

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        FundingProgramModel programModel = new FundingProgramModel();
        list = programModel.getAllProgramId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(FundingProgramFormController.class.getResource("/View/FundingProgramBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            FundingProgramBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProgramId() {
        lblProgramId.setText(id);
    }

    private void setData() {
        FundingProgramDto programDto = null;
        try {
            programDto = programModel.getDataToUpdateForm(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lblProgramId.setText(id);
        txtProgramName.setText(programDto.getProgram_name());
        txtLocation.setText(programDto.getLocation());
        dateDate.setValue(LocalDate.parse(programDto.getDate()));
        txtDescription.setText(programDto.getDescription());
        txtIncome.setText(programDto.getIncome());
        txtExpenditure.setText(programDto.getExpenditure());
    }

    public static void getId(String id) {
        FundingProgramUpdateFormController.id = id;
    }

    public void btnSponsorOnAction(ActionEvent actionEvent) {
    }

    public void btnFundingProgramOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        FundingProgramDto programDto = new FundingProgramDto();
        programDto.setProgram_id(id);
        programDto.setProgram_name(txtProgramName.getText());
        programDto.setDate(String.valueOf(dateDate.getValue()));
        programDto.setDescription(txtDescription.getText());
        programDto.setLocation(txtLocation.getText());
        programDto.setIncome(txtIncome.getText());
        programDto.setExpenditure(txtExpenditure.getText());

        boolean isUpdated = programModel.updateFundingProgram(programDto);
        if(isUpdated){
            clearFeilds();
            new Alert(Alert.AlertType.CONFIRMATION,"Program Updated!").show();
            Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
        }else{
            new Alert(Alert.AlertType.ERROR,"Doesn't Updated!").show();
        }
        getAllId();
    }

    private void clearFeilds() {
        lblProgramId.setText("");
        txtProgramName.setText("");
        txtDescription.setText("");
        txtIncome.setText("");
        txtExpenditure.setText("");
        txtLocation.setText("");
        //dateDate.setValue("");
    }
    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
    }
}
