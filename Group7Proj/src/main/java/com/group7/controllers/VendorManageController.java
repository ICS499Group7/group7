package com.group7.controllers;

import com.group7.model.AddressModel;
import com.group7.model.LoginModel;
import com.group7.model.PropertyModel;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VendorManageController implements Initializable {
    @FXML
    private Label vendorIDLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField companyName;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField state;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private TextField selectedVendor;
    @FXML
    private ComboBox chooseProperty;
    @FXML
    private TableView tableViewContracts;


    private String vendorID;
    private String propertyID;
    private String addressID;
    private ObservableList<ObservableList> items = FXCollections.observableArrayList();
    private ObservableList<String> propID = FXCollections.observableArrayList();
    private PropertyModel property = new PropertyModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cancelButtonOnAction(ActionEvent event) {
        vendorMainScreen();
    }

    public void submitAddButtonOnAction(ActionEvent event) throws Exception {
        if (1 == 1){
            addVendor();
            vendorMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check.");
        }
    }
    public void submitModifyButtonOnAction(ActionEvent event) throws Exception {
        if ((companyName.getText().isBlank() == false) && (address.getText().isBlank() == false) && (zipCode.getText().isBlank() == false) && (firstName.getText().isBlank() == false) && (lastName.getText().isBlank() == false) && (phone.getText().isBlank() == false) &&
                (email.getText().isBlank() == false)){
            modifyVendor();
            vendorMainScreen();
        } else {
            statusMessageLabel.setText("There was an issue with the form. Please check Entries.");
        }
    }


    public void addVendor() {

        addressID = new AddressModel().createAddress(address.getText(),city.getText(), zipCode.getText(), state.getText());
        System.out.println(addressID);

        boolean createUserQuery = new VendorModel().createVendor(companyName.getText(), addressID, firstName.getText(),lastName.getText(),phone.getText(),email.getText());

        if (createUserQuery == true) {
            statusMessageLabel.setText("Created User Successfully");
        } else {
            statusMessageLabel.setText("Error With DB Query");
        }

    }

    public void modifyVendor() {
        String addressID = new VendorModel().getAddressIDByVendor(vendorID);
        boolean modifyAddressQuery = new AddressModel().modifyAddress(addressID, address.getText(),city.getText(), zipCode.getText(),state.getText());
        boolean modifyUserQuery = new VendorModel().modifyVendor(vendorID,companyName.getText(),firstName.getText(),lastName.getText(),phone.getText(), email.getText());

        System.out.println("in Save User = " + modifyUserQuery);

        if (modifyUserQuery == true && modifyAddressQuery == true) {
            // statusMessageLabel.setText("Updated User Successfully");
        } else {
            //  statusMessageLabel.setText("Error With DB Query");
        }
    }

    public void passVendorInfo(String id, String cName, String fName, String lName, String phone, String em) throws SQLException {
        String street;
        String city;
        String zip;
        String state;

        String addrID = new VendorModel().getAddressIDByVendor(id);

        ResultSet rs = new AddressModel().getAddressByID(addrID);
        rs.next();
        street = rs.getString(1);
        city = rs.getString(2);
        zip = rs.getString(3);
        state = rs.getString(4);

        this.vendorID = id;
        this.vendorIDLabel.setText(id);
        this.companyName.setText(cName);
        this.address.setText(street);
        this.city.setText(city);
        this.zipCode.setText(zip);
        this.state.setText(state);
        this.firstName.setText(fName);
        this.lastName.setText(lName);
        this.phone.setText(phone);
        this.email.setText(em);
    }

    public void vendorMainScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/group7/Vendor/vendorMain.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /*****************************************************************************
     * passVendorInfoToContractForm
     *****************************************************************************/
    public void passVendorInfoToContractForm(String id, String cName) throws SQLException {

        VendorModel propertyNames = new VendorModel();

        ResultSet rs = property.getProperties();

        ObservableList<ObservableList> items = FXCollections.observableArrayList();
        ObservableList<String> propertyItems = FXCollections.observableArrayList();
        ObservableList<String> propertyItemsID = FXCollections.observableArrayList();

        try {
            while (rs.next()) {
                propertyItems.add(rs.getString(2));
            }
            chooseProperty.setItems(propertyItems);

        } catch(Exception e) {}
        this.vendorID = id;
        this.selectedVendor.setText(cName);
        chooseProperty.setValue(propertyItems.get(0));

//        tableViewContracts.getItems().removeAll(1);
//        System.out.println("In passVendorIDtoContractForm Item1 = " + tableViewContracts.getItems().removeAll(1));

    }

    /*****************************************************************************
     * loadTable
     *****************************************************************************/
    public void loadTable() {
        //Get the properties that are already assigned to the Vendor
        ResultSet propID = new VendorModel().getVendorsContracts(vendorID);

        try {
            // Clear table before reloading
            for ( int i = 0; i<tableViewContracts.getItems().size(); i++) {
                tableViewContracts.getItems().clear();
            }

            //populate the table with the properties that the vendor currently has
            while (propID.next()) {
                System.out.println("propID.getString(1) =" + propID.getString(1));

                ResultSet rsVM = new PropertyModel().getPropertyDataById(propID.getString(1));
                tableViewContracts.getColumns().clear();
                for (int i = 0; i < rsVM.getMetaData().getColumnCount(); i++)
                {
                    final int j = i;

                    TableColumn col = new TableColumn(rsVM.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>()
                    {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param)
                        {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    tableViewContracts.getColumns().addAll(col);
                }
                while (rsVM.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rsVM.getMetaData().getColumnCount(); i++) {
                        row.add(rsVM.getString(i));
                        System.out.println("In loadTable row.add, i = " + i + ",  " + rsVM.getString(i));
                    }
                    items.add(row);
                    System.out.println("In loadTable row = " + row);
                }
                tableViewContracts.setItems(items);
//                System.out.println("In items row = " + items);
            }
//            System.out.println("In items row After CLEAR = " + items);
        } catch(Exception e) {}
    }

    /*****************************************************************************
     * addPropertyToVendor
     *****************************************************************************/
    public void addPropertyToVendor(String vendorID, String propertyID) throws SQLException {

        System.out.println(" In addProperyToVendor propertyID = " + propertyID);
        boolean createUserQuery = new VendorModel().addVendorsContracts(vendorID, propertyID);
        loadTable();

    }

    /*****************************************************************************
     * saveContractButtonOnAction
     *****************************************************************************/
    public void saveContractButtonOnAction(ActionEvent actionEvent) throws SQLException {
        addPropertyToVendor(vendorID, property.getPropertyIdByName(chooseProperty.getValue().toString()));
    }

    /*****************************************************************************
     * deleteContractButtonOnAction
     *****************************************************************************/
    public void deleteContractButtonOnAction(ActionEvent actionEvent) throws SQLException {
        boolean createUserQuery = new VendorModel().deleteVendorsContracts(vendorID, property.getPropertyIdByName(chooseProperty.getValue().toString()));
        loadTable();
    }
}
