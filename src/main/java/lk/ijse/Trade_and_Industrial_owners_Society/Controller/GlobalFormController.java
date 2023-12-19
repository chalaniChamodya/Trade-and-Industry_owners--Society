package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.GlobalModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class GlobalFormController {
    public JFXButton btnDashboard;
    public JFXButton btnMembers;
    public JFXButton btnMeetings;
    public JFXButton btnMembershipDues;
    public JFXButton btnFundingProgram;
    public JFXButton btnLogOut;
    public Label setTime;
    public Label setDate;
    public JFXButton btnClose;
    public JFXButton btnDonations;
    private static GlobalFormController controller;
    public AnchorPane pagingPane;
    public AnchorPane popupPane;
    public JFXTextField searchId;

    public GlobalFormController(){controller = this;}

    public static GlobalFormController getInstance(){return controller;}

    GlobalModel model = new GlobalModel();

    public void initialize(){
        btnSelected(btnDashboard);
        try {
            Navigation.switchPaging(pagingPane,"DashboardForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDateAndTime();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> setTime.setText(timeNow())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        // System.out.println(dateFormat.format(new Date()));
        return dateFormat.format(new Date()) ;
    }

    private void setDateAndTime() {
        setDate.setText(String.valueOf(LocalDate.now()));
    }

    void btnSelected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #E7AD5D;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #533710;"
        );
    }

    void btnUnselected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #E8E8E8;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #727374;"
        );
    }

    void logoutBtnSelected(JFXButton btn){
        btn.setStyle(
                "-fx-background-color: #FFD3D3;"+
                        "-fx-background-radius: 12px;"+
                        "-fx-text-fill: #FF2626;"
        );
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        btnSelected(btnDashboard);
        btnUnselected(btnMembers);
        btnUnselected(btnMeetings);
        btnUnselected(btnFundingProgram);
        btnUnselected(btnDonations);
        btnUnselected(btnMembershipDues);
        btnUnselected(btnLogOut);

        Navigation.switchPaging(pagingPane,"DashboardForm.fxml");
    }

    public void btnMembersOnAction(ActionEvent actionEvent) throws IOException {
        btnUnselected(btnLogOut);
        btnSelected(btnMembers);
        btnUnselected(btnMeetings);
        btnUnselected(btnDashboard);
        btnUnselected(btnDonations);
        btnUnselected(btnFundingProgram);
        btnUnselected(btnMembershipDues);

        Navigation.switchPaging(pagingPane,"MembersForm.fxml");
    }

    public void btnMeetingsOnAction(ActionEvent actionEvent) throws IOException {
        btnUnselected(btnLogOut);
        btnUnselected(btnMembers);
        btnSelected(btnMeetings);
        btnUnselected(btnDashboard);
        btnUnselected(btnDonations);
        btnUnselected(btnFundingProgram);
        btnUnselected(btnMembershipDues);

        Navigation.switchPaging(pagingPane,"GeneralMeetingForm.fxml");
    }

    public void btnMembershipDuesOnAction(ActionEvent actionEvent) throws IOException {
        btnUnselected(btnLogOut);
        btnUnselected(btnMembers);
        btnUnselected(btnMeetings);
        btnUnselected(btnDashboard);
        btnUnselected(btnDonations);
        btnUnselected(btnFundingProgram);
        btnSelected(btnMembershipDues);

        Navigation.switchPaging(pagingPane,"SubscriptionFeeForm.fxml");
    }

    public void btnFundingProgramOnAction(ActionEvent actionEvent) throws IOException {
        btnUnselected(btnLogOut);
        btnUnselected(btnMembers);
        btnUnselected(btnMeetings);
        btnUnselected(btnDashboard);
        btnUnselected(btnDonations);
        btnSelected(btnFundingProgram);
        btnUnselected(btnMembershipDues);

        Navigation.switchPaging(pagingPane,"FundingProgramForm.fxml");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        logoutBtnSelected(btnLogOut);
        btnUnselected(btnMembers);
        btnUnselected(btnDonations);
        btnUnselected(btnMeetings);
        btnUnselected(btnDashboard);
        btnUnselected(btnMembershipDues);
        btnUnselected(btnFundingProgram);

        Navigation.switchNavigation("LoginForm.fxml", actionEvent);
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnDonationsOnAction(ActionEvent actionEvent) throws IOException {
        btnUnselected(btnLogOut);
        btnUnselected(btnMembers);
        btnUnselected(btnMeetings);
        btnUnselected(btnDashboard);
        btnSelected(btnDonations);
        btnUnselected(btnFundingProgram);
        btnUnselected(btnMembershipDues);

        Navigation.switchPaging(pagingPane,"DeathBenefitForm.fxml");
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String searchTerm = searchId.getText();
        ArrayList<String> searchItems = model.search(searchTerm);

        if(searchItems.size() > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setContentText("Member ID : "+ searchItems.get(0) + "\nMember Name : "+ searchItems.get(1) + "\nJoined Date : " +searchItems.get(2)+"\nBusiness Type : "+
                    searchItems.get(3)+ "\nContact No : "+ searchItems.get(4)+ "\nAddress : "+searchItems.get(5));
            alert.showAndWait();
            searchId.setText("");
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No matching records found.", ButtonType.OK);
            alert.setTitle("Search Result");
            alert.setHeaderText("No Records Found");
            alert.showAndWait();
            searchId.setText("");
        }
    }
}
