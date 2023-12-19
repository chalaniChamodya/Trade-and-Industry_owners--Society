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
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.GeneralMeetingDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.GeneralMeetingModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GeneralMeetingUpdateFormController {
    private static String id;
    public AnchorPane pagingPane;
    public JFXButton btnCommittee;
    public JFXButton btnGeneral;
    public VBox vBox;
    public JFXButton btnGeneralAttendance;
    public TextField txtMeetingId;
    public DatePicker dateDate;
    public TextField timeTime;
    public TextField txtLocation;
    public TextField txtPurpose;
    public ComboBox txtMeetingType;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;
    GeneralMeetingModel meetingModel = new GeneralMeetingModel();

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
        btnSelected(btnGeneral);
        getAllId();
        getMeetingId();
        setData();
        setDataInComboBox();
    }

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        GeneralMeetingModel generalMeetingModel = new GeneralMeetingModel();
        list = generalMeetingModel.getAllMeetingId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(GeneralMeetingUpdateFormController.class.getResource("/View/MeetingBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            MeetingBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMeetingId() {
        txtMeetingId.setText(id);
    }

    public static void getId(String id){
        GeneralMeetingUpdateFormController.id = id;
    }

    private void setData() {
        GeneralMeetingDto meetingDto = null;

        try {
            meetingDto = meetingModel.getDataToUpdateForm(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtMeetingType.setValue("General Meeting");
        //dateDate.setValue(LocalDate.parse(meetingDto.getDate()));
        timeTime.setText(meetingDto.getTime());
        txtPurpose.setText(meetingDto.getDescription());
        txtLocation.setText(meetingDto.getLocation());
    }

    private void setDataInComboBox() {
        ArrayList<String> meetingType = new ArrayList<>();
        meetingType.add("General Meeting");
        meetingType.add("Committee Meeting");

        txtMeetingType.getItems().addAll(meetingType);
    }


    public void btnCommitteeOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"CommitteeMeetingForm.fxml");
    }

    public void btnGeneralOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }

    public void btnGeneralMeetingAttendanceOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingAttendanceForm.fxml");
    }

    public void cmbMeetingTypeOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        GeneralMeetingDto meetingDto = new GeneralMeetingDto();
        meetingDto.setGeneral_meeting_id(id);
        meetingDto.setDate(String.valueOf(dateDate.getValue()));
        meetingDto.setTime(timeTime.getText());
        meetingDto.setDescription(txtPurpose.getText());
        meetingDto.setLocation(txtLocation.getText());

        boolean isUpdated = meetingModel.updateGeneralMeeting(meetingDto);
        if(isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Meeting Updated !").show();
            Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
            MembersFormController.getInstance().getAllId();
        }else{
            new Alert(Alert.AlertType.ERROR,"Meeting doesn't Updated !").show();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }
}
