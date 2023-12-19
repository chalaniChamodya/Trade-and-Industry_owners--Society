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
import java.util.ArrayList;

public class FundingProgramFormController {
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
    public JFXButton btnAdd;
    public JFXButton btnCancel;

    private static  FundingProgramFormController controller;
    FundingProgramModel fundingProgramModel = new FundingProgramModel();

    public FundingProgramFormController(){controller = this;}

    public static  FundingProgramFormController getInstance(){return controller;}

    void btnSelected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #533710;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #FFFFFF;"
        );
    }

    void btnUnselected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #E8E8E8;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #727374;"
        );
    }

    public void initialize() throws SQLException {
        getAllId();
        generateNextFundingProgramId();
        btnSelected(btnFundingProgram);
    }

    public void generateNextFundingProgramId() {
        try {
            lblProgramId.setText(fundingProgramModel.generateNextFundingProgramId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void btnSponsorOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"SponsorForm.fxml");
    }

    public void btnFundingProgramOnAction(ActionEvent actionEvent) {
        btnSelected(btnFundingProgram);
        btnUnselected(btnSponsor);
        btnUnselected(btnAdd);
        btnUnselected(btnCancel);
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        btnSelected(btnAdd);
        btnUnselected(btnCancel);
        btnSelected(btnFundingProgram);
        btnUnselected(btnSponsor);

        String program_id = lblProgramId.getText();
        String program_name = txtProgramName.getText();
        String description = txtDescription.getText();
        String date = String.valueOf(dateDate.getValue());
        String location = txtLocation.getText();
        String income = txtIncome.getText();
        String expenditure = txtExpenditure.getText();

        FundingProgramDto dto = new FundingProgramDto(program_id, program_name, description, date, location, income, expenditure);

        boolean isSaved = fundingProgramModel.isSaved(dto);

        if(isSaved){
            clearFields();
            Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
        }else{
            new Alert(Alert.AlertType.ERROR, "Doesn't saved yet !");
        }
    }

    private void clearFields() {
        txtProgramName.setText("");
        txtDescription.setText("");
        txtIncome.setText("");
        txtExpenditure.setText("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnSelected(btnCancel);
        btnUnselected(btnAdd);
        btnSelected(btnFundingProgram);
        btnUnselected(btnSponsor);

        clearFields();
    }
}
