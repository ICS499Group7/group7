package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerModel {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public OwnerModel() { //empty constructor

    }

    public ResultSet getOwners() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM owner_accounts";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public boolean createOwner(String firstName, String lastName, String username, String password) { //returns a boolean value, adds a new user to the user_accounts
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "INSERT INTO user_accounts (first_name,last_name,username,password) VALUES ('"+ this.firstName +"','"+ this.lastName +"','"+ this.username +"','"+ this.password +"')";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(createUserQuery); //execute the above query
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
