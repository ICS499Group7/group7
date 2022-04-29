package com.group7.controllers;

import com.group7.model.*;
import com.group7.model.OwnerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

/*
This class will display all of the relative information in a detailed screen for each particular reservation.
 */

public class ReservationController implements Initializable {

    @FXML
    private Label resId;
    @FXML
    private Label resValue;
    @FXML
    private Label resNights;
    @FXML
    private Label resStart;
    @FXML
    private Label resEnd;
    @FXML
    private Label resBooked;
    @FXML
    private Label propertyName;
    @FXML
    private Label propertyAddress;
    @FXML
    private Label propertyType;
    @FXML
    private Label propertyOwnerCompanyName;
    @FXML
    private Label propertyOwnerName;
    @FXML
    private Label propertyOwnerPhone;
    @FXML
    private Label propertyOwnerEmail;
    @FXML
    private Label vendorName;
    @FXML
    private Label vendorPhone;
    @FXML
    private Label vendorEmail;
    @FXML
    private Label guestName;
    @FXML
    private Label guestPhone;
    @FXML
    private Label guestEmail;
    @FXML
    private Label guestDateOfBirth;

    @FXML
    private Button backButton;
    @FXML
    private Button modifyButton;

    private ReservationModel reservations = new ReservationModel();
    private PropertyModel property = new PropertyModel();
    private OwnerModel owner = new OwnerModel();
    private GuestModel guest = new GuestModel();
    private VendorModel vendor = new VendorModel();

    String reservationIdData;
    String resValueData;
    String resNightsData;
    String resStartData;
    String resEndData;
    String resBookedData;
    String propertyIdData;
    String propertyNameData;
    String propertyAddressData;
    String propertyTypeData;
    String propertyOwnerIdData;
    String propertyOwnerCompanyNameData;
    String propertyOwnerNameData;
    String propertyOwnerPhoneData;
    String propertyOwnerEmailData;
    String vendorIdData;
    String vendorNameData;
    String vendorPhoneData;
    String vendorEmailData;
    String guestIdData;
    String guestNameData;
    String guestPhoneData;
    String guestEmailData;
    String guestDateOfBirthData;
    String agentIdData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void passInfo(String id){
        reservationIdData = id;
        fillData();
    }


/*
This method queries all of the needed tables in the DB for the information regarding the reservation.
 */
    public void fillData(){
        try {
            ResultSet resRs = reservations.getReservationDataById(reservationIdData);
            if (resRs.next()){
                guestIdData = resRs.getString(1);
                propertyIdData = resRs.getString(2);
                agentIdData = resRs.getString(3);
                resStartData = resRs.getString(4);
                resEndData = resRs.getString(5);
                resBookedData = resRs.getString(6);
                resValueData = resRs.getString(7);
                resNightsData = Long.toString(DAYS.between(LocalDate.parse(resStartData), LocalDate.parse(resEndData)));
            }


            ResultSet propRs = property.getPropertyDataById(propertyIdData);
            if (propRs.next()){
                propertyNameData = propRs.getString(1);
                propertyTypeData = propRs.getString(2);
                propertyOwnerIdData = propRs.getString(3);
                propertyAddressData = propRs.getString(4) + ", " + propRs.getString(5) + ", " + propRs.getString(6) + ", " + propRs.getString(7);
                vendorIdData = property.getVendorIdByPropertyId(propertyIdData);
            }

            ResultSet vendorRs = vendor.getVendorsDataByID(vendorIdData);
            if (vendorRs.next()) {
                vendorNameData = vendorRs.getString(1);
                vendorPhoneData = vendorRs.getString(2);
                vendorEmailData = vendorRs.getString(3);
            }


            ResultSet ownerRs = owner.getOwnerDataById(propertyOwnerIdData);
            if (ownerRs.next()){
                propertyOwnerCompanyNameData = ownerRs.getString(1);
                propertyOwnerNameData = ownerRs.getString(2) + " " + ownerRs.getString(3);
                propertyOwnerPhoneData = ownerRs.getString(4);
                propertyOwnerEmailData = ownerRs.getString(5);
            }

            ResultSet guestRs = guest.getGuestDataByID(guestIdData);
            if (guestRs.next()){
                guestNameData = guestRs.getString(2) + " " + guestRs.getString(3);
                guestPhoneData = guestRs.getString(4);
                guestEmailData = guestRs.getString(5);
                guestDateOfBirthData = guestRs.getString(6);
            }

        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        displayData();
    }

    /*
    This method then displays all of the data into the labels of the UI
     */
    public void displayData() {
        resId.setText("Reservation #" + reservationIdData);
        resValue.setText(resValueData);
        resNights.setText(resNightsData);
        resStart.setText(resStartData);
        resEnd.setText(resEndData);
        resBooked.setText(resBookedData);

        propertyName.setText(propertyNameData);
        propertyAddress.setText(propertyAddressData);
        propertyType.setText(propertyTypeData);

        vendorName.setText(vendorNameData);
        vendorPhone.setText(vendorPhoneData);
        vendorEmail.setText(vendorEmailData);


        propertyOwnerCompanyName.setText(propertyOwnerCompanyNameData);
        propertyOwnerName.setText(propertyOwnerNameData);
        propertyOwnerPhone.setText(propertyOwnerPhoneData);
        propertyOwnerEmail.setText(propertyOwnerEmailData);

        guestName.setText(guestNameData);
        guestPhone.setText(guestPhoneData);
        guestEmail.setText(guestEmailData);
        guestDateOfBirth.setText(guestDateOfBirthData);

    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/reservationMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
