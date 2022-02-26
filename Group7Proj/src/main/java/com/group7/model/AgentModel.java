package com.group7.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.group7.DatabaseConnection;

public class AgentModel { //Will control getting and setting data to the SQL server
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public AgentModel() { //empty constructor

    }

    public ResultSet getAgents() { //Returns a Resultset list of all agents in user_accounts
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

    public ResultSet getAgentDataByID(String agentID) { //Returns a Resultset list of all agents in Agents_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM agent_accounts WHERE id = " + agentID;
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean deleteAgent(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String deleteAgentQuery = "DELETE FROM agent_accounts WHERE id = " + id;

        System.out.println(deleteAgentQuery);

        int result = connectDB.createStatement().executeUpdate(deleteAgentQuery);
        if(result == 1)
            return true;
        return false;
    }

    //returns a boolean value, adds a new agent to the agents_accounts
    public int addAgent(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addAgentQuery = "INSERT INTO agent_accounts (first_name,last_name,username,password) VALUES ('" + this.firstName + "','" + this.lastName + "','" + this.username + "','" + this.password + "')";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(addAgentQuery); //execute the above query
        } catch( SQLIntegrityConstraintViolationException constraintViolationException) {
            return 2; //The username must be unique
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            return 0; //Error With DB Query
        }
        return 1;
}

    public int modifyAgent(String id, String firstName, String lastName, String username, String password) { //returns a boolean value, modifies agent in the agent_accounts
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String modifyAgentQuery = "UPDATE agent_accounts SET first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', username = '" + this.username + "', password = '" + this.password + "' WHERE id = '" + this.id + "'";

        try {
            int queryResult = connectDB.createStatement().executeUpdate(modifyAgentQuery); //execute the above query
        } catch( SQLIntegrityConstraintViolationException constraintViolationException) {
            return 2; //The username must be unique
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            return 0; //Error With DB Query
        }
        return 1;
    }

}
