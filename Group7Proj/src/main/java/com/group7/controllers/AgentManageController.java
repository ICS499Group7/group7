package com.group7.controllers;

import com.group7.model.AgentModel;
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

public class AgentManageController{
    @FXML
    private Button cancelButton;
    @FXML
    private Label statusMessageLabel;
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
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (userName.getText().isBlank() == false) && (password.getText().isBlank() == false) && (passwordConfirm.getText().equals(password.getText()))){
            saveAgent();
            agentMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (userName.getText().isBlank() == false) && (password.getText().isBlank() == false) && (passwordConfirm.getText().equals(password.getText()))){
            modifyAgent();
            agentMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check Entries.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        agentMainScreen();
    }

    public void saveAgent() {
        boolean addAgentQuery = new AgentModel().addAgent(firstName.getText(),lastName.getText(),userName.getText(),password.getText());

        if (addAgentQuery == true) {
            statusMessageLabel.setText("Created Agent Successfully");
        } else {
            statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void modifyAgent() {
        boolean modifyAgentQuery = new AgentModel().modifyAgent(agentID, firstName.getText(),lastName.getText(),userName.getText(),password.getText());

        System.out.println("in Save Agent = " + modifyAgentQuery);

        if (modifyAgentQuery == true) {
            // statusMessageLabel.setText("Updated User Successfully");
        } else {
            //  statusMessageLabel.setText("Error With DB Query");
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


}
