package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyModel {
    public PropertyModel() { //empty constructor

    }

    public ResultSet getProperties() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM properties";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }


}
