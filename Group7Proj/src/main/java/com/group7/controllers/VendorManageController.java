package com.group7.controllers;

import com.group7.model.AddressModel;
import com.group7.model.VendorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class VendorManageController implements Initializable {
    @FXML
    private Label vendorIDLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField companyName;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField state;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private Label statusMessageLabel;

    private String vendorID;
    private String addressID;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cancelButtonOnAction(ActionEvent event) {
        vendorMainScreen();
    }

    public void submitAddButtonOnAction(ActionEvent event) throws Exception {
        if (1 == 1){
            addVendor();
            vendorMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }
    public void submitModifyButtonOnAction(ActionEvent event) throws Exception {
        if ((companyName.getText().isBlank() == false) && (address.getText().isBlank() == false) && (zipCode.getText().isBlank() == false) && (firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (phone.getText().isBlank() == false) &&
                (email.getText().isBlank() == false)){
            modifyVendor();
            vendorMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check Entries.");
        }
    }


    public void addVendor() {

        addressID = new AddressModel().createAddress(address.getText(),city.getText(), zipCode.getText(), state.getText());
        System.out.println(addressID);

        boolean createUserQuery = new VendorModel().createVendor(companyName.getText(), addressID, firstName.getText(),lastName.getText(),phone.getText(),email.getText());

        if (createUserQuery == true) {
            statusMessageLabel.setText("Created User Successfully");
        } else {
            statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void modifyVendor() {
        String addressID = new VendorModel().getAddressIDByVendor(vendorID);
        boolean modifyAddressQuery = new AddressModel().modifyAddress(addressID, address.getText(),city.getText(), zipCode.getText(),state.getText());
        boolean modifyUserQuery = new VendorModel().modifyVendor(vendorID,companyName.getText(),firstName.getText(),lastName.getText(),phone.getText(), email.getText());

        System.out.println("in Save User = " + modifyUserQuery);

        if (modifyUserQuery == true && modifyAddressQuery == true) {
            // statusMessageLabel.setText("Updated User Successfully");
        } else {
            //  statusMessageLabel.setText("Error With DB Query");
        }
    }

    public void passVendorInfo(String id, String cName, String fName, String lName, String phone, String em) throws SQLException {
        String street;
        String city;
        String zip;
        String state;

        String addrID = new VendorModel().getAddressIDByVendor(id);

        ResultSet rs = new AddressModel().getAddressByID(addrID);
        rs.next();
        street = rs.getString(1);
        city = rs.getString(2);
        zip = rs.getString(3);
        state = rs.getString(4);

        this.vendorID = id;
        this.vendorIDLabel.setText(id);
        this.companyName.setText(cName);
        this.address.setText(street);
        this.city.setText(city);
        this.zipCode.setText(zip);
        this.state.setText(state);
        this.firstName.setText(fName);
        this.lastName.setText(lName);
        this.phone.setText(phone);
        this.email.setText(em);
    }

    public void vendorMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/vendorMain.fxml"));
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
