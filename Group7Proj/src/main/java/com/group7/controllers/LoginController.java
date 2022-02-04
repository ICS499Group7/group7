package com.group7.controllers;

import com.group7.model.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
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
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("../../../../Images/Brand.png");
        Image brandingImage = new Image(brandingFile.toURI().toString()) ;
        brandingImageView.setImage(brandingImage);

    }

    public void loginButtonOnAction(ActionEvent event) {
        if ((usernameTextField.getText().isBlank() == false) && (enterPasswordField.getText().isBlank() == false)) {
            LoginModel login = new LoginModel(usernameTextField.getText(), enterPasswordField.getText());
            if (login.verifyLogin() == true) {
                loginMessageLabel.setText("Login Successful");
                homepageScreen();
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

}
