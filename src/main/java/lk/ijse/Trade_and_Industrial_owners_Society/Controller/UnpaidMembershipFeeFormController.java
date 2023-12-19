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

public class UnpaidMembershipFeeFormController {
    public AnchorPane pagingPane;
    public JFXButton btnInformAll;
    public VBox vBox;
    private static UnpaidMembershipFeeFormController controller;

    public UnpaidMembershipFeeFormController(){controller = this;}

    public static UnpaidMembershipFeeFormController getInstance(){return controller;}

    MemberModel memberModel = new MemberModel();

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
        list = dashboardModel.getAllUnpaidMembershipFeeId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(UnpaidMembershipFeeFormController.class.getResource("/View/UnpaidFeeBarForm.fxml"));
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
        ArrayList<String> emailList = memberModel.getAllMemberEmailAddress();
        System.out.println(emailList);

        for (int i = 0; i < emailList.size(); i++) {
            String email = emailList.get(i);
            System.out.println(email);
            new SendText().sendMail("Reminder","Pay this year's your membership fee as soon as possible.!" ,email);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"DashboardForm.fxml");
    }
}
