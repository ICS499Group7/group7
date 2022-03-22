package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestModel {

    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String dateOfBirth;

    public GuestModel() { //empty constructor

    }

    public ResultSet getGuests() { //Returns a Resultset list of all agents in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM guests";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public String getGuestIdByName(String fName, String lName) { //Returns a String id
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT g.id FROM guests g WHERE g.first_name = '" + fName + "' AND g.last_name = '" + lName + "'";
        try {
            rs = connectDB.createStatement().executeQuery(query);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "null";
    }

    public ResultSet getGuestDataByID(String guestID) { //Returns a Resultset list of all agents in Agents_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM guests WHERE id = " + guestID;
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean deleteGuest(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String deleteGuestQuery = "DELETE FROM guests WHERE id = " + id;

        System.out.println(deleteGuestQuery);

        int result = connectDB.createStatement().executeUpdate(deleteGuestQuery);
        if(result == 1)
            return true;
        return false;
    }

    //returns a boolean value, adds a new agent to the agents_accounts
    public boolean addGuest(String firstName, String lastName, String phone, String email, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dob;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addGuestQuery = "INSERT INTO guests (first_name,last_name,phone,email,dateOfBirth) VALUES ('"+ this.firstName +"','"+ this.lastName +"','"+ this.phone +"','"+ this.email +"','"+ this.dateOfBirth +"')";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(addGuestQuery); //execute the above query
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

    public boolean modifyGuest(String id, String firstName, String lastName, String phone, String email, String dob) { //returns a boolean value, modifies agent in the agent_accounts
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dob;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String modifyGuestQuery = "UPDATE guests SET first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', phone = '" + this.phone + "', email = '" + this.email + "', dateOfBirth = '" + this.dateOfBirth + "' WHERE id = '" + this.id + "'";
        System.out.println(modifyGuestQuery);
        try {
            int queryResult = connectDB.createStatement().executeUpdate(modifyGuestQuery); //execute the above query
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

}
