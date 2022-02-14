package com.group7.controllers;

import com.group7.model.AgentModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AgentMainController implements Initializable {
    private Label statusMessageLabel;
    @FXML
    private Button backButton;

    @FXML
    private TableView tableView;

    @FXML
    private Button createUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private  Button modifyUserButton;


    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private AgentModel user = new AgentModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResultSet rs = new AgentModel().getUsers();
        try {
            for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
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

        } catch(Exception e) {

        }


    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        homepage();
    }

    public void createUserButtonOnAction(ActionEvent event) {
        createUserPage();
    }

    public void deleteUserButtonOnAction(ActionEvent event) {
        try {
            if (user.deleteUser(getSelectedUserID()))
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    @FXML
    public void modifyUserButtonOnAction(ActionEvent event) throws IOException {
      //  user.setId(getSelectedUserID());
        modifyUserPage();
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

    public void createUserPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/agentAddForm.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 300, 400));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void modifyUserPage() {
        try {
            String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
            items = items.substring(1, items.length() - 1);
            List<String> id = Arrays.asList(items.split(",\\s*"));
            System.out.println(id);

            //This code is slightly different as I needed to get at .getController to transfer content from 1 scene to the next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group7/agentModifyForm.fxml"));
            Parent root = loader.load();
            AgentModifyController modifyController = loader.getController();
            modifyController.showInformation(id.get(0), id.get(1), id.get(2), id.get(3), id.get(4), id.get(4));

            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 300, 400));
            registerStage.show();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public String getSelectedUserID(){
        String items = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
        items = items.substring(1, items.length() - 1);
        List<String> id = Arrays.asList(items.split(",\\s*"));
        return id.get(0);
    }


}

