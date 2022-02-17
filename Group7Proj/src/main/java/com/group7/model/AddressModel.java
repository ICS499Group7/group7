package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.*;

public class AddressModel {
    private String id;
    private String streetAddress;
    private String city;
    private String zipCode;
    private String state;
    private String Country;

    public AddressModel() {

    }

    public ResultSet getAddress() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT * FROM address";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getAddressByID() {
        return null;
    }

    public String createAddress(String addr, String zip) {
        this.streetAddress = addr;
        this.zipCode = zip;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addAddressQuery = "INSERT INTO address (street_address,city,state,zip_code,country) VALUES ('"+ this.streetAddress +"','testCity','testState','"+ this.zipCode +"','testCountry')";
        String getIDQuery = "SELECT LAST_INSERT_ID()";



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

    public boolean modifyAddress() {
        return false;
    }

    public boolean deleteAddress() {
        return false;
    }



}
