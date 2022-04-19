package com.group7.controllers;

import com.group7.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

/*
Controls the creation of reservations on the Add Reservation page of the program.
 */
public class ReservationCreationController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private ComboBox propertyChoice;
    @FXML
    private ChoiceBox guestChoice;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label statusLabel;

    private ObservableList<String> propertyItems = FXCollections.observableArrayList();
    private ObservableList<String> guestItems = FXCollections.observableArrayList();
    private ObservableList<LocalDate> items = FXCollections.observableArrayList();
    private PropertyModel property = new PropertyModel();
    private GuestModel guest = new GuestModel();
    private ReservationModel reservations = new ReservationModel();

    private String sDate;
    private String eDate;
    private String p;
    private String g;
    private String a;

/*
Runs when class is first entered.
 */
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

        statusLabel.setText("");

    }

    /*
    When a property is selected from the drop down, this method will trigger, setting the date restrictions based on current reservations in the system.
     */
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
            restrictDatePicker(endDate,items);

        }catch (Exception e){

        }
    }

/*
  This will take in both a datePicker object and a list of dates to block out.
  All dates given in the list will be marked as red and unselectable.
  All dates previous to today will also be highlighted yellow.
  Returns void.
 */
    public void restrictDatePicker(DatePicker datePicker,ObservableList<LocalDate> listItems) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>(){
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isBefore(LocalDate.now())) {
                            setStyle("-fx-background-color: #FFFFE0;");
                        }
                        if (listItems.size() == 0){
                            super.updateItem(item, empty);
                        }
                        for(int i=0; i < listItems.size(); i=i+2){
                            LocalDate minDate = listItems.get(i);
                            LocalDate maxDate = listItems.get(i+1);
                            super.updateItem(item, empty);
                            if (item.isAfter(minDate) && item.isBefore(maxDate) || item.isEqual(minDate) || item.isEqual(maxDate)) {
                                setDisable(true);
                                //setStyle("-fx-background-color: #ffc0cb;");
                                setFont(Font.font("family", FontWeight.BOLD, FontPosture.REGULAR, 12));
                                setTextFill(Color.RED);
                            }

                        }

                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    /*
    Triggers when the reserve button is pressed.
    It will get the start + end dates of the reservation, as well as the chosen property.
    This method is used as a buffer, to first check if the selected dates are avaliable to reserve, and return an error message if they are not.
     */
    public void reserveButtonOnAction() {
        sDate = startDate.getValue().toString();
        eDate = endDate.getValue().toString();
        p = propertyChoice.getValue().toString();

        String rStart;
        String rEnd;

        if (sDate.compareTo(eDate) >= 0){
            statusLabel.setText("End date must be at least 1 day after start date!");
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
                statusLabel.setText("Reservation is avaliable!");
                createReservation();
            } else {
                statusLabel.setText("Not avaliable!");
            }

        } catch (Exception e) {

        }
    }

    /*
    If reservation is avaliable, this method is triggered.
    It will get the current property + guest information, as well as the start + end date from the fields.
    It will then call the model to create a new reservation entry, returning the success / fail message to the user.
     */
    public void createReservation() {
        p = property.getPropertyIdByName(propertyChoice.getValue().toString());
        String fName = guestChoice.getValue().toString().split(" ")[0];
        String lName = guestChoice.getValue().toString().split(" ")[1];
        g = guest.getGuestIdByName(fName, lName);

        a = AgentModel.getAgentIdByName(LoginModel.usernameFromLoginForm);

        System.out.println(p +" AND " +g);

        sDate = startDate.getValue().toString();
        eDate = endDate.getValue().toString();
        String cDate = String.valueOf(LocalDate.now());


        String totalValue = Long.toString(Integer.parseInt(property.getValueByProperty(p)) * DAYS.between(startDate.getValue(), endDate.getValue()));


        boolean createReservationQuery = reservations.createReservation(p,g,a,sDate,eDate,totalValue,cDate);
        if (createReservationQuery == true) {
            statusLabel.setText("Created Reservation Successfully");
        } else {
            statusLabel.setText("Error With DB Query");
        }

    }

    /*
    modes the current screen back to the reservation main screen if the back button is pressed.
     */
    public void backButtonOnAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/reservationMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 800, 600));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


}
