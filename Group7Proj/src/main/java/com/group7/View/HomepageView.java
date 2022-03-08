package com.group7.View;

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

public class HomepageView implements Initializable {
    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Images
    }

    public void exitButtonOnAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void logOutButtonOnAction() {
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
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void agentMainButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Agent/agentMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void ownerMainButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Owner/ownerMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void propertyMainButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Property/propertyMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void guestMainButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Guest/guestMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void vendorMainButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Vendor/vendorMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
