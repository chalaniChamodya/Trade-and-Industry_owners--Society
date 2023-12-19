package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.SpecialScholDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.DonationModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.SpecialScholModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Map;

public class SpecialScholarshipFormController {
    public AnchorPane pagingPane;
    public JFXButton btnSpecialSchol;
    public JFXButton btnScholarship;
    public JFXButton btnDeathBenefit;
    public VBox vBox;
    public Label lblBenefitId;
    public DatePicker dateDate;
    public TextField txtAmount;
    public ComboBox txtMemberId;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    public TextField txtMemberName;
    SpecialScholModel scholModel = new SpecialScholModel();

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
        selectEligibleMembers();
        btnSelected(btnSpecialSchol);
        generateNextScholId();
        getAllId();
    }

    private void getAllId() throws SQLException {
        ArrayList<String> list = null;
        SpecialScholModel scholModel = new SpecialScholModel();
        list = scholModel.getAllScholId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(SpecialScholarshipFormController.class.getResource("/View/DonationBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            DonationBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateNextScholId() throws SQLException {
        lblBenefitId.setText(scholModel.generateNextScholId());
    }

    private void selectEligibleMembers() throws SQLException {
        LocalDate currentDate = LocalDate.now();
        ArrayList<String> eligibleMemberList = new ArrayList<>();


        Map<String, LocalDate> memberJoinedDates = scholModel.calculateMemberDuration();

        LocalDate joined_date ;
        for(Map.Entry<String,LocalDate> entry : memberJoinedDates.entrySet()){
            String member_id = entry.getKey();
            joined_date = entry.getValue();
        }

        Map<String, Double> meetingAttendance = scholModel.calculateMeetingAttendance();
        for (Map.Entry<String, Double> entry : meetingAttendance.entrySet()){
            String memberId = entry.getKey();
            Double attendance = entry.getValue();

            if(attendance > 80){
                LocalDate joinedDate = memberJoinedDates.get(memberId);
                Period period = Period.between(joinedDate, currentDate);
                int years = period.getYears();

                if(years > 5){
                    eligibleMemberList.add(memberId);
                    System.out.println(eligibleMemberList);
                }
            }
        }
        setDataInComboBox(eligibleMemberList);
        System.out.println(eligibleMemberList);
        //getEligibleId(eligibleMemberList);
    }

    private void getEligibleId(ArrayList<String> eligibleMemberList) {
        vBox.getChildren().clear();
        for(int i = 0; i< eligibleMemberList.size(); i++){
            //loadTableData(eligibleMemberList.get(i));
        }
    }

//    private void loadTableData(String id) {
//        try {
//            FXMLLoader loader = new FXMLLoader(SpecialScholarshipFormController.class.getResource("/View/DonationBarForm.fxml"));
//            Parent root = null;
//            root = loader.load();
//            DonationBarFormController controller = loader.getController();
//            controller.setSpecialScholData(id);
//            vBox.getChildren().add(root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void setDataInComboBox(ArrayList<String> eligibleMemberList) {
        txtMemberId.getItems().addAll(eligibleMemberList);
    }

    public void btnSpecialScholOnAction(ActionEvent actionEvent) {
        btnSelected(btnSpecialSchol);
        btnUnselected(btnDeathBenefit);
        btnUnselected(btnScholarship);
        btnUnselected(btnAdd);
        btnUnselected(btnCancel);
    }

    public void btnScholarshipOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"ScholarshipForm.fxml");
    }

    public void btnDeathBenefitOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"DeathBenefitForm.fxml");
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSelected(btnSpecialSchol);
        btnSelected(btnAdd);
        btnUnselected(btnDeathBenefit);
        btnUnselected(btnScholarship);
        btnUnselected(btnCancel);

        String schoId = lblBenefitId.getText();
        String memberId = String.valueOf(txtMemberId.getValue());
        String name = txtMemberName.getText();
        String date = String.valueOf(dateDate.getValue());
        String amount = txtAmount.getText();

        SpecialScholDto scholDto = new SpecialScholDto(schoId, memberId, name, date, amount);
        boolean isSaved = scholModel.isSaved(scholDto);
        if(isSaved){
           // clearFeilds();
            generateNextScholId();
            getAllId();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnSelected(btnSpecialSchol);
        btnUnselected(btnAdd);
        btnUnselected(btnDeathBenefit);
        btnUnselected(btnScholarship);
        btnSelected(btnCancel);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"DeathBenefitForm.fxml");
    }
}
