package com.group7.model;

import com.group7.DatabaseConnection;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationModel {
    private String id;
    private String propertyName;
    private String type;
    private String rate;
    private String addressID;
    private String ownerID;

    public ReservationModel() { //empty constructor

    }

    public ResultSet getReservationsByPropertyId(String propertyId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        ResultSet rs = null;
        String query = "SELECT r.* FROM reservations r LEFT JOIN properties p on r.propertyId = '" + propertyId + "'";

        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;

    }

    public ResultSet getProperties() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT p.id, p.name,p.type, p.rent_value,p.owner_id, a.street_address, a.city, a.state, a.zip_code FROM properties p LEFT JOIN address a on p.address_id = a.id";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public String getAddressIDByProperty(String propertyID) {
        this.id = propertyID;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT address_id FROM properties WHERE id = '" + propertyID + "'";
        try {
            ResultSet rs = connectDB.createStatement().executeQuery(query);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "null";

    }

    public boolean createProperty(String propertyName, String type, String rate, String addressID, String ownerID) { //returns a boolean value, adds a new user to the user_accounts
        this.propertyName = propertyName;
        this.type = type;
        this.rate = rate;
        this.addressID = addressID;
        this.ownerID = ownerID;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createPropertyQuery = "INSERT INTO properties (name,type,rent_value,address_id,owner_id) VALUES ('"+ this.propertyName +"','"+ this.type +"','"+ this.rate +"','"+ this.addressID +"','"+ this.ownerID +"')";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(createPropertyQuery); //execute the above query
            if (queryResult == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

    public boolean modifyProperty(String propertyID, String propertyName, String type, String rate, String ownerID) { //returns a boolean value, modifies user in the user_accounts
        this.id = propertyID;
        this.propertyName = propertyName;
        this.type = type;
        this.rate = rate;
        this.ownerID = ownerID;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createOwnerQuery = "UPDATE properties SET name = '" + this.propertyName + "', type = '" + this.type + "', rent_value = '" + this.rate + "', owner_id = '" + this.ownerID + "'";
        System.out.println(createOwnerQuery);


        try {
            int queryResult = connectDB.createStatement().executeUpdate(createOwnerQuery); //execute the above query
            if (queryResult == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

    public boolean deleteProperty(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addrID = getAddressIDByProperty(id);
        String deleteAddressQuery = "DELETE FROM address WHERE id = " + addrID;
        String deletePropertyQuery = "DELETE FROM properties WHERE id = " + id;

        System.out.println(deletePropertyQuery);
        System.out.println(deleteAddressQuery);

        int result2 = connectDB.createStatement().executeUpdate(deletePropertyQuery);
        int result = connectDB.createStatement().executeUpdate(deleteAddressQuery);
        if(result == 1 && result2 == 1)
            return true;
        return false;
    }


}
