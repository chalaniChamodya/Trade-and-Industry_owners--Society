package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lk.ijse.Trade_and_Industrial_owners_Society.DbConnection.DBConnection;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class MembersFormController {
    public VBox vBox;
    public ImageView addMember;
    public Label lblAdd;
    public Pane btnAdd;
    public JFXButton btnMember;
    public JFXButton btnFamily;
    public JFXButton btnCommittee;
    public AnchorPane pagingPane;
    private static MembersFormController controller;
    public JFXButton btnDetailReport;

    public MembersFormController(){controller = this;}

    public static MembersFormController getInstance() {
        return controller;
    }

    void btnAddSelected(JFXButton btn, Label add, ImageView img, String path){
        btn.setStyle(
                "-fx-background-color: #E7AD5D;"+
                        "-fx-background-radius: 15px;"
        );
        add.setStyle("-fx-font-size: 24px;" +
                "-fx-text-fill: #533710;"
        );
        img.setImage(new Image("Assets/Imgs/"+ path));
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

    public void initialize() throws SQLException {
        getAllId();
        btnSelected(btnMember);
    }

    public void getAllId() throws SQLException {
        ArrayList<String> list = null;
        MemberModel memberModel = new MemberModel();
        list = memberModel.getAllMemberId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(MembersFormController.class.getResource("/View/MemberBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            MemberBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAddMemberOnAction(ActionEvent actionEvent) throws IOException {
        //btnAddSelected(btnAddMember, lblAdd, addMember, "addMember.png");
        Navigation.switchPaging(pagingPane, "MemberAddForm.fxml");
    }

    public void btnFamilyOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"FamilyMemberForm.fxml");
    }

    public void btnMemberOnAction(ActionEvent actionEvent) {
        btnSelected(btnMember);
    }

    public void btnCommitteeOnAction(ActionEvent actionEvent) throws IOException {
        btnSelected(btnCommittee);
        btnUnselected(btnMember);
        btnUnselected(btnFamily);
       // btnUnselected(btnAdd);
        Navigation.switchPaging(pagingPane, "CommitteeMemberForm.fxml");
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        btnSelected(btnDetailReport);
        InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/MemberDetails.jrxml");
        JasperDesign load = null;
        try {
            load = JRXmlLoader.load(resourceAsStream);

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("" +
                    "SELECT * FROM member");
            load.setQuery(jrDesignQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport, //compiled report
                            null,
                            DBConnection.getInstance().getConnection() //database connection
                    );

            net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "/home/chalani/Documents/name.pdf");

        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
        //btnUnselected(btnDetailReport);
    }
}
