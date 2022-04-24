package com.group7.model;

import com.group7.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

public class LoginModel {

    public static String usernameFromLoginForm;
    public static boolean admin;
    public static String passwordFromLoginForm;

    public LoginModel(String user, String pass) {
        usernameFromLoginForm = user;
        passwordFromLoginForm = pass;
        admin = user.substring(0).equals("!");
    }

    /**
     BCrypt function to verify password from login form to the hash stored in the database
     **/
    public static boolean verifyHash(String password, String hashed) {
        if (BCrypt.checkpw(password, hashed)) {
            System.out.println("It matches: password = " + password + " hash = " + hashed);
            return true;
        }
        System.out.println("It does not match");
        return false;
    }

    public boolean verifyLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String dbQuery = "SELECT * FROM agent_accounts WHERE username = '" + usernameFromLoginForm + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(dbQuery);

            while(queryResult.next()) {
                if (verifyHash(passwordFromLoginForm, queryResult.getString("password")))  {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

}