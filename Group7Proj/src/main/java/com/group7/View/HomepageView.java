package com.group7.View;

import com.group7.controllers.AgentManageController;
import com.group7.model.AgentModel;
import com.group7.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageView implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AgentModel agent = new AgentModel();

        String name = agent.getAgentNameByUsername(LoginModel.usernameFromLoginForm);

        if (name.startsWith("!")) {
            name = name.substring(1);

            if (name.length() == 0) {
                name = "Admin";
            }

        }
        welcomeText.setText("Welcome " + name);
    }

    public void exitButtonOnAction(ActionEvent event) {
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

    public void agentMainScreen() {
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

    public void reservationMainButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/reservationMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 800, 600));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loadModifyAgentForCurrentUser(){
        ResultSet rs = new AgentModel().getAgentDataByUsername(LoginModel.usernameFromLoginForm);
        List<String> currentUser = new ArrayList<>();

        try {

            if(rs.next()) {
                for (int i = 1; i < 6; i++) {
                    currentUser.add(rs.getString(i));
                }
            } else {
                //db error
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/agent/agentModifyForm.fxml"));
            Parent root = loader.load();
            AgentManageController modifyController = loader.getController();
            modifyController.passAgentInfo(currentUser.get(0), currentUser.get(1), currentUser.get(2),
                    currentUser.get(3));
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
