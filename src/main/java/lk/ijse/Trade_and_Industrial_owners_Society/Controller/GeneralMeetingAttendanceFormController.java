package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.MeetingAttendanceDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MeetingAttendanceModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.QRCodeReader;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GeneralMeetingAttendanceFormController {

    public AnchorPane pagingPane;
    public VBox vBox;
    public TextField txtMemberNme;
    public TextField txtTme;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    public ComboBox cmbGenMetingId;
    public ComboBox cmbMemberID;

    private static GeneralMeetingAttendanceFormController controller;
    public JFXButton btnQr;
    //public TextField txtMeetingID;

    MeetingAttendanceModel meetingAttendanceModel = new MeetingAttendanceModel();

    public GeneralMeetingAttendanceFormController(){controller = this;}

    public static GeneralMeetingAttendanceFormController getInstance(){return controller;}

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
        setDataInMeetingIdComboBox();
        setDataInMemberIdComboBox();
    }

    private void setDataInMemberIdComboBox() throws SQLException {
        ArrayList<String> memberId = meetingAttendanceModel.getAllMemberId();
        cmbMemberID.getItems().addAll(memberId);
    }

    private void setDataInMeetingIdComboBox() throws SQLException {
        ArrayList<String> MeetingId = meetingAttendanceModel.getAllGeneralMeetingId();
        cmbGenMetingId.getItems().addAll(MeetingId);
    }

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        MeetingAttendanceModel attendanceModel = new MeetingAttendanceModel();
        list = attendanceModel.getAllGeneralMeetingId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(GeneralMeetingAttendanceFormController.class.getResource("/View/MeetingAttendanceBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            MeetingAttendanceBarFormController controller = loader.getController();
            controller.setDataInGeneral(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSelected(btnAdd);
        btnUnselected(btnCancel);

        String meeting_id = String.valueOf(cmbGenMetingId.getValue());
        String member_id = String.valueOf(cmbMemberID.getValue());
        String name = txtMemberNme.getText();
        String time = txtTme.getText();
        String date = String.valueOf(LocalDate.now());

        MeetingAttendanceDto dto = new MeetingAttendanceDto(meeting_id,member_id,name, date,time);

        boolean isSaved = meetingAttendanceModel.isSavedGeneralMeetingAttendance(dto);

        if(isSaved){
            getAllId();
            clearFields();
            btnUnselected(btnQr);
            btnUnselected(btnAdd);
            //Navigation.switchNavigation("FundingProgramForm.fxml", actionEvent);
        }else{
            new Alert(Alert.AlertType.ERROR, "Doesn't saved yet !").show();
        }
    }

    private void clearFields() {
        cmbGenMetingId.setValue("");
        cmbMemberID.setValue("");
        txtMemberNme.setText("");
        txtTme.setText("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnSelected(btnCancel);
        btnUnselected(btnAdd);
    }

    public void btnQRScanOnAction(ActionEvent actionEvent) throws SQLException {
        btnSelected(btnQr);
        String memberId = QRCodeReader.initWebcam();
        cmbGenMetingId.setValue(meetingAttendanceModel.getTodayGeneralMeetingId());
        cmbMemberID.setValue(memberId);
        txtMemberNme.setText(meetingAttendanceModel.getMemberName(memberId));
        txtTme.setText(String.valueOf(LocalTime.now()));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }
}
