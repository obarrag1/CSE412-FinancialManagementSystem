package com.example.cse412financialmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class DatabaseConnector {
    public static Connection connect(){
        try {

            String url = "jdbc:postgresql://localhost:8888/your_database_name";
            String username = "your_username";
            String password = "your_password";

            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the PostgreSQL database successfully");
            return connection;
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error:");
            e.printStackTrace();
            return null;
        }
    }
    public static List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();

        try (Connection connection = connect()) {
            // Check if the connection is null
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return receipts; // Return an empty list or handle the error as needed
            }

            String sql = "SELECT pur_id, incoming, amount, date, subscr, account_id, vendor_id FROM receipt";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int purId = resultSet.getInt("pur_id");
                    boolean incoming = resultSet.getBoolean("incoming");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getDate("date");
                    boolean subscr = resultSet.getBoolean("subscr");
                    int accountId = resultSet.getInt("account_id");
                    int vendorId = resultSet.getInt("vendor_id");

                    Receipt receipt = new Receipt(purId, incoming, amount, date, subscr, vendorId);
                    receipts.add(receipt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return receipts;
    }


}
