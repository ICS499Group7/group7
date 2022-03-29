package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerModel {
    private String id;
    private String companyName;
    private String addressID;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public OwnerModel() { //empty constructor

    }

    public ResultSet getOwners() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT o.id, o.company_name,o.first_name,o.last_name,o.phone,o.email, a.street_address, a.city, a.state, a.zip_code FROM owner_accounts o LEFT JOIN address a on o.address_id = a.id";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
/*
    public ResultSet getOwnerDataByID(String ownerID) { //Returns a Resultset list of all agents in Agents_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM owner_accounts WHERE id = " + ownerID;
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
*/
    public String getAddressIDByOwner(String ownerID) {
        this.id = ownerID;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT address_id FROM owner_accounts WHERE id = '" + ownerID + "'";
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

    public ResultSet getOwnerDataById(String id) { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT o.company_name,o.first_name, o.last_name,o.phone,o.email FROM owner_accounts o WHERE o.id = '" + id + "'";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public boolean createOwner(String companyName, String addressID, String firstName, String lastName, String phone, String email) { //returns a boolean value, adds a new user to the user_accounts
        this.companyName = companyName;
        this.addressID = addressID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "INSERT INTO owner_accounts (company_name,address_id,first_name,last_name,phone,email) VALUES ('"+ this.companyName +"','"+ this.addressID +"','"+ this.firstName +"','"+ this.lastName +"','"+ this.phone +"','"+ this.email +"')";

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

    public boolean modifyOwner(String ownerID, String companyName, String firstName, String lastName, String phone, String email) { //returns a boolean value, modifies user in the user_accounts
        this.id = ownerID;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createOwnerQuery = "UPDATE owner_accounts SET company_name = '" + this.companyName + "', first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', phone = '" + this.phone + "', email = '" + this.email + "' WHERE id = '" + this.id + "'";
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

    public boolean deleteOwner(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addrID = getAddressIDByOwner(id);
        String deleteAddressQuery = "DELETE FROM address WHERE id = " + addrID;
        String deleteOwnerQuery = "DELETE FROM owner_accounts WHERE id = " + id;

        System.out.println(deleteOwnerQuery);
        System.out.println(deleteAddressQuery);

        int result2 = connectDB.createStatement().executeUpdate(deleteOwnerQuery);
        int result = connectDB.createStatement().executeUpdate(deleteAddressQuery);
        if(result == 1 && result2 == 1)
            return true;
        return false;
    }

}
