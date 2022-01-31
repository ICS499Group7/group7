package com.group7;

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
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.util.ResourceBundle;
import java.net.URL;

public class UserAddController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirm;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (userName.getText().isBlank() == false) && (password.getText().isBlank() == false) && (passwordConfirm.getText().equals(password.getText()))){
            saveUser();
            userMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        userMainScreen();
    }

    public void saveUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "INSERT INTO user_accounts (first_name,last_name,username,password) VALUES ('" + firstName.getText() + "','" + lastName.getText() + "','" + userName.getText() + "','" + password.getText() + "')";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(createUserQuery);
            if (queryResult == 1) {
                statusMessageLabel.setText("Created User Successfully");
            } else {
                statusMessageLabel.setText("Error With DB Query");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void userMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("userMain.fxml"));
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
