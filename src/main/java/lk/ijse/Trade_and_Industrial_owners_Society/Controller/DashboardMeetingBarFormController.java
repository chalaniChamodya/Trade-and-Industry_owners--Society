package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.scene.control.Label;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.DashboardModel;
import lk.ijse.Trade_and_Industrial_owners_Society.TM.MeetingTm;

import java.sql.SQLException;

public class DashboardMeetingBarFormController {
    public Label lblMeetingType;
    public Label lblDate;

    DashboardModel dashboardModel = new DashboardModel();

    public void setData(String id) throws SQLException {
        MeetingTm meetingTm = null;
        meetingTm = dashboardModel.getData(id);
        if(id.startsWith("G")){
            lblMeetingType.setText("General");
        }else if(id.startsWith("C")){
            lblMeetingType.setText("Committee");
        }
        lblDate.setText(meetingTm.getDate());
    }
}
