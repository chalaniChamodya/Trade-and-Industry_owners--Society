package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.util.NavigableMap;

public class CommitteeMeetingAttendanceFormController {
    public AnchorPane pagingPane;
    public VBox vBox;
    public TextField txtMemberNme;
    public TextField txtTme;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    public ComboBox cmbGenMetingId;
    public ComboBox cmbMemberID;

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

    public void initialize(){
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        btnSelected(btnAdd);
        btnUnselected(btnCancel);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnSelected(btnCancel);
        btnUnselected(btnAdd);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"CommitteeMeetingForm.fxml");
    }
}
