package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MembershipFeeModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.SubscriptionFeeModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MembershipDueTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class MembershipDueBarFormController {
    public Label fee_id;
    public Label memberName;
    public Label date;
    public ImageView btnDelete;
    public ImageView btnUpdate;

    SubscriptionFeeModel SfeeModel = new SubscriptionFeeModel();
    MembershipFeeModel MfeeModel = new MembershipFeeModel();

    public void setData(String id) {
        MembershipDueTm dueTm = null;
        if(id.startsWith("SF")){
            try {
                dueTm = SfeeModel.getData(id);
                this.fee_id.setText(dueTm.getId());
                memberName.setText(dueTm.getMember_name());
                date.setText(dueTm.getDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(id.startsWith("MF")){
            try {
                dueTm = MfeeModel.getData(id);
                this.fee_id.setText(dueTm.getId());
                memberName.setText(dueTm.getMember_name());
                date.setText(dueTm.getDate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) throws SQLException {
        String id = fee_id.getText();
        if(id.startsWith("SF")){
            boolean isDeleted = SfeeModel.deleteSubscriptionFee(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Fee Deleted!").show();
                SubscriptionFeeFormController.getInstance().getAllId();
            }else{
                new Alert(Alert.AlertType.ERROR,"Doesn't Deleted!").show();
            }
        }else if(id.startsWith("MF")){
            boolean isDeleted = MfeeModel.deleteMembershipFee(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Fee Deleted!").show();
               // MembershipDuesFormController.getInstance().getAllId();
            }else{
                new Alert(Alert.AlertType.ERROR,"Doesn't Deleted!").show();
            }
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) throws IOException {
        SubscriptionFeeUpdateFormController.getId(fee_id.getText());
        if(fee_id.getText().startsWith("SF")){
            Navigation.barPane("SubscriptionFeeUpdateForm.fxml");
        }else if(fee_id.getText().startsWith("MF")){
            Navigation.barPane("SubscriptionFeeUpdateForm.fxml");
        }
    }
}
