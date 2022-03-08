package com.group7.controllers;

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
    private Button checkButton;
    @FXML
    private Button reserveButton;
    @FXML
    private ChoiceBox propertyChoice;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label avaliabilityStatus;

    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private PropertyModel property = new PropertyModel();
    private ReservationModel reservations = new ReservationModel();

    private String sDate;
    private String eDate;
    private String p;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reserveButton.setVisible(false);

        ResultSet rs = property.getProperties();
        try {
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString(2));
                items.add(row);
                System.out.println(row);
            }
            propertyChoice.setItems(items);
            restrictDatePicker(startDate,LocalDate.now());
            restrictDatePicker(endDate,LocalDate.now());
        } catch(Exception e) {}

    }

    public void checkButtonOnSubmit() {
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

        ResultSet rs = reservations.getReservationsByPropertyId(p);
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

    public void setPropertyOnAction() {
        p = propertyChoice.getValue().toString();
        ResultSet rs = reservations.getReservationsByPropertyId(p);

        String rStart;
        String rEnd;
        restrictDatePicker(startDate,LocalDate.now());
        try{
            while (rs.next()) {
                rStart = rs.getString(5);
                rEnd = rs.getString(6);

            }
        }catch (Exception e){

        }

    }


    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    public void triggerEndUpdate() {
        restrictDatePicker(endDate,LocalDate.parse(startDate.getValue().toString()).plusDays(1));
    }

    public void reserveButtonOnAction() {

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
