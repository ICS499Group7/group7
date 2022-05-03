package com.group7.controllers;

import com.group7.model.GuestModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GuestManageController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private Label statusMessageLabelModify;
    @FXML
    private Label guestIDLabel;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateOfBirthField;

    private String dateOfBirth;
    private String guestID;

    public void passGuestInfo(String id, String fName, String lName, String phone, String email, String dob){
        this.guestID = id;
        this.guestIDLabel.setText(id);
        this.firstName.setText(fName);
        this.lastName.setText(lName);
        this.phone.setText(phone);
        this.email.setText(email);
        this.dateOfBirth = dob;
        this.dateOfBirthField.setValue(LocalDate.parse(dob));
    }

    public void submitGuestButtonOnAction(ActionEvent event) throws Exception {
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (phone.getText().isBlank() == false) && (email.getText().isBlank() == false)){
            dateOfBirth = dateOfBirthField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            saveGuest();
            guestMainScreen();

        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }

    public void submitModifyButtonOnAction(ActionEvent event) throws Exception {
        dateOfBirth = dateOfBirthField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if ((firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (phone.getText().isBlank() == false) && (email.getText().isBlank() == false) && (dateOfBirth.isBlank() == false)) {
            modifyGuest();
            guestMainScreen();

        } else {
            statusMessageLabelModify.setText("There was an issue with the form. Please check Entries.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        guestMainScreen();
    }

    public void saveGuest() {
        boolean addGuestQuery = new GuestModel().addGuest(firstName.getText(),lastName.getText(),phone.getText(),email.getText(),dateOfBirth);

        if (addGuestQuery == true) {
            statusMessageLabel.setText("Created Agent Successfully");
        } else {
            statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void modifyGuest() {
        boolean modifyGuestQuery = new GuestModel().modifyGuest(guestID, firstName.getText(),lastName.getText(),phone.getText(),email.getText(),dateOfBirth);

        System.out.println("in Save Guest = " + modifyGuestQuery);

        if (modifyGuestQuery == true) {
            // statusMessageLabel.setText("Updated User Successfully");
        } else {
            //  statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void guestMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Guest/guestMain.fxml"));
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
