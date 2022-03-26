package com.group7.View;

import com.group7.controllers.OwnerManageController;
import com.group7.controllers.PropertyManageController;
import com.group7.controllers.ReservationController;
import com.group7.model.PropertyModel;
import com.group7.model.ReservationModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class ReservationView implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private Button backButton;

    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private ReservationModel reservations = new ReservationModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = reservations.getReservations();

        try {
            for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i=1; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                items.add(row);
            }

            tableView.setItems(items);
        } catch(Exception e) {}

    }

    public void createReservationButtonOnAction() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/reservationCreate.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void filterButtonOnAction() {
        /*
        String filterStart = null;
        String filterEnd = null;
        int filterProperty = -1;
        int filterGuest = -1;

        tableView.getItems().clear();
        ResultSet rs = reservations.getReservations();

        if (filterStartDate.getValue() != null){
            filterEnd = filterStartDate.getValue().toString();
        }
        if (filterEndDate.getValue() != null){
            filterEnd = filterEndDate.getValue().toString();
        }
        try {
            for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i=1; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                items.add(row);
            }

            tableView.setItems(items);
        } catch(Exception e) {}
        */
    }

    public void viewReservationButtonOnAction() {
        try {
            if (tableView.getSelectionModel().getSelectedIndex() != -1) {
                String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
                items = items.substring(1, items.length() - 1);
                List<String> id = Arrays.asList(items.split(",\\s*"));
                System.out.println(id);

                //This code is slightly different as I needed to get at .getController to transfer content from 1 scene to the next scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/reservationDetail.fxml"));
                Parent root = loader.load();
                ReservationController viewController = loader.getController();
                viewController.passInfo(id.get(0));

                Stage registerStage = new Stage();
                registerStage.initStyle(StageStyle.UNDECORATED);
                registerStage.setScene(new Scene(root, 600, 400));
                registerStage.show();
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.close();
            } else {
                statusMessageLabel.setText("Please Select an Owner to Modify from the Table and Try Again");
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
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
