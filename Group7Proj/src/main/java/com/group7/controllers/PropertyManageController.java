package com.group7.controllers;

import com.group7.model.AddressModel;
import com.group7.model.PropertyModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PropertyManageController {
    @FXML
    private TextField propertyName;
    @FXML
    private TextField type;
    @FXML
    private TextField rate;
    @FXML
    private TextField ownerID;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField state;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private Button cancelButton;

    private String propertyID;
    private String addressID;

    public void cancelButtonOnAction(ActionEvent event) {
        propertyMainScreen();
    }

    public void submitAddButtonOnAction(ActionEvent event) throws Exception {
        if (1 == 1){
            addProperty();
            propertyMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }

    public void submitModifyButtonOnAction(ActionEvent event) throws Exception {
        if ((propertyName.getText().isBlank() == false) && (address.getText().isBlank() == false) && (zipCode.getText().isBlank() == false) && (state.getText().isBlank() == false) && (city.getText().isBlank() == false) && (type.getText().isBlank() == false) &&
                (rate.getText().isBlank() == false)){
            modifyProperty();
            propertyMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check Entries.");
        }
    }


    public void addProperty() {

        addressID = new AddressModel().createAddress(address.getText(),city.getText(), zipCode.getText(), state.getText());
        System.out.println(addressID);

        boolean createPropertyQuery = new PropertyModel().createProperty(propertyName.getText(), type.getText(), rate.getText(), addressID, ownerID.getText());

        if (createPropertyQuery == true) {
            statusMessageLabel.setText("Created Property Successfully");
        } else {
            statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void modifyProperty() {
        String addressID = new PropertyModel().getAddressIDByProperty(propertyID);
        boolean modifyAddressQuery = new AddressModel().modifyAddress(addressID, address.getText(),city.getText(), zipCode.getText(),state.getText());
        boolean modifyUserQuery = new PropertyModel().modifyProperty(propertyID, propertyName.getText(),type.getText(),rate.getText(),ownerID.getText());

        System.out.println("in Save User = " + modifyUserQuery);

        if (modifyUserQuery == true && modifyAddressQuery == true) {
            // statusMessageLabel.setText("Updated User Successfully");
        } else {
            //  statusMessageLabel.setText("Error With DB Query");
        }
    }

    public void passPropertyInfo(String id, String propertyName, String type, String rate, String oID) throws SQLException {

        String street;
        String city;
        String zip;
        String state;

        String addrID = new PropertyModel().getAddressIDByProperty(id);

        ResultSet rs = new AddressModel().getAddressByID(addrID);
        rs.next();
        street = rs.getString(1);
        city = rs.getString(2);
        zip = rs.getString(3);
        state = rs.getString(4);

        this.propertyID = id;
        this.propertyName.setText(propertyName);
        this.type.setText(type);
        this.rate.setText(rate);
        this.ownerID.setText(oID);
        this.address.setText(street);
        this.city.setText(city);
        this.zipCode.setText(zip);
        this.state.setText(state);

    }

    public void propertyMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/propertyMain.fxml"));
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
