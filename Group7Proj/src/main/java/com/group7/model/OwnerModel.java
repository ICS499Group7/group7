package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerModel {
    private String id;
    private String companyName;
    private String address;
    private String zipcode;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String officePhone;
    private String email;

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

    public boolean deleteOwner(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String deleteOwnerQuery = "DELETE FROM owner_accounts WHERE id = " + id;

        System.out.println(deleteOwnerQuery);

        int result = connectDB.createStatement().executeUpdate(deleteOwnerQuery);
        if(result == 1)
            return true;
        return false;
    }

    public boolean createOwner(String companyName, String address, String zipcode, String firstName, String lastName, String mobilePhone, String officePhone, String email) { //returns a boolean value, adds a new user to the user_accounts
        this.companyName = companyName;
        this.address = address;
        this.zipcode = zipcode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.officePhone = officePhone;
        this.email = email;


        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "INSERT INTO owner_accounts (company_name,address,zip_code,first_name,last_name,mobile_phone,office_phone,email) VALUES ('"+ this.companyName +"','"+ this.address +"','"+ this.zipcode +"','"+ this.firstName +"','"+ this.lastName +"','"+ this.mobilePhone +"','"+ this.officePhone +"','"+ this.email +"')";

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

    public boolean modifyOwner(String ownerID, String companyName, String address, String zipcode, String firstName, String lastName, String mobilePhone, String officePhone, String email) { //returns a boolean value, modifies user in the user_accounts
        this.id = ownerID;
        this.companyName = companyName;
        this.address = address;
        this.zipcode = zipcode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.officePhone = officePhone;
        this.email = email;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createOwnerQuery = "UPDATE owner_accounts SET company_name = '" + this.companyName + "', address = '" + this.address + "', zip_code = '" + this.zipcode + "',first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', mobile_phone = '" + this.mobilePhone + "', office_phone = '" + this.officePhone + "', email = '" + this.email + "' WHERE id = '" + this.id + "'";
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

}
