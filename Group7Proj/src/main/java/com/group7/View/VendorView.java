package com.group7.View;

import com.group7.controllers.VendorManageController;
import com.group7.model.LoginModel;
import com.group7.model.VendorModel;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class VendorView  implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private Button backButton;
    @FXML
    private Label statusMessageLabel;

    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private VendorModel vendor = new VendorModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = new VendorModel().getVendors();
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

    public void createVendorButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Vendor/vendorAddForm.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 500, 400));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void modifyVendorButtonOnAction(ActionEvent event) throws IOException {
        try {
            if (tableView.getSelectionModel().getSelectedIndex() != -1) {
                String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
                items = items.substring(1, items.length() - 1);
                List<String> id = Arrays.asList(items.split(",\\s*"));
                System.out.println(id);

                //This code is slightly different as I needed to get at .getController to transfer content from 1 scene to the next scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/Vendor/vendorModifyForm.fxml"));
                Parent root = loader.load();
                VendorManageController modifyController = loader.getController();
                modifyController.passVendorInfo(id.get(0), id.get(1), id.get(2), id.get(3), id.get(4), id.get(5));

                Stage registerStage = new Stage();
                registerStage.initStyle(StageStyle.UNDECORATED);
                registerStage.setScene(new Scene(root, 500, 400));
                registerStage.show();
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.close();
            } else {
                statusMessageLabel.setText("Please Select an Vendor to Modify from the Table and Try Again");
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void deleteVendorButtonOnAction(ActionEvent event) {
        try {
            if (vendor.deleteVendor(getSelectedVendorID()) && LoginModel.admin)
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public String getSelectedVendorID(){
        String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
        items = items.substring(1, items.length() - 1);
        List<String> id = Arrays.asList(items.split(",\\s*"));
        return id.get(0);
    }
}
