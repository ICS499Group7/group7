package com.group7.controllers;

import com.group7.model.ReservationModel;
import com.group7.model.PropertyModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private Label statusMessageLabel;

    @FXML
    private Button backButton;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TextField searchCity;
    @FXML
    private ComboBox listZipCode;
    @FXML
    private ComboBox listCity;

    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private PropertyModel property = new PropertyModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void reservationMainButtonOnAction(ActionEvent event) {

        ResultSet rs = new PropertyModel().getProperties();
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


            ResultSet zip = property.uniquePropertyZipCodes();
            while(zip.next() ){
                listZipCode.getItems().add(zip.getString("zip_code"));
            }

            ResultSet city = property.uniquePropertyCity();

//            while(test.next() ){
//                listCity.getItems().add(test.getString("city"));
//            }


        } catch(Exception e) {}


    }

    public String getSelectedPropertyID(){
        String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
        items = items.substring(1, items.length() - 1);
        List<String> id = Arrays.asList(items.split(",\\s*"));
        return id.get(0);
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        homepage();
    }

    public void homepage() {
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