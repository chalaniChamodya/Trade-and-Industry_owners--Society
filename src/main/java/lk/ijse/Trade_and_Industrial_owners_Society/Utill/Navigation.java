package lk.ijse.Trade_and_Industrial_owners_Society.Utill;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.Trade_and_Industrial_owners_Society.Controller.GlobalFormController;

import java.io.IOException;

public class Navigation {
    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

    public static void switchNavigation(String link, ActionEvent event) throws IOException {
        parent = FXMLLoader.load(Navigation.class.getResource("/View/" + link));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchNavigation(String link, javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        parent = FXMLLoader.load(Navigation.class.getResource("/View/" + link));
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchPaging(AnchorPane pane, String path) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/View/"+path));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }

    public static void barPane(String path) throws IOException {
        GlobalFormController.getInstance().popupPane.setVisible(true);
        GlobalFormController.getInstance().pagingPane.setVisible(true);
        switchPaging(GlobalFormController.getInstance().pagingPane, path);
    }

    public static void switchPaging(Pane pane, String path) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/View/"+path));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }

    public static void switchNavigation(String link, KeyEvent event) throws IOException {
        parent = FXMLLoader.load(Navigation.class.getResource("/View/" + link));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
