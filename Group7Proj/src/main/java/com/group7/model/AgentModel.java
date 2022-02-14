package com.group7.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group7.DatabaseConnection;

public class AgentModel { //Will control getting and setting data to the SQL server
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public AgentModel() { //empty constructor

    }

    public ResultSet getUsers() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM agent_accounts";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getUserDataByID(String userID) { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM agent_accounts WHERE id = " + userID;
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean deleteUser(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String deleteUserQuery = "DELETE FROM agent_accounts WHERE id = " + id;

        System.out.println(deleteUserQuery);

        int result = connectDB.createStatement().executeUpdate(deleteUserQuery);
        if(result == 1)
            return true;
        return false;
    }

        public boolean createUser(String firstName, String lastName, String username, String password) { //returns a boolean value, adds a new user to the user_accounts
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "INSERT INTO agent_accounts (first_name,last_name,username,password) VALUES ('"+ this.firstName +"','"+ this.lastName +"','"+ this.username +"','"+ this.password +"')";
            System.out.println(createUserQuery);

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

    public boolean modifyUser(String userID, String firstName, String lastName, String username, String password) { //returns a boolean value, modifies user in the user_accounts
        this.id = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "UPDATE agent_accounts SET first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', username = '" + this.username + "', password = '" + this.password + "' WHERE id = '" + this.id + "'";
        System.out.println(createUserQuery);


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

    public boolean deleteUser() {
        return false;
    }


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {this.id = id; }
}