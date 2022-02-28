package com.group7.model;

import com.group7.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginModel {

    public static String username;
    public static boolean admin;
    private static String password;

    public LoginModel(String user, String pass) {
        username = user;
        password = pass;
        admin = user.substring(0).equals("!");
    }

    public boolean verifyLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM agent_accounts WHERE username = '" + username + "' AND password ='" + password + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
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
