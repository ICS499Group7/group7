package com.group7.View;

import com.group7.controllers.AgentManageController;
import com.group7.model.AgentModel;
import com.group7.model.LoginModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageView implements Initializable {
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
        if(LoginModel.admin) {
            agentMainScreen();
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
        else{
            loadModifyAgentForCurrentUser();
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
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

    public void guestMainButtonOnAction(ActionEvent event) throws IOException {
        guestMainScreen();
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

    public void guestMainScreen() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/guestMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loadModifyAgentForCurrentUser(){
        ResultSet rs = new AgentModel().getAgents();
        List<String> currentUser = new ArrayList<>();
        try {
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i=1; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                if (rs.getString(4).equals(LoginModel.username)) {
                    for(int i = 1; i < 6; i++ ) {
                        currentUser.add(rs.getString(i));
                    }
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/agentModifyForm.fxml"));
            Parent root = loader.load();
            AgentManageController modifyController = loader.getController();
            modifyController.passAgentInfo(currentUser.get(0), currentUser.get(1), currentUser.get(2),
                    currentUser.get(3), currentUser.get(4), currentUser.get(4));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 350, 450));
            registerStage.show();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


}
