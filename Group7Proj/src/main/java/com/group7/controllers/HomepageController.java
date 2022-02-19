package com.group7.controllers;

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
    private Button exitButton;

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

    public void agentMainButtonOnAction(ActionEvent event) throws IOException {
        agentMainScreen();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void ownerMainButtonOnAction(ActionEvent event) throws IOException {
        ownerMainScreen();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void propertyMainButtonOnAction(ActionEvent event) throws IOException {
        propertyMainScreen();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 400));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void agentMainScreen() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/agentMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void ownerMainScreen() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/ownerMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void propertyMainScreen() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/propertyMain.fxml"));
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
