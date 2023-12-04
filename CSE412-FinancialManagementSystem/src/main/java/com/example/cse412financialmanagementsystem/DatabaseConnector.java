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

    private static final String URL = "jdbc:postgresql://localhost:8888/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    // Connect to the db
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e);
            return null;
        }
    }

    // get the receipt table info
    public static List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();

        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return receipts;
            }

            String sql = "SELECT pur_id, incoming, amount, date, subscr, account_id, vendor_id FROM receipt";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Extract data and create a Receipt object
                    Receipt receipt = extractReceiptFromResultSet(resultSet);
                    receipts.add(receipt);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e);
        }

        return receipts;
    }

    // extract receipt info
    private static Receipt extractReceiptFromResultSet(ResultSet resultSet) throws SQLException {
        Long purId = resultSet.getLong("pur_id");
        boolean incoming = resultSet.getBoolean("incoming");
        double amount = resultSet.getDouble("amount");
        Date date = resultSet.getDate("date");
        boolean subscr = resultSet.getBoolean("subscr");
        Long accountId = resultSet.getLong("account_id");
        Long vendorId = resultSet.getLong("vendor_id");

        return new Receipt(purId, incoming, amount, date, subscr, accountId, vendorId);
    }

    // delete the receipt from the db
    public static void deleteReceipt(Receipt receipt) throws SQLException {
        try(Connection connection = connect()){
            if (connection == null){
                System.err.println("Connection is null. Check your database connection.");
                return;
            }
            // delete payment method since it is FK
            String payMethodSql = "DELETE FROM payment_method WHERE pur_id = " + receipt.getPurId();
            try (PreparedStatement statement = connection.prepareStatement(payMethodSql)){
                statement.executeUpdate();
            }
            // delete receipt
            String sql = "DELETE FROM receipt WHERE pur_id = " + receipt.getPurId();
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                statement.executeUpdate();
            }
        }catch (SQLException e){
            System.err.println("Error executing SQL query: " + e);
        }
    }
}