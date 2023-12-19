package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.CommitteeMemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.CommitteeMemberTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class CommitteeMemberBarFormController {
    private static String id;
    public Label comMemId;
    public Label name;
    public Label position;
    public ImageView btnDelete;
    public ImageView btnUpdate;

    CommitteeMemberModel committeeMemberModel = new CommitteeMemberModel();

    public static void getId(){CommitteeMemberBarFormController.id = id;}

    public void setData(String id) {
        CommitteeMemberTm tm = null;
        try {
            tm = committeeMemberModel.getData(id);
            this.comMemId.setText(tm.getCom_mem_id());
            name.setText(tm.getName());
            position.setText(tm.getPosition());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) throws SQLException {
        String id = comMemId.getText();
        boolean isDeleted = committeeMemberModel.deleteCommitteeMember(id);

        if(isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Committee member deleted !").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Doesn't deleted !").show();
        }
        CommitteeMemberFormController.getInstance().getAllId();
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) throws IOException {
        CommitteeMemberUpdateFormController.getId(comMemId.getText());
        Navigation.barPane("CommitteeMemberUpdateForm.fxml");
    }
}
