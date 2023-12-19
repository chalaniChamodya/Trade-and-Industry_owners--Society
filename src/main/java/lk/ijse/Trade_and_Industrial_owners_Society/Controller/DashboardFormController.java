package lk.ijse.Trade_and_Industrial_owners_Society.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.DashboardModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Model.MemberModel;
import lk.ijse.Trade_and_Industrial_owners_Society.Utill.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardFormController {
    public Label lblGeneralAttendance;
    public Label lblCommitteeAttendance;
    public Label lblSubscriptionUnpaid;
    public Label lblMembershipUnpaid;
    public Pane panePieChart;
    private static Stage stage;
    public Pane panePieChart1;
    public Pane pagingPane;
    public VBox vBox;

    DashboardModel dashboardModel = new DashboardModel();

    public void initialize() throws SQLException {
        dashboardModel.generalMeetingAttendanceCount();
        lblGeneralAttendance.setText(String.valueOf(dashboardModel.generalMeetingAttendanceCount()));
        lblCommitteeAttendance.setText(String.valueOf(dashboardModel.committeeMeetingAttendanceCount()));
        lblSubscriptionUnpaid.setText(String.valueOf(dashboardModel.unPaidSubscriptionFeeCount()));
        lblMembershipUnpaid.setText(String.valueOf(dashboardModel.unPaidMembershipFeeCount()));
        pieChart();
        comboChart();
        getUpcomingMeeting();
    }

    private void getUpcomingMeeting() throws SQLException {
        ArrayList<String> list = null;
        DashboardModel dashboardModel = new DashboardModel();
        list = dashboardModel.getUpComingMeetingId();

        vBox.getChildren().clear();
        for(int i = 0; i< list.size(); i++){
            loadTableData(list.get(i));
        }
    }

    private void loadTableData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(DashboardFormController.class.getResource("/View/DashboardMeetingBarForm.fxml"));
            Parent root = null;
            root = loader.load();
            DashboardMeetingBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void pieChart(){
        PieChart pieChart = new PieChart();

        try {
            ObservableList<PieChart.Data> pieChartData = dashboardModel.getFundDataForPieChart();
            pieChart.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        panePieChart.getChildren().add(pieChart);
    }

    public void comboChart() throws SQLException {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        xAxis.setLabel("Category");
        yAxis.setLabel("Amount");

        ObservableList<XYChart.Data<String, Number>> fundData = dashboardModel.monthlyFund();
        ObservableList<XYChart.Data<String, Number>> expenseData = dashboardModel.monthlyExpense();

        XYChart.Series<String, Number> fundSeries = new XYChart.Series<>("Funds", fundData);
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>("Expenses", expenseData);

        barChart.getData().addAll(fundSeries);
        lineChart.getData().addAll(expenseSeries);

        panePieChart1.getChildren().addAll(barChart, lineChart);
    }

    public void btnSubscriptionFeeUnpaidOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"UnPaidSubscriptionFeeForm.fxml");
    }

    public void btnGenMeetingAttendanceOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"GeneralMeetingAttendanceForm.fxml");
    }

    public void btnComMeetingAttendanceOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"CommitteeMeetingAttendanceForm.fxml");
    }

    public void btnMembershipFeeUnpaidOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.switchPaging(pagingPane,"UnpaidMembershipFeeForm.fxml");
    }
}
