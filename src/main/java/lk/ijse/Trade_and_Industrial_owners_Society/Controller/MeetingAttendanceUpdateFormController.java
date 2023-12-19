package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;

public class MeetingAttendanceUpdateFormController {
    public AnchorPane pagingPane;
    public VBox vBox;
    public TextField txtMemberNme;
    public TextField txtTme;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;
    public ComboBox cmbGenMetingId;
    public ComboBox cmbMemberID;

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingAttendanceForm.fxml");
    }

    public void ackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingAttendanceForm.fxml");
    }
}
