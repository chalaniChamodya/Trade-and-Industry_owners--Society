package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.MemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.SendText;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.QRGenerator;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class MemberAddFormController {
    public Label lblMemberId;
    public TextField txtNAmeWithInitials;
    public TextField txtFullName;
    public TextField txtBusinessAddress;
    public TextField txtPersonalAddress;
    public TextField txtEmail;
    public TextField txtNic;
    public TextField txtBusinesType;
    public TextField txtAdmissionFee;
    public TextField txtBusinesContactNumber;
    public TextField txtPersonalContactNumber;
    public DatePicker txtDateOfBirth;
    public DatePicker txtJoinedDate;
    public JFXButton btnAdd;
    public JFXButton btnCancel;
    public AnchorPane pagingPane;
    MemberModel memberModel = new MemberModel();

    void btnSelected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #533710;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #FFFFFF;"
        );
    }

    void btnUnselected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #E7AD5D;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #533710;"
        );
    }

    public void initialize(){
        btnUnselected(btnAdd);
        btnUnselected(btnCancel);
        txtJoinedDate.requestFocus();
        generateNextMemberId();
    }

    private void generateNextMemberId()  {
        try {
            lblMemberId.setText(memberModel.generateNextMemberId());
        } catch (SQLException e) {

        }
    }

    public void btnAddMemberOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, GeneralSecurityException, IOException, MessagingException {
        btnSelected(btnAdd);
        btnUnselected(btnCancel);

        boolean isMemberSaved = validateMember();
        if(isMemberSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Member Saved Successfully !").show();
            String filePath = "Member"+lblMemberId.getText()+".png";
            File QRPath = QRGenerator.generateQRCode(lblMemberId.getText(), filePath, 350, 350);
            new SendText().sendMail("QR code", QRPath ,txtEmail.getText());
            Navigation.switchPaging(pagingPane,"MembersForm.fxml");
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        btnSelected(btnCancel);
        btnUnselected(btnAdd);
        Navigation.switchPaging(pagingPane, "MembersForm.fxml");
    }

    private boolean validateMember() throws SQLException, ClassNotFoundException {
        String member_id = lblMemberId.getText();

        String name_with_initials = txtNAmeWithInitials.getText();
        boolean isMemberNameWithInitialsValidated = Pattern.matches("[A-Za-z\\s]+",name_with_initials);
        System.out.println(isMemberNameWithInitialsValidated + " hi");
        if (!isMemberNameWithInitialsValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid name ").show();
            return false;
        }

        String full_name = txtFullName.getText();
        boolean isMemberFullNameValidated = Pattern.matches("[A-Za-z\\s]{3,}",full_name);
        if (!isMemberFullNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid a").show();
            return false;
        }

        String business_address = txtBusinessAddress.getText();
        boolean isBusinesAddressValidated = Pattern.matches("[A-Za-z\\s]{3,}",business_address);
        if (!isBusinesAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Business Address").show();
            return false;
        }

        String personal_address = txtPersonalAddress.getText();
        boolean isPersonalAddressValidated = Pattern.matches("[A-Za-z\\s]{3,}",personal_address);
        if (!isPersonalAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Personal Address").show();
            return false;
        }

        String business_type = txtBusinesType.getText();
        boolean isBusinessTypeValidated = Pattern.matches("[A-Za-z\\s]{3,}",business_type);
        if (!isBusinessTypeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Business Type").show();
            return false;
        }

        String nic = txtNic.getText();
        boolean isNicValidated = Pattern.matches("[a-z0-9]{3,}",nic);
        if (!isNicValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC").show();
            return false;
        }

        String email = txtEmail.getText();
        boolean isEmailValidated = Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",email);
        if (!isEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            return false;
        }

        String date_of_birth = String.valueOf(txtDateOfBirth.getValue());
//        boolean isDOBValidated = Pattern.matches("/[0-9][-/]/",date_of_birth);
//        if (!isDOBValidated) {
//            new Alert(Alert.AlertType.ERROR, "Invalid Date Of Birth").show();
//            return false;
//        }

        String personal_contact_num = txtPersonalContactNumber.getText();
        boolean isPConNumValidated = Pattern.matches("[0-9]{10}",personal_contact_num);
        if (!isPConNumValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Personal Contact Number").show();
            return false;
        }

        String business_contact_num = txtBusinesContactNumber.getText();
        boolean isBConNumValidated = Pattern.matches("[0-9]{10}",business_contact_num);
        if (!isBConNumValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Business Contact Number").show();
            return false;
        }

        String admission_fee = txtAdmissionFee.getText();
        boolean isAdmissionFeeValidated = Pattern.matches("[0-9]{3,}",admission_fee);
        if (!isAdmissionFeeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Admission Fee").show();
            return false;
        }

        String joined_date = String.valueOf(txtJoinedDate.getValue());

        MemberDto memberDto = new MemberDto(member_id, name_with_initials, full_name, business_address, personal_address,business_type, nic, email, date_of_birth, personal_contact_num, business_contact_num, admission_fee, joined_date);

        MemberModel memberModel = new MemberModel();
        boolean isSaved = memberModel.saveMember(memberDto);

        return isSaved;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"MembersForm.fxml");
    }

    public void EnterOnJoinedDate(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String joined_date = String.valueOf(txtJoinedDate.getValue());
            keyEvent.consume();
            txtNAmeWithInitials.requestFocus();
        }
    }

    public void EnterOnNameWithInitials(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String name_with_initials = txtNAmeWithInitials.getText();
            boolean isMemberNameWithInitialsValidated = Pattern.matches("[A-Za-z\\s]+", name_with_initials);
            //System.out.println(isMemberNameWithInitialsValidated + " hi");
            if (!isMemberNameWithInitialsValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid name ").show();
            }
            keyEvent.consume();
            txtDateOfBirth.requestFocus();
        }
    }

    public void EnterOnDateOfBirth(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String date_of_birth = String.valueOf(txtDateOfBirth.getValue());
            keyEvent.consume();
            txtFullName.requestFocus();
        }
    }

    public void EnterOnFullName(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String full_name = txtFullName.getText();
            boolean isMemberFullNameValidated = Pattern.matches("[A-Za-z\\s]{3,}",full_name);
            if (!isMemberFullNameValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid a").show();
            }
            keyEvent.consume();
            txtBusinessAddress.requestFocus();
        }
    }

    public void EnterOnBusinessAddress(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String business_address = txtBusinessAddress.getText();
            boolean isBusinesAddressValidated = Pattern.matches("[A-Za-z\\s]{3,}",business_address);
            if (!isBusinesAddressValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Business Address").show();
            }
            keyEvent.consume();
            txtPersonalAddress.requestFocus();
        }
    }

    public void EnterOnPersonalAddress(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String personal_address = txtPersonalAddress.getText();
            boolean isPersonalAddressValidated = Pattern.matches("[A-Za-z\\s]{3,}",personal_address);
            if (!isPersonalAddressValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Personal Address").show();
            }
            keyEvent.consume();
            txtPersonalContactNumber.requestFocus();
        }
    }

    public void EnterOnPersonalContactNum(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String personal_contact_num = txtPersonalContactNumber.getText();
            boolean isPConNumValidated = Pattern.matches("[0-9]{10}",personal_contact_num);
            if (!isPConNumValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Personal Contact Number").show();
            }
            keyEvent.consume();
            txtBusinesContactNumber.requestFocus();
        }
    }

    public void EnterOnBusinessContactNum(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String business_contact_num = txtBusinesContactNumber.getText();
            boolean isBConNumValidated = Pattern.matches("[0-9]{10}",business_contact_num);
            if (!isBConNumValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Business Contact Number").show();
            }
            keyEvent.consume();
            txtEmail.requestFocus();
        }
    }

    public void EnterOnEmail(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String email = txtEmail.getText();
            boolean isEmailValidated = Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",email);
            if (!isEmailValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            }
            keyEvent.consume();
            txtNic.requestFocus();
        }
    }

    public void EnterOnNic(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String nic = txtNic.getText();
            boolean isNicValidated = Pattern.matches("[a-z0-9]{3,}",nic);
            if (!isNicValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid NIC").show();
            }
            keyEvent.consume();
            txtBusinesType.requestFocus();
        }
    }

    public void EnterOnBusinessType(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String business_type = txtBusinesType.getText();
            boolean isBusinessTypeValidated = Pattern.matches("[A-Za-z\\s]{3,}",business_type);
            if (!isBusinessTypeValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Business Type").show();
            }
            keyEvent.consume();
            txtAdmissionFee.requestFocus();
        }
    }
}
