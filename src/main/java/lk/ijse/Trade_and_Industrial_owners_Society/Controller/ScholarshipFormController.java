package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.ScholarshipDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.DonationModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ScholarshipFormController {
    public AnchorPane pagingPane;
    public JFXButton btnSpecialSchol;
    public JFXButton btnScholarship;
    public JFXButton btnDeathBenefit;
    public VBox vBox;
    public Label lblBenefitId;
    public DatePicker dateDate;
    public TextField txtAmount;
    public ComboBox txtFamilyMemberId;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    private static ScholarshipFormController controller;

    DonationModel scholModel = new DonationModel();

    public ScholarshipFormController(){controller = this;}

    public static ScholarshipFormController getInstance(){return controller;}

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
        generateNextScholarshipId();
        btnSelected(btnScholarship);
        setDataInFamilyMemComboBox();
    }

    private void setDataInFamilyMemComboBox() throws SQLException {
        ArrayList<String> famMemberId = scholModel.getAllFamlyMemberId();
        txtFamilyMemberId.getItems().addAll(famMemberId);
    }

    private void generateNextScholarshipId() throws SQLException {
        lblBenefitId.setText(scholModel.generateNextScholId());
    }

    private void getAllId() throws SQLException {
        ArrayList<String> list = null;
        DonationModel donationModel = new DonationModel();
        list = donationModel.getAllScholarshipId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(ScholarshipFormController.class.getResource("/View/DonationBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            DonationBarFormController controller = loader.getController();
            controller.setDataOfSchol(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSpecialScholOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"SpecialScholarshipForm.fxml");
    }

    public void btnScholarshipOnAction(ActionEvent actionEvent) {
        btnSelected(btnScholarship);
        btnUnselected(btnDeathBenefit);
        btnUnselected(btnSpecialSchol);
        btnUnselected(btnAdd);
        btnUnselected(btnCancel);
    }

    public void btnDeathBenefitOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"DeathBenefitForm.fxml");
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        btnSelected(btnAdd);
        btnSelected(btnScholarship);
        btnUnselected(btnDeathBenefit);
        btnUnselected(btnCancel);
        btnUnselected(btnSpecialSchol);

        String scholId = lblBenefitId.getText();
        String date = String.valueOf(dateDate.getValue());
        String amonut = txtAmount.getText();
        String fam_mem_id = String.valueOf(txtFamilyMemberId.getValue());

        ScholarshipDto scholarshipDto = new ScholarshipDto(scholId, date, amonut, fam_mem_id);

        try {
            boolean isSaved = scholModel.isSavedSchol(scholarshipDto);
            if(isSaved){
                clearFields();
                generateNextScholarshipId();
            }else{
                new Alert(Alert.AlertType.ERROR,"Doesn't Saved !").show();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void clearFields() {
        dateDate.setValue(LocalDate.parse(""));
        txtFamilyMemberId.setValue("");
        txtAmount.setText("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnUnselected(btnAdd);
        btnSelected(btnScholarship);
        btnUnselected(btnDeathBenefit);
        btnSelected(btnCancel);
        btnUnselected(btnSpecialSchol);

        clearFields();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"DeathBenefitForm.fxml");
    }
}
