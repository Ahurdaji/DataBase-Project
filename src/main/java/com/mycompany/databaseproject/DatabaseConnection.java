
package com.mycompany.databaseproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hadalkharouf
 */
public class DatabaseConnection {
    

    public static Connection getConnection() {
        Connection conn = null;

        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=FixedAssestHirePurchaseDB;encrypt=false;";
            String user = "LoginAsma";
            String password = "12345";


            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected Successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
}
}