package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.UserDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.UserModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignUpFormController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public JFXButton btnSignup;
    public TextField txtComMemId;
    public ComboBox cmbPosition;
    public PasswordField txtConfirmPassword;
    public String userId;
    UserModel userModel = new UserModel();

    public void initialize() {
        setDataInComboBox();
        generateNextUserId();
    }

    private void generateNextUserId() {
        try {
            userId = userModel.generateNextUserId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDataInComboBox(){
        ArrayList<String> roles = new ArrayList<>();
        roles.add("Chairman");
        roles.add("Secretery");
        roles.add("Tresurer");
        cmbPosition.getItems().addAll(roles);
    }

    public String getRole(){
        return String.valueOf(cmbPosition.getSelectionModel().getSelectedItem());
    }

    public void btnSignupOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String id = userId;
        String comMemId = txtComMemId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String role = getRole();

        if(password.equals(confirmPassword)){
            UserDto userDTO = new UserDto(id, comMemId,role, username, password );

            boolean save = userModel.save(userDTO);
            if(save){
                //clearFields();
                Navigation.switchNavigation("GlobalForm.fxml", actionEvent);
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Confirm your password correctly !");
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchNavigation("LoginForm.fxml", actionEvent);
    }
}
