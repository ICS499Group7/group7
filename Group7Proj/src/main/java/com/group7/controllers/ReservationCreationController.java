package com.group7.controllers;

import com.group7.model.GuestModel;
import com.group7.model.PropertyModel;
import com.group7.model.ReservationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationCreationController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button reserveButton;
    @FXML
    private ComboBox propertyChoice;
    @FXML
    private ChoiceBox guestChoice;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label avaliabilityStatus;

    private ObservableList<String> propertyItems = FXCollections.observableArrayList();
    private ObservableList<String> guestItems = FXCollections.observableArrayList();
    private ObservableList<LocalDate> items = FXCollections.observableArrayList();
    private PropertyModel property = new PropertyModel();
    private GuestModel guest = new GuestModel();
    private ReservationModel reservations = new ReservationModel();



    private String sDate;
    private String eDate;
    private String p;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResultSet rsp = property.getProperties();
        ResultSet rsg = guest.getGuests();
        try {
            while (rsp.next()) {
                propertyItems.add(rsp.getString(2));
            }
            propertyChoice.setItems(propertyItems);
            while (rsg.next()) {
                guestItems.add(rsg.getString(2) + " " + rsg.getString(3));
            }
            guestChoice.setItems(guestItems);
        } catch(Exception e) {}



    }

    public void checkButtonOnSubmit() {

    }

    public void setPropertyOnAction() {
        p = propertyChoice.getValue().toString();
        System.out.println(p);
        ResultSet rs = reservations.getReservationsByPropertyName(p);
        items.clear();

        String rStart;
        String rEnd;

        try{
            while (rs.next()) {
                rStart = rs.getString(5);
                rEnd = rs.getString(6);
                items.add(LocalDate.parse(rStart));
                items.add(LocalDate.parse(rEnd));

            }
            System.out.println("IMPORT: " + items.size());
            restrictDatePicker(startDate,items);

        }catch (Exception e){

        }
    }




    public void restrictDatePicker(DatePicker datePicker,ObservableList<LocalDate> listItems) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>(){
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (listItems.size() == 0){
                            super.updateItem(item, empty);
                        }
                        for(int i=0; i < listItems.size(); i=i+2){
                            LocalDate minDate = listItems.get(i);
                            LocalDate maxDate = listItems.get(i+1);
                            super.updateItem(item, empty);
                            if (item.isAfter(minDate) && item.isBefore(maxDate) || item.isEqual(minDate) || item.isEqual(maxDate)) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }

                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    public void triggerEndUpdate() {
        //restrictDatePicker(endDate,LocalDate.parse(startDate.getValue().toString()).plusDays(1));
    }

    public void reserveButtonOnAction() {
        sDate = startDate.getValue().toString();
        eDate = endDate.getValue().toString();
        p = propertyChoice.getValue().toString();

        String rStart;
        String rEnd;

        if (sDate.compareTo(eDate) >= 0){
            avaliabilityStatus.setText("End date must be at least 1 day after start date!");
            return;
        }


        System.out.println("[" + sDate + ", " + eDate + ", " + p +"]");

        ResultSet rs = reservations.getReservationsByPropertyName(p);
        boolean isValid = true;
        try{
            while (rs.next()){
                rStart = rs.getString(5);
                rEnd = rs.getString(6);
                System.out.println("[" + rStart + ", " + rEnd + "]");

                if ((rEnd.compareTo(sDate) < 0) || ((rStart.compareTo(sDate) > 0) && (rStart.compareTo(eDate) > 0))) {
                    continue;
                } else {
                    isValid = false;
                }
            }

            if (isValid == true) {
                avaliabilityStatus.setText("Reservation is avaliable!");
            } else {
                avaliabilityStatus.setText("Not avaliable!");
            }

        } catch (Exception e) {

        }
    }

    public void backButtonOnAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/homepage.fxml"));
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
