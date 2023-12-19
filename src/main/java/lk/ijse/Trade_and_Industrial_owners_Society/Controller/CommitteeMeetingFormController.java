package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.CommitteeMeetingDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.GeneralMeetingDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.CommitteeMeetingModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.GeneralMeetingModel;
import lk.ijse.Trade_and_Industrial_owners_Society.SendText;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CommitteeMeetingFormController {
    public AnchorPane pagingPane;
    public JFXButton btnCommittee;
    public JFXButton btnGeneral;
    public VBox vBox;
    public JFXButton btnCommitteeAttendance;
    public TextField txtMeetingId;
    public DatePicker dateDate;
    public TextField timeTime;
    public TextField txtLocation;
    public TextField txtPurpose;
    public ComboBox txtMeetingType;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    public String meetingType;
    public String meetingId;
    private static CommitteeMeetingFormController controller;

    GeneralMeetingModel generalMeetingModel = new GeneralMeetingModel();
    CommitteeMeetingModel committeeMeetingModel = new CommitteeMeetingModel();

    public CommitteeMeetingFormController(){
        controller = this;
    }

    public static CommitteeMeetingFormController getInstance(){
        return controller;
    }

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
        setDataInComboBox();
        btnSelected(btnCommittee);
    }

    private void setDataInComboBox() {
        ArrayList<String> meetingType = new ArrayList<>();
        meetingType.add("General Meeting");
        meetingType.add("Committee Meeting");

        txtMeetingType.getItems().addAll(meetingType);
    }

    public String getMeetingType(){
        return (String) txtMeetingType.getValue();
    }

    private void generateNextMeetingId(String meetingType) {
        try {
            if (meetingType.equals("General Meeting")) {
                meetingId = generalMeetingModel.generateNextGeneralMeetingID();
                txtMeetingId.setText(meetingId);
            }else if(meetingType.equals("Committee Meeting")){
                meetingId = committeeMeetingModel.generateNextCommitteeMeetingId();
                txtMeetingId.setText(meetingId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        CommitteeMeetingModel meetingModel = new CommitteeMeetingModel();
        list = meetingModel.getAllMeetingId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(CommitteeMeetingFormController.class.getResource("/View/MeetingBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            MeetingBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCommitteeOnAction(ActionEvent actionEvent) {
        btnUnselected(btnAdd);
        btnUnselected(btnGeneral);
        btnUnselected(btnCancel);
        btnSelected(btnCommittee);
        btnUnselected(btnCommitteeAttendance);
    }

    public void btnGeneralOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }

    public void btnCommitteeMeetingAttendanceOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"CommitteeMeetingAttendanceForm.fxml");
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, GeneralSecurityException, IOException, MessagingException, ClassNotFoundException {
        btnSelected(btnAdd);
        btnUnselected(btnGeneral);
        btnUnselected(btnCancel);
        btnSelected(btnCommittee);
        btnUnselected(btnCommitteeAttendance);

        String meetingType = getMeetingType();
        if(meetingType.equals("General Meeting")){
            String id = txtMeetingId.getText();
            String date = String.valueOf(dateDate.getValue());
            String time = timeTime.getText();
            String description = txtPurpose.getText();
            String location = txtLocation.getText();

            GeneralMeetingDto generalMeetingDto = new GeneralMeetingDto(id, date, time, description, location);

            boolean isSaved = generalMeetingModel.isSaved(generalMeetingDto);

            //String email= null;

            System.out.println(isSaved);

            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"meeting Saved!").show();
                getAllId();
                ArrayList<String> emailList = generalMeetingModel.getMailAddress();
                System.out.println(emailList.size());
                for (int i = 0; i < emailList.size(); i++) {
                    String email = emailList.get(i);
                    new SendText().sendMail("Next General Meeting", String.valueOf(generalMeetingDto),email);
                }
                //Navigation.switchNavigation("MeetingForm.fxml", actionEvent);
            }else{
                new Alert(Alert.AlertType.ERROR,"Doesn't Saved Yet!").show();
            }
        }else if(meetingType.equals("Committee Meeting")){
            String id = txtMeetingId.getText();
            String date = String.valueOf(dateDate.getValue());
            String time = timeTime.getText();
            String description = txtPurpose.getText();
            String location = txtLocation.getText();

            CommitteeMeetingDto committeeMeetingDto = new CommitteeMeetingDto(id, date, time, description, location);

            boolean isSaved = committeeMeetingModel.isSaved(committeeMeetingDto);

            if(isSaved){
                // Navigation.switchNavigation("CommitteeMeetingForm.fxml", actionEvent);
            }else{
                new Alert(Alert.AlertType.ERROR, "Doesn't Saved Yet!").show();
            }
        }
    }

    private void clearFeilds() {
        dateDate.setValue(LocalDate.parse(""));
        txtLocation.setText("");
        txtMeetingType.setValue("");
        timeTime.setText("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnUnselected(btnAdd);
        btnUnselected(btnGeneral);
        btnSelected(btnCancel);
        btnSelected(btnCommittee);
        btnUnselected(btnCommitteeAttendance);

        clearFeilds();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }
}
