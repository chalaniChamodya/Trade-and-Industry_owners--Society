package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.UserModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginFormController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public JFXButton btnLogin;

    UserModel userModel = new UserModel();

    void btnSelected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #533710;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #FFFFFF;"
        );
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        btnSelected(btnLogin);
        try {
            if (userModel.checkUsernameAndPassword(txtUsername.getText(), txtPassword.getText()).equals("Chairman")){
                Navigation.switchNavigation("GlobalForm.fxml", actionEvent);
            }else if(userModel.checkUsernameAndPassword(txtUsername.getText(), txtPassword.getText()).equals("Secretary")){
                Navigation.switchNavigation("GlobalForm.fxml", actionEvent);
            }else if(userModel.checkUsernameAndPassword(txtUsername.getText(), txtPassword.getText()).equals("Treasurer")){
                Navigation.switchNavigation("GlobalForm.fxml", actionEvent);
            }else{
                new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password !").show();
            }
        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSignupOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchNavigation("SignUpForm.fxml", actionEvent);
    }

    public void EnterOnUsername(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            txtPassword.requestFocus();
        }
    }

    public void EnterOnPassword(KeyEvent keyEvent) {
//        try {
//            if (userModel.checkUsernameAndPassword(txtUsername.getText(), txtPassword.getText()).equals("Chairman")){
//                Navigation.switchNavigation("GlobalForm.fxml", keyEvent);
//            }else if(userModel.checkUsernameAndPassword(txtUsername.getText(), txtPassword.getText()).equals("Secretary")){
//                Navigation.switchNavigation("GlobalForm.fxml", keyEvent);
//            }else if(userModel.checkUsernameAndPassword(txtUsername.getText(), txtPassword.getText()).equals("Treasurer")){
//                Navigation.switchNavigation("GlobalForm.fxml", keyEvent);
//            }else{
//                new Alert(Alert.AlertType.ERROR, "Incorrect Username or Password !").show();
//            }
//        } catch (ClassNotFoundException | IOException | SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
