package com.group7.controllers;

import com.group7.model.AgentModel;
import com.group7.model.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.ActionEvent;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginButtonOnAction(ActionEvent event) {
        if ((usernameTextField.getText().isBlank() == false) && (enterPasswordField.getText().isBlank() == false)) {
            LoginModel login = new LoginModel(usernameTextField.getText(), enterPasswordField.getText());
            if (login.verifyLogin() == true) {

                if (enterPasswordField.getText().equals("Password123")){
                    loginMessageLabel.setText("Login Successful, please enter new password.");
                    modifyScreen();
                } else {
                    loginMessageLabel.setText("Login Successful");
                    homepageScreen();
                }
            } else {
                loginMessageLabel.setText("Invalid Login. Please Try Again");
            }
        } else {
            loginMessageLabel.setText("Please Enter Username and Password.");
        }
    }


    public void CancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void homepageScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/homepage.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600,400));
            registerStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void modifyScreen() {
        String username = usernameTextField.getText();
        ResultSet rs = AgentModel.getAgentDataByUsername(username);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/Agent/agentModifyForm.fxml"));
            Parent root = loader.load();
            AgentManageController modifyController = loader.getController();

            if(rs.next()){
                String id = rs.getString(1);
                String fName = rs.getString(2);
                String lName = rs.getString(3);
                String uName = rs.getString(4);

                modifyController.passAgentInfo(id, fName, lName, uName);
                modifyController.setRequirePass();
            }

                Stage registerStage = new Stage();
                registerStage.initStyle(StageStyle.UNDECORATED);
                registerStage.setScene(new Scene(root, 350, 450));
                registerStage.show();
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
