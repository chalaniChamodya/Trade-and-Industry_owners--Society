package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.SponsorDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.SponsorModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SponsorFormController {
    public AnchorPane pagingPane;
    public JFXButton btnSponsor;
    public JFXButton btnFundingProgram;
    public VBox vBox;
    public Label lblSponsorId;
    public DatePicker txtDate;
    public TextField txtSponsorName;
    public TextField txtDescription;
    public TextField txtAmount;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    public ComboBox txtProgramId;

    private static SponsorFormController controller;
    SponsorModel sponsorModel = new SponsorModel();

    public SponsorFormController(){controller = this;}

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
        generateNextSponsorId();
        btnSelected(btnSponsor);
        setDataInProgramIdComboBox();
    }

    private void setDataInProgramIdComboBox() throws SQLException {
        ArrayList<String> programId = sponsorModel.getAllProgramId();
        txtProgramId.getItems().addAll(programId);
    }

    private void getAllId() throws SQLException {
        ArrayList<String> list = null;
        SponsorModel sponsorModel = new SponsorModel();
        list = sponsorModel.getAllSponsorId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(SponsorFormController.class.getResource("/View/SponsorBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            SponsorBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateNextSponsorId() {
        try {
            lblSponsorId.setText(sponsorModel.generateNextSponsorId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSponsorOnAction(ActionEvent actionEvent) {
        btnSelected(btnSponsor);
        btnUnselected(btnFundingProgram);
        btnUnselected(btnAdd);
        btnUnselected(btnCancel);
    }

    public void btnFundingProgramOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSelected(btnSponsor);
        btnSelected(btnAdd);
        btnUnselected(btnCancel);
        btnUnselected(btnFundingProgram);

        String sponsor_id = lblSponsorId.getText();
        String program_id = String.valueOf(txtProgramId.getValue());
        String sponsor_name = txtSponsorName.getText();
        String description = txtDescription.getText();
        String date = String.valueOf(txtDate.getValue());
        String amount = txtAmount.getText();

        SponsorDto sponsorDto = new SponsorDto(sponsor_id, program_id, sponsor_name, description, date, amount);

        boolean isSaved = sponsorModel.isSaved(sponsorDto);

        if(isSaved){
            clearFeilds();
            //Navigation.switchNavigation("SponsorsForm.fxml", actionEvent);
            generateNextSponsorId();
            getAllId();
        }else{
            new Alert(Alert.AlertType.ERROR,"Doesn't saved sponsor yet !");
        }
    }

    private void clearFeilds() {
        lblSponsorId.setText("");
        txtProgramId.setValue("");
        txtSponsorName.setText("");
        txtDescription.setText("");
        //dateDate.
        txtAmount.setText("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnSelected(btnSponsor);
        btnUnselected(btnAdd);
        btnSelected(btnCancel);
        btnUnselected(btnFundingProgram);

        clearFeilds();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
    }
}
