package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.SponsorModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.SponsorTm;

import java.sql.SQLException;

public class SponsorBarFormController {
    public Label SponsorId;
    public Label programId;
    public Label sponsorName;
    public ImageView btnDelete;
    public ImageView btnUpdate;
    SponsorModel sponsorModel = new SponsorModel();

    public void setData(String id) {
        SponsorTm sponsorTm = null;
        try {
            sponsorTm = sponsorModel.getData(id);
            this.SponsorId.setText(sponsorTm.getSponsor_id());
            programId.setText(sponsorTm.getProgram_id());
            sponsorName.setText(sponsorTm.getSponsor_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) {
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) {
    }
}
