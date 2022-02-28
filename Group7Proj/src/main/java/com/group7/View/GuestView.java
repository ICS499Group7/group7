package com.group7.View;

import com.group7.controllers.GuestManageController;
import com.group7.model.GuestModel;
import com.group7.model.LoginModel;
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

public class GuestView implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private TableView tableView;
    @FXML
    private Label statusMessageLabel;

    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private GuestModel guest = new GuestModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResultSet rs = guest.getGuests();
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
        homepage();
    }

    public void createGuestButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/guestAddForm.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 350, 450));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void modifyGuestButtonOnAction(ActionEvent event) throws IOException {
        try {
            if (tableView.getSelectionModel().getSelectedIndex() != -1) {
                String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
                items = items.substring(1, items.length() - 1);
                List<String> id = Arrays.asList(items.split(",\\s*"));
                System.out.println(id);

                //This code is slightly different as I needed to get at .getController to transfer content from 1 scene to the next scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/guestModifyForm.fxml"));
                Parent root = loader.load();
                GuestManageController modifyController = loader.getController();
                modifyController.passGuestInfo(id.get(0), id.get(1), id.get(2), id.get(3), id.get(4), id.get(5));

                if (!id.get(3).startsWith("!") || LoginModel.admin) {
                    Stage registerStage = new Stage();
                    registerStage.initStyle(StageStyle.UNDECORATED);
                    registerStage.setScene(new Scene(root, 350, 450));
                    registerStage.show();
                    Stage stage = (Stage) backButton.getScene().getWindow();
                    stage.close();
                } else {
                    statusMessageLabel.setText("Please contact an Administrator.  You must have administrative rights to change account to an Administrators account");
                }
            } else {
                statusMessageLabel.setText("Please Select an Agent to Modify from the Table and Try Again");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void deleteGuestButtonOnAction(ActionEvent event) {
        if (LoginModel.admin)
            try {
                String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
                items = items.substring(1, items.length() - 1);
                List<String> id = Arrays.asList(items.split(",\\s*"));
                if (guest.deleteGuest(id.get(0)))
                    tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        else {
            statusMessageLabel.setText("Please contact an administrator.  You must have administrative rights to delete a Guest");

        }
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
