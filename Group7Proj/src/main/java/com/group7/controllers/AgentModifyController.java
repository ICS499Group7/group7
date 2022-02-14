package com.group7.controllers;

import com.group7.model.AgentModel;
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

import java.net.URL;
import java.util.ResourceBundle;

public class AgentModifyController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private Label statusMessageLabelModify;
    @FXML
    private TextField userIDModify;
    @FXML
    private TextField firstNameModify;
    @FXML
    private TextField lastNameModify;
    @FXML
    private TextField userNameModify;
    @FXML
    private PasswordField passwordModify;
    @FXML
    private PasswordField passwordConfirmModify;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showInformation(String id, String fName, String lName, String userName, String password, String passwordConfirm){

        userIDModify.setText(id);
        firstNameModify.setText(fName);
        lastNameModify.setText(lName);
        userNameModify.setText(userName);
        passwordModify.setText(password);
        passwordConfirmModify.setText(password);
    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
        if ((firstNameModify.getText().isBlank() == false) && (lastNameModify.getText().isBlank() == false) && (userNameModify.getText().isBlank() == false) &&
                (passwordModify.getText().isBlank() == false) && (passwordConfirmModify.getText().equals(passwordModify.getText()))){
            saveUser();
            userMainScreen();
        } else {
            statusMessageLabelModify.setText("There was an issue with the form. Please check Entries.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        userMainScreen();
    }

    public void saveUser() {
            boolean modifyUserQuery = new AgentModel().modifyUser(userIDModify.getText(), firstNameModify.getText(),lastNameModify.getText(),userNameModify.getText(),passwordModify.getText());

        System.out.println("in Save User = " + modifyUserQuery);

            if (modifyUserQuery == true) {
               // statusMessageLabel.setText("Updated User Successfully");
            } else {
              //  statusMessageLabel.setText("Error With DB Query");
            }

    }

    public void userMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/agentMain.fxml"));
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
