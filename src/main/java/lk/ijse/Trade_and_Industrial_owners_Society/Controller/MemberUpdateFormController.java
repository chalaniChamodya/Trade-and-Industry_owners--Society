package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Trade_and_Industrial_owners_Society.Dto.MemberDto;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberUpdateFormController {
    private static String id;
    public AnchorPane pagingPane;
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
    public JFXButton btnUpdate;
    public JFXButton btnCancel;

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
                "-fx-background-color: #E8E8E8;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #727374;"
        );
    }

    public void initialize() throws SQLException {
        setData();
        setMemberId();
    }

    private void setData() {
        MemberDto memberDto = null;
        try {
            memberDto =  memberModel.getDataToUpdateForm(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lblMemberId.setText(id);
        txtNAmeWithInitials.setText(memberDto.getName_with_initials());
        txtFullName.setText(memberDto.getFull_name());
        txtBusinessAddress.setText(memberDto.getBusiness_address());
        txtPersonalAddress.setText(memberDto.getPersonal_address());
        txtBusinesType.setText(memberDto.getBusiness_type());
        txtNic.setText(memberDto.getNic());
        txtEmail.setText(memberDto.getEmail());
        txtDateOfBirth.setValue(LocalDate.parse(memberDto.getDate_of_birth()));
        txtPersonalContactNumber.setText(memberDto.getPersonal_contact_num());
        txtBusinesContactNumber.setText(memberDto.getBusiness_contact_num());
        txtAdmissionFee.setText(memberDto.getAdmission_fee());
        txtJoinedDate.setValue(LocalDate.parse(memberDto.getJoined_date()));
    }

    private void setMemberId() {
        lblMemberId.setText(id);
    }

    public static void getId(String id) {
        MemberUpdateFormController.id = id;
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        btnSelected(btnUpdate);
        btnUnselected(btnCancel);

        MemberDto memberDto = new MemberDto();
        memberDto.setMember_id(id);
        memberDto.setName_with_initials(txtNAmeWithInitials.getText());
        memberDto.setFull_name(txtFullName.getText());
        memberDto.setPersonal_address(txtPersonalAddress.getText());
        memberDto.setBusiness_address(txtBusinessAddress.getText());
        memberDto.setBusiness_type(txtBusinesType.getText());
        memberDto.setNic(txtNic.getText());
        memberDto.setEmail(txtEmail.getText());
        memberDto.setDate_of_birth(String.valueOf(txtDateOfBirth.getValue()));
        memberDto.setPersonal_contact_num(txtPersonalContactNumber.getText());
        memberDto.setBusiness_contact_num(txtBusinesContactNumber.getText());
        memberDto.setJoined_date(String.valueOf(txtJoinedDate.getValue()));

        boolean isUpdated = memberModel.updateMember(memberDto);

        if(isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Member Updated !").show();
            Navigation.switchPaging(pagingPane,"MembersForm.fxml");
            MembersFormController.getInstance().getAllId();
        }else{
            new Alert(Alert.AlertType.ERROR,"Member doesn't Updated !").show();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"MembersForm.fxml");
    }
}
