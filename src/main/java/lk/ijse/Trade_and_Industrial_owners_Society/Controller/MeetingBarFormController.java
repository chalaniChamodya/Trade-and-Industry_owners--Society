package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.CommitteeMeetingModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.GeneralMeetingModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class MeetingBarFormController {
    public Label meetingId;
    public Label date;
    public Label Location;
    public ImageView btnDelete;
    public ImageView btnUpdate;

    GeneralMeetingModel generalMeetingModel = new GeneralMeetingModel();
    CommitteeMeetingModel committeeMeetingModel = new CommitteeMeetingModel();

    public void setData(String id){
        MeetingTm meetingTm = null;
        if(id.startsWith("G")){
            try {
                meetingTm = generalMeetingModel.getData(id);
                this.meetingId.setText(meetingTm.getMeeting_id());
                date.setText(meetingTm.getDate());
                Location.setText(meetingTm.getLocation());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(id.startsWith("C")){
            try {
                meetingTm = committeeMeetingModel.getData(id);
                this.meetingId.setText(meetingTm.getMeeting_id());
                date.setText(meetingTm.getDate());
                Location.setText(meetingTm.getLocation());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) throws SQLException {
        String id = meetingId.getText();
        if(id.startsWith("G")){
            boolean isDeleted = generalMeetingModel.deleteGeneralMeeting(id);

            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"meeting deleted !").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Doesn't deleted !").show();
            }
            GeneralMeetingFormController.getInstance().getAllId();
        }else if(id.startsWith("C")){
            boolean isDeleted = committeeMeetingModel.deleteCommitteeMeeting(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"meeting deleted !").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Doesn't deleted !").show();
            }
            CommitteeMeetingFormController.getInstance().getAllId();
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) throws IOException {
        MemberUpdateFormController.getId(meetingId.getText());
        if(meetingId.getText().startsWith("G")){
            Navigation.barPane("GeneralMeetingUpdateForm.fxml");
        }else if(meetingId.getText().startsWith("C")){
            //Navigation.switchNavigation("CommitteeMeetingUpdateForm.fxml", mouseEvent);
        }
    }
}
