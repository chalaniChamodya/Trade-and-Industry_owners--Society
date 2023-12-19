package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.FamilyMemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.FamilyMemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FamilyMemberFormController {
    public AnchorPane pagingPane;
    public JFXButton btnFamily;
    public JFXButton btnMember;
    public JFXButton btnCommittee;
    public VBox vBox;
    public Label lblFamMemId;
    public DatePicker dateDateOfBirth;
    public TextField txtName;
    public TextField txtOccupation;
    public Label lblIsAlive;
    public ComboBox cmbMemberId;
    public ComboBox cmbRelationship;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    private static FamilyMemberFormController controller;

    FamilyMemberModel famMemModel = new FamilyMemberModel();

    public FamilyMemberFormController(){controller = this;}

    public static FamilyMemberFormController getInstance(){return controller;}

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
        btnSelected(btnFamily);
        btnUnselected(btnMember);
        btnUnselected(btnCommittee);

        getAllId();
        generateNextFamilyMemberId();
        setDataInRelationComboBox();
        setDataInMemberIdComboBox();
        lblIsAlive.setText("Yes");
    }

    private void setDataInMemberIdComboBox() throws SQLException {
        ArrayList<String> memberId = famMemModel.getAllMemberId();
        cmbMemberId.getItems().addAll(memberId);
    }

    public void setDataInRelationComboBox(){
        ArrayList<String> relationships = new ArrayList<>();
        relationships.add("Wife/Husband");
        relationships.add("Son");
        relationships.add("Daughter");
        relationships.add("Father");
        relationships.add("Mother");
        cmbRelationship.getItems().addAll(relationships);
    }

    public String getRelationship(){
        return String.valueOf(cmbRelationship.getSelectionModel().getSelectedItem());
    }

    private void generateNextFamilyMemberId() throws SQLException {
        lblFamMemId.setText(famMemModel.generateNextFamilyMemberId());
    }

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        FamilyMemberModel famMemModel = new FamilyMemberModel();
        list = famMemModel.getAllFamilyMemberId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(FamilyMemberFormController.class.getResource("/View/FamilyMemberBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            FamilyMemberBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnFamilyOnAction(ActionEvent actionEvent) {
        btnSelected(btnFamily);
        btnUnselected(btnMember);
        btnUnselected(btnCancel);
        btnUnselected(btnCommittee);
        btnUnselected(btnAdd);
    }

    public void btnMemberOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane, "MembersForm.fxml");
    }

    public void btnCommitteeOnAction(ActionEvent actionEvent) throws IOException {
       Navigation.switchPaging(pagingPane, "CommitteeMemberForm.fxml");
    }

    public void cmbMemberIdOnAction(ActionEvent actionEvent) {
    }

    public void cmbRrelationshipOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSelected(btnAdd);
        btnSelected(btnFamily);
        btnUnselected(btnMember);
        btnUnselected(btnCancel);
        btnUnselected(btnCommittee);

        String family_member_id = lblFamMemId.getText();
        String member_id = String.valueOf(cmbMemberId.getValue());
        String name = txtName.getText();
        String date_of_birth = String.valueOf(dateDateOfBirth.getValue());
        String occupation = txtOccupation.getText();
        String relationship = getRelationship();
        String isAlive = lblIsAlive.getText();

        FamilyMemberDto familyMemberDto = new FamilyMemberDto(family_member_id,member_id,name,relationship,occupation,date_of_birth,isAlive);

        boolean isSaved = famMemModel.saveFamMember(familyMemberDto);

        if(isSaved){
            clearFeilds();
            generateNextFamilyMemberId();
            getAllId();
        }else{
            new Alert(Alert.AlertType.ERROR,"Family Member Doesn't Saved !");
        }
    }

    private void clearFeilds() {
        cmbMemberId.setValue("");
        txtName.setText("");
        txtOccupation.setText("");
        cmbRelationship.setValue("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnSelected(btnCancel);
        btnSelected(btnFamily);
        btnUnselected(btnMember);
        btnUnselected(btnAdd);
        btnUnselected(btnCommittee);

        clearFeilds();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"MembersForm.fxml");
    }
}
