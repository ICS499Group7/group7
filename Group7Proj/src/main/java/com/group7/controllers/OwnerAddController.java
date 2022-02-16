package com.group7.controllers;

import com.group7.model.OwnerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerAddController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField companyName;
    @FXML
    private TextField address;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField mobilePhone;
    @FXML
    private TextField officePhone;
    @FXML
    private TextField email;
    @FXML
    private Label statusMessageLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
        if (1 == 1){
            saveOwner();
            ownerMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        ownerMainScreen();
    }

    public void saveOwner() {
        boolean createUserQuery = new OwnerModel().createOwner(companyName.getText(), address.getText(), zipCode.getText(), firstName.getText(),lastName.getText(),mobilePhone.getText(),officePhone.getText(),email.getText());

        if (createUserQuery == true) {
            statusMessageLabel.setText("Created User Successfully");
        } else {
            statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void ownerMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/OwnerMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


}