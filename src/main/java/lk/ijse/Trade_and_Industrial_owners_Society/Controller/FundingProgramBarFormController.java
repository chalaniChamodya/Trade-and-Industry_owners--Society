package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.FundingProgramModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.FundingProgramTm;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class FundingProgramBarFormController {
    private static String id;
    public Label programId;
    public Label programName;
    public Label date;
    public ImageView btnDelete;
    public ImageView btnUpdate;
    FundingProgramModel programModel = new FundingProgramModel();

    public static void getId(String id){
        FundingProgramBarFormController.id = id;
    }

    public void setData(String id) {
        FundingProgramTm programTm = null;
        try {
            programTm = programModel.getData(id);
            this.programId.setText(programTm.getProgram_id());
            programName.setText(programTm.getProgram_name());
            date.setText(programTm.getDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnDeleteOnAction(MouseEvent mouseEvent) throws SQLException {
        String id = programId.getText();
        boolean isDeleted = programModel.deleteFundingProgram(id);
        if(isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Program Deleted !").show();
            FundingProgramFormController.getInstance().getAllId();
            FundingProgramFormController.getInstance().generateNextFundingProgramId();
        }
    }

    public void btnUpdateOnAction(MouseEvent mouseEvent) throws IOException {
        FundingProgramUpdateFormController.getId(programId.getText());
        Navigation.barPane("FundingProgramUpdateForm.fxml");
    }
}
