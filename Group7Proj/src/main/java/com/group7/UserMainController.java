package com.group7;

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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserMainController implements Initializable {
    private Label statusMessageLabel;
    @FXML
    private Button backButton;

    @FXML
    private TableView tableView;

    @FXML
    private Button createUserButton;

    private ObservableList<ObservableList> items = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM user_accounts";
        try {
            stmt = connectDB.createStatement();
            rs = stmt.executeQuery(query);

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

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String id = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).toString();
        id = id.substring(1, id.length() - 1);
        System.out.println(id);
        List<String> items = Arrays.asList(id.split(",\\s*"));

        String deleteUserQuery = "DELETE FROM user_accounts WHERE id = " + items.get(0);
        System.out.println(deleteUserQuery);
        try {
            int queryResult = connectDB.createStatement().executeUpdate(deleteUserQuery);
            if (queryResult == 1)
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


    }

    public void homepage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("userAddForm.fxml"));
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


}

