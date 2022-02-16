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

import java.net.URL;
import java.util.ResourceBundle;

public class OwnerModifyController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField ownerIDModify;
    @FXML
    private TextField companyNameModify;
    @FXML
    private TextField addressModify;
    @FXML
    private TextField zipCodeModify;
    @FXML
    private TextField firstNameModify;
    @FXML
    private TextField lastNameModify;
    @FXML
    private TextField mobilePhoneModify;
    @FXML
    private TextField officePhoneModify;
    @FXML
    private TextField emailModify;
    @FXML
    private Label statusMessageLabelModify;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void passOwnerInfo(String id, String cName, String addr, String zip, String fName, String lName, String mPhone, String oPhone, String em){
        ownerIDModify.setText(id);
        companyNameModify.setText(cName);
        addressModify.setText(addr);
        zipCodeModify.setText(zip);
        firstNameModify.setText(fName);
        lastNameModify.setText(lName);
        mobilePhoneModify.setText(mPhone);
        officePhoneModify.setText(oPhone);
        emailModify.setText(em);
    }

    public void showInformation(String id, String cName, String addr, String zip, String fName, String lName, String mPhone, String cPhone, String email){

        ownerIDModify.setText(id);
        companyNameModify.setText(cName);
        addressModify.setText(addr);
        zipCodeModify.setText(zip);
        firstNameModify.setText(fName);
        lastNameModify.setText(lName);
        mobilePhoneModify.setText(mPhone);
        officePhoneModify.setText(cPhone);
        emailModify.setText(email);

    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
        if ((companyNameModify.getText().isBlank() == false) && (addressModify.getText().isBlank() == false) && (zipCodeModify.getText().isBlank() == false) && (firstNameModify.getText().isBlank() == false) && (lastNameModify.getText().isBlank() == false) && (mobilePhoneModify.getText().isBlank() == false) &&
                (officePhoneModify.getText().isBlank() == false) && (emailModify.getText().isBlank() == false)){
            saveOwner();
            ownerMainScreen();
        } else {
            statusMessageLabelModify.setText("There was an issue with the form. Please check Entries.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        ownerMainScreen();
    }

    public void saveOwner() {
        boolean modifyUserQuery = new OwnerModel().modifyOwner(ownerIDModify.getText(), companyNameModify.getText(), addressModify.getText(), zipCodeModify.getText(),firstNameModify.getText(),lastNameModify.getText(),mobilePhoneModify.getText(),officePhoneModify.getText(), emailModify.getText());

        System.out.println("in Save User = " + modifyUserQuery);

        if (modifyUserQuery == true) {
            // statusMessageLabel.setText("Updated User Successfully");
        } else {
            //  statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void ownerMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/ownerMain.fxml"));
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
