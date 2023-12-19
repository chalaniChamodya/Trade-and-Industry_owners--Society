package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MeetingAttendanceModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingAttendanceTm;

import java.sql.SQLException;

public class MeetingAttendanceBarFormController {
    public Label MeetingId;
    public Label MemberID;
    public Label memberName;
    public ImageView btnDelete;
    public ImageView btnUpdate;
    MeetingAttendanceModel attendanceModel = new MeetingAttendanceModel();

    public void btnDeleteOnAction(MouseEvent mouseEvent) throws SQLException {
        String meetingId = MeetingId.getText();
        String memberId = MemberID.getText();
        boolean isDeleted = attendanceModel.deleteGeneralMeetingAttendance(meetingId,memberId);

        if(isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Attendance Deleted !").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Doesn't Deleted !").show();
        }
        GeneralMeetingAttendanceFormController.getInstance().getAllId();
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {

    }

    public void setDataInGeneral(String id) {
        MeetingAttendanceTm attendanceTm = null;
        try {
            attendanceTm = attendanceModel.getGeneralData(id);
            this.MeetingId.setText(attendanceTm.getMeeting_id());
            MemberID.setText(attendanceTm.getMember_id());
            memberName.setText(attendanceTm.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
