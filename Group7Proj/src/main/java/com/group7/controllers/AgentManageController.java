package com.group7.controllers;

import com.group7.model.AgentModel;
import com.group7.model.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

public class AgentManageController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private Label statusMessageLabelModify;
    @FXML
    private Label agentIDLabel;
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
    private String agentID;
    private static String dbError;


    public void passAgentInfo(String id, String fName, String lName, String userName, String password, String passwordConfirm){
        this.agentID = id;
        this.agentIDLabel.setText(id);
        this.firstName.setText(fName);
        this.lastName.setText(lName);
        this.userName.setText(userName);
        this.password.setText(password);
        this.passwordConfirm.setText(passwordConfirm);
    }

    public void submitAgentButtonOnAction(ActionEvent event) throws Exception {
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (userName.getText().isBlank() == false) && (password.getText().isBlank() == false) && (passwordConfirm.getText().equals(password.getText()))) {

            if (!userName.getText().substring(userName.getText().length() - 1).equals("!") || LoginModel.admin) {
                if (checkPassword(this.password.getText())) {
                    if (saveAgent())
                        agentMainScreen();
                } else {
                    statusMessageLabel.setText("Please try again, the password must have 8 characters and include at least 1 number, 1 upper case letter, and 1 lower case letter.");
                }
            } else {
                statusMessageLabel.setText("Please contact an Administrator.  You must have administrative rights to add an Administrators account");
            }
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }

    public void submitModifyButtonOnAction(ActionEvent event) throws Exception {
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (userName.getText().isBlank() == false) && (password.getText().isBlank() == false) && (passwordConfirm.getText().equals(password.getText()))) {

            if (!userName.getText().substring(userName.getText().length() - 1).equals("!") || LoginModel.admin) {
                if (checkPassword(this.password.getText())) {
                    if (modifyAgent())
                        agentMainScreen();
                } else {
                    statusMessageLabelModify.setText("Please try again, the password must have 8 characters and include at least 1 number, 1 upper case letter, and 1 lower case letter.");
                }
            } else {
                statusMessageLabelModify.setText("Please contact an Administrator.  You must have administrative rights to change account to an Administrators account");
            }
        } else {
            statusMessageLabelModify.setText("There was an issue with the form. Please check Entries.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        agentMainScreen();
    }

    public boolean saveAgent() {
        boolean addAgentQuery = new AgentModel().addAgent(firstName.getText(),lastName.getText(),userName.getText(),password.getText());

        if (addAgentQuery == true) {
            statusMessageLabel.setText("Created Agent Successfully");
            return true;
        } else {
            statusMessageLabel.setText("The following error occurred - " + dbError);
            dbError = "";
            return false;
        }
    }

    public boolean modifyAgent() {
        boolean modifyAgentQuery = new AgentModel().modifyAgent(agentID, firstName.getText(),lastName.getText(),userName.getText(),password.getText());

        if (modifyAgentQuery == true) {
            statusMessageLabelModify.setText("Modified Agent Successfully");
            return true;
        } else {
            statusMessageLabelModify.setText("The following error occurred - " + dbError);
            dbError = "";
            return false;
        }
    }

    public void agentMainScreen() {
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

    public boolean checkPassword(String password) {
        boolean length = password.length() >= 8;
        System.out.println(length);
        boolean lower = password.matches(".*[a-z].*");  //if true password has a lower letter
        System.out.println(lower);
        boolean upper = password.matches(".*[A-Z].*"); //if true password has an uppercase letter
        System.out.println(upper);
        boolean number = password.matches(".*[0-9].*"); //if true password has number
        System.out.println(number);
        return length && lower && upper && number;
    }

    public String getDbError() {
        return dbError;
    }

    public void setDbError(String dbError) {
        this.dbError = dbError;
    }

}
