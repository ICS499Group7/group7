package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.*;

public class AddressModel {
    private String id;
    private String streetAddress;
    private String city;
    private String zipCode;
    private String state;

    public AddressModel() {

    }
/*
    public ResultSet getAddress() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT a.street_address, a.city, a.zip_code, a.state FROM address a";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
*/
    public ResultSet getAddressByID(String id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT a.street_address, a.city, a.zip_code, a.state FROM address a WHERE a.id =" + id;
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }



    public String createAddress(String addr, String city, String zip, String state) {
        this.streetAddress = addr;
        this.city = city;
        this.zipCode = zip;
        this.state = state;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addAddressQuery = "INSERT INTO address (street_address,city,zip_code,state) VALUES ('"+ this.streetAddress +"','"+ this.city +"','"+ this.zipCode +"','"+ this.state +"')";


        try {
            //int queryResult = connectDB.createStatement().executeUpdate(addAddressQuery); //execute the above query

            PreparedStatement ps = connectDB.prepareStatement(addAddressQuery, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                return id = rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return "null";
    }

    public boolean modifyAddress(String addrID, String addr, String city, String zip, String state) {
        this.id = addrID;
        this.streetAddress = addr;
        this.city = city;
        this.zipCode = zip;
        this.state = state;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String modifyAddressQuery = "UPDATE address SET street_address = '" + this.streetAddress + "', city = '" + this.city + "', state = '" + this.state + "',zip_code = '" + this.zipCode + "' WHERE id = '" + this.id + "'";


        try {
            int queryResult = connectDB.createStatement().executeUpdate(modifyAddressQuery); //execute the above query
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

    public boolean deleteAddress() {
        return false;
    }



}
