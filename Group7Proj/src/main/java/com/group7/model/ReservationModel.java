package com.group7.model;

import com.group7.DatabaseConnection;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationModel {
    private String id;
    private String propertyId;
    private String guestId;
    private String agentId;
    private String startDate;
    private String endDate;
    private String bookedDate;
    private String totalValue;

    public ReservationModel() { //empty constructor

    }

    public ResultSet getReservationsByPropertyName(String propertyId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        ResultSet rs = null;
        String query = "SELECT r.* FROM reservations r WHERE r.propertyId = (SELECT p.id FROM properties p WHERE p.name = '" + propertyId + "')";

        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;

    }

    public ResultSet getReservations() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;

        String query = "SELECT r.id, g.first_name, g.last_name, p.name, a.street_address, r.startDate, r.endDate, r.totalValue FROM reservations r LEFT JOIN properties p on p.id = r.propertyId LEFT JOIN address a on p.address_id = a.id LEFT JOIN guests g on g.id = r.guestId";
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

    public ResultSet getReservationDataById(String id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;

        String query = "SELECT r.guestId, r.propertyId, r.agentId, r.startDate, r.endDate, r.bookedDate, r.totalValue FROM reservations r WHERE r.id = '" + id + "'";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }



    public boolean createReservation(String propertyId, String guestId, String agentId, String startDate, String endDate, String totalValue, String currentDate) { //returns a boolean value, adds a new user to the user_accounts
        this.propertyId = propertyId;
        this.guestId = guestId;
        this.agentId = agentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalValue = totalValue;
        this.bookedDate = currentDate;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createQuery = "INSERT INTO reservations (propertyId, guestId, agentId, startDate, endDate, totalValue, bookedDate) VALUES ('"+ this.propertyId +"','"+ this.guestId +"','"+ this.agentId +"','"+ this.startDate +"','"+ this.endDate +"','"+ this.totalValue +"','"+ this.bookedDate +"')";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(createQuery); //execute the above query
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

    public boolean modifyReservation(String id, String propertyId, String guestId, String startDate, String endDate, String totalValue) { //returns a boolean value, modifies user in the user_accounts
        this.id = id;
        this.propertyId = propertyId;
        this.guestId = guestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalValue = totalValue;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createReservationQuery = "UPDATE reservations SET propertyId = '" + this.propertyId + "', guestId = '" + this.guestId + "', startDate = '" + this.startDate + "', endDate = '" + this.endDate + "',totalValue = '" + this.totalValue + "' WHERE id = '" + this.id + "'";
        System.out.println(createReservationQuery);


        try {
            int queryResult = connectDB.createStatement().executeUpdate(createReservationQuery); //execute the above query
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

    public boolean deleteReservation(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addrID = getAddressIDByProperty(id);
        String deleteAddressQuery = "DELETE FROM address WHERE id = " + addrID;
        String deletePropertyQuery = "DELETE FROM reservations WHERE id = " + id;

        System.out.println(deletePropertyQuery);
        System.out.println(deleteAddressQuery);

        int result2 = connectDB.createStatement().executeUpdate(deletePropertyQuery);
        int result = connectDB.createStatement().executeUpdate(deleteAddressQuery);
        if(result == 1 && result2 == 1)
            return true;
        return false;
    }


}
