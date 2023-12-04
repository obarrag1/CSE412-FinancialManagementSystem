package com.example.cse412financialmanagementsystem;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class DatabaseConnector {

    private static final String URL = "jdbc:postgresql://localhost:8888/FinancialManagement";
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

    // add a new receipt to the database
    public static void addReceipt(Receipt receipt) {
        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return;
            }

            // Insert receipt into the database
            String sql = "INSERT INTO receipt (pur_id, incoming, amount, date, subscr, account_id, vendor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, receipt.getPurId());
                statement.setBoolean(2, receipt.isIncoming());
                statement.setDouble(3, receipt.getAmount());
                statement.setDate(4, new java.sql.Date(receipt.getDate().getTime()));
                statement.setBoolean(5, receipt.isRecurring());
                statement.setLong(6, receipt.getAccountId());
                statement.setLong(7, receipt.getVendorId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e);
        }
    }

    //Check if purchase ID is already in the DB
    public static boolean isPurchaseIdExists(long purchaseId) {
        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return false;
            }

            String sql = "SELECT COUNT(*) FROM receipt WHERE pur_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, purchaseId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e);
            return false;
        }
    }

    // Check if accountID exists or not
    public static boolean isAccountIdExists(long accountId) {
        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return false;
            }

            String sql = "SELECT COUNT(*) FROM account WHERE account_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, accountId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return true;
                    } else {
                        // Handle the case where there is no result (optional, depending on your logic)
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e);
            return false;
        }
    }

    // Check if Vendor ID exists or not
    public static boolean isVendorIdExists(long vendorId) {
        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return false;
            }

            String sql = "SELECT COUNT(*) FROM vendor WHERE vendor_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, vendorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e);
            return false;
        }
    }
}


