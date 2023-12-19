package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.DonationModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.SpecialScholModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.DonationTm;

import java.sql.SQLException;

public class DonationBarFormController {
    private static String id;
    public Label donationId;
    public Label date;
    public Label amount;
    public ImageView btnDelete;
    public ImageView btnUpdate;

    DonationModel donationModel = new DonationModel();
    SpecialScholModel scholModel = new SpecialScholModel();
    public static void getId(String id){
        DonationBarFormController.id = id;
    }

    public void setData(String id) {
        DonationTm donationTm = null;

        try {
            donationTm = donationModel.getDeathBenefitData(id);
            this.donationId.setText(donationTm.getId());
            date.setText(donationTm.getDate());
            amount.setText(donationTm.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDataOfSchol(String id) {
        DonationTm scholTm = null;

        try {
            scholTm = donationModel.geScholarshipData(id);
            this.donationId.setText(scholTm.getId());
            date.setText(scholTm.getDate());
            amount.setText(scholTm.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSpecialScholData(String id) {
        DonationTm scholTm = null;

        try {
            scholTm = scholModel.getScholarshipData(id);
            this.donationId.setText(scholTm.getId());
            date.setText(scholTm.getDate());
            amount.setText(scholTm.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
        new Alert(Alert.AlertType.ERROR,"Can't Update Donation Details !").show();
    }
}
