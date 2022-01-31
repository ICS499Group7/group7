package com.group7;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    @FXML
    private Button logOutButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button userMainButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Images
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void logOutButtonOnAction(ActionEvent event) throws IOException {
        loginScreen();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void userMainButtonOnAction(ActionEvent event) throws IOException {
        userMainScreen();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 400));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void userMainScreen() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("userMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
