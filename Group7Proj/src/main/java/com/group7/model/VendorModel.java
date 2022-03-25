package com.group7.model;

import com.group7.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendorModel {

    private String id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String addressID;
    private String phone;
    private String email;

    public VendorModel() { //empty constructor

    }

    public ResultSet getVendors() { //Returns a Resultset list of all users in user_accounts
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        ResultSet rs = null;
        String query = "SELECT v.id,v.company_name,v.first_name,v.last_name,v.phone,v.email, a.street_address, a.city, a.state, a.zip_code FROM vendors v LEFT JOIN address a on v.address_id = a.id";
        try {
            rs = connectDB.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    /*
        public ResultSet getVendorsDataByID(String vendorID) { //Returns a Resultset list of all agents in Agents_accounts
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
    public String getAddressIDByVendor(String vendorID) {
        this.id = vendorID;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT address_id FROM vendors WHERE id = '" + vendorID + "'";
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

    public boolean createVendor(String companyName, String addressID, String firstName, String lastName, String phone, String email) { //returns a boolean value, adds a new user to the user_accounts
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressID = addressID;
        this.phone = phone;
        this.email = email;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createUserQuery = "INSERT INTO vendors (company_name,first_name,last_name,address_id,phone,email) VALUES ('"+ this.companyName +"','"+ this.firstName +"','"+ this.lastName +"','"+ this.addressID +"','"+ this.phone +"','"+ this.email +"')";

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

    public boolean modifyVendor(String vendorID, String companyName, String firstName, String lastName, String phone, String email) { //returns a boolean value, modifies user in the user_accounts
        this.id = vendorID;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String createVendorQuery = "UPDATE vendors SET company_name = '" + this.companyName + "', first_name = '" + this.firstName + "', last_name = '" + this.lastName + "', phone = '" + this.phone + "', email = '" + this.email + "' WHERE id = '" + this.id + "'";
        System.out.println(createVendorQuery);


        try {
            int queryResult = connectDB.createStatement().executeUpdate(createVendorQuery); //execute the above query
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

    public boolean deleteVendor(String id) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addrID = getAddressIDByVendor(id);
        String deleteAddressQuery = "DELETE FROM address WHERE id = " + addrID;
        String deleteVendorQuery = "DELETE FROM vendors WHERE id = " + id;

        System.out.println(deleteVendorQuery);
        System.out.println(deleteAddressQuery);

        int result2 = connectDB.createStatement().executeUpdate(deleteVendorQuery);
        int result = connectDB.createStatement().executeUpdate(deleteAddressQuery);
        if(result == 1 && result2 == 1)
            return true;
        return false;
    }


    //Vendor_Contract Code
    /*****************************************************************************
     * getVendorsContracts
     *****************************************************************************/
    public ResultSet getVendorsContracts(String vendorID) {
        this.id = vendorID;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT propertyId FROM vendor_contracts WHERE vendorId = '" + vendorID + "'";
        System.out.println( "getVendorsContracts query = "  + query);



        try {
            ResultSet rs = connectDB.createStatement().executeQuery(query);
            return rs;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*****************************************************************************
     * deleteVendorsContracts
     *****************************************************************************/
    public boolean deleteVendorsContracts(String vendorID, String propertyID) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String deleteVendorContractQuery = "";
        if(vendorID.compareTo("") != 0 && propertyID.compareTo("") != 0 ) {
            deleteVendorContractQuery = "DELETE FROM vendor_contracts WHERE vendorId = " + vendorID + " AND propertyId = " + propertyID;
        } else if(vendorID.compareTo("") != 0 && propertyID.compareTo("") == 0 ) {
            deleteVendorContractQuery = "DELETE FROM vendor_contracts WHERE propertyId = " + propertyID;
        } else if(vendorID.compareTo("") == 0 && propertyID.compareTo("") != 0 ) {
            deleteVendorContractQuery = "DELETE FROM vendor_contracts WHERE vendorId = " + vendorID;
        }

        System.out.println(deleteVendorContractQuery);

        int result = connectDB.createStatement().executeUpdate(deleteVendorContractQuery);
        if(result == 1)
            return true;
        return false;
    }


    /*****************************************************************************
    * addVendorsContracts
    *****************************************************************************/
    public boolean addVendorsContracts(String vendorID, String propertyID) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String queryCheckExists = "SELECT id FROM vendor_contracts WHERE vendorId = " + vendorID + " AND propertyId = " + propertyID;
        ResultSet rs = connectDB.createStatement().executeQuery(queryCheckExists);

        System.out.println( "getVendorsContracts query = "  + queryCheckExists);
        String ID = "";
        if (rs.next()) {
            ID = rs.getString(1);
        }
        System.out.println( "getVendorsContracts results = "  + ID);
        if(ID.compareTo("") == 0 )
        {
            String addVendorContractQuery = "INSERT INTO vendor_contracts (vendorId,propertyId) VALUES ('"+ vendorID +"','"+ propertyID +"')";
            System.out.println(addVendorContractQuery);

            int result = connectDB.createStatement().executeUpdate(addVendorContractQuery);
            if(result == 1)
                return true;
            return false;
        }
        return false;
    }


}
