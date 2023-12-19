package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;

public class SponsorUpdateFormController {
    public AnchorPane pagingPane;
    public JFXButton btnSponsor;
    public JFXButton btnFundingProgram;
    public VBox vBox;
    public Label lblSponsorId;
    public DatePicker txtDate;
    public TextField txtSponsorName;
    public TextField txtDescription;
    public TextField txtAmount;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;
    public ComboBox txtProgramId;

    public void btnSponsorOnAction(ActionEvent actionEvent) {
    }

    public void btnFundingProgramOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"SponsorForm.fxml");
    }
}
