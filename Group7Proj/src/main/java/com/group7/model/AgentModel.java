package com.group7.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Locale;

import com.group7.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import com.group7.controllers.AgentManageController;


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

    public static String getAgentIdByName(String name) { //Returns a String id
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT a.id FROM agent_accounts a WHERE a.username = '" + name + "'";
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

    public static String getAgentNameByUsername(String uName) { //Returns a String id
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT a.first_name FROM agent_accounts a WHERE a.username = '" + uName + "'";
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

    public static ResultSet getAgentDataByUsername(String uName) { //Returns a Resultset list of all agents in Agents_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM agent_accounts WHERE username = '" + uName + "'";
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
        this.username = username.toLowerCase(Locale.ROOT);
        this.password = password;
        String hash = hashPassword(password);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addAgentQuery = "INSERT INTO agent_accounts (first_name,last_name,username,password) VALUES ('" + this.firstName + "','" + this.lastName + "','" + this.username + "','" + hash + "')";

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
        this.username = username.toLowerCase(Locale.ROOT);
        this.password = password;
        String hash = hashPassword(password);
        AgentManageController amc = new AgentManageController();
        if (amc.checkPassword(this.password)) {
        }
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String modifyAgentQuery = "UPDATE agent_accounts SET first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', username = '" + this.username + "', password = '" + hash + "' WHERE id = '" + this.id + "'";

        try {
            connectDB.createStatement().executeUpdate(modifyAgentQuery); //execute the above query
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

    /**
     BCrypt function to convert password into a 240bit hash w/ salt
     **/
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

}


