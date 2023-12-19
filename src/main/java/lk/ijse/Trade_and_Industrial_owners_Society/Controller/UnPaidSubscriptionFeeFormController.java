package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.DashboardModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.SendText;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnPaidSubscriptionFeeFormController {
    public AnchorPane pagingPane;
    public JFXButton btnInformAll;
    public VBox vBox;
    private static UnPaidSubscriptionFeeFormController controller;
    MemberModel memberModel = new MemberModel();

    public UnPaidSubscriptionFeeFormController(){controller = this;}

    public static UnPaidSubscriptionFeeFormController getInstance(){return controller;}

    public void initialize() throws SQLException {
        getAllId();
    }

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

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        DashboardModel dashboardModel = new DashboardModel();
        list = dashboardModel.getAllUnpaidSubscriptionFeeId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(UnPaidSubscriptionFeeFormController.class.getResource("/View/UnpaidFeeBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            UnpaidFeeBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnInformAllOnAction(ActionEvent actionEvent) throws SQLException, GeneralSecurityException, IOException, MessagingException {
        btnSelected(btnInformAll);
        ArrayList<String> emailList = memberModel.getAllEmailAddress();

        for (int i = 0; i < emailList.size(); i++) {
            String email = emailList.get(i);
            new SendText().sendMail("Reminder","Pay this month's your subscription fee as soon as possible.!" ,email);
        }
       // btnUnselected(btnInformAll);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"DashboardForm.fxml");
    }
}
