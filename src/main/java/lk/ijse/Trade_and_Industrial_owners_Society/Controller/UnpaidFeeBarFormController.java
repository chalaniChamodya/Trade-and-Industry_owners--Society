package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.SendText;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MemberTm;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class UnpaidFeeBarFormController {
    public Label member_id;
    public Label name;
    public Label contact_no;
    public Label business_type;
    public Label nic;
    public Button btnInform;
    MemberModel memberModel = new MemberModel();

    public void setData(String id) {
        MemberTm memberTm = null;

            try {
                memberTm = memberModel.getData(id);
                this.member_id.setText(memberTm.getMember_id());
                name.setText(memberTm.getName());
                contact_no.setText(memberTm.getPersonal_contact_num());
                business_type.setText(memberTm.getBusiness_type());
                nic.setText(memberTm.getNic());
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void btnInformOnAction(ActionEvent actionEvent) throws SQLException, GeneralSecurityException, IOException, MessagingException {
        String id = member_id.getText();
        System.out.println(id);
        String email = memberModel.getEmailAddress(id);
        System.out.println(email);
        new SendText().sendMail("Payment Reminder","Pay this month's your subscription fee as soon as possible.!",email);
    }
}
