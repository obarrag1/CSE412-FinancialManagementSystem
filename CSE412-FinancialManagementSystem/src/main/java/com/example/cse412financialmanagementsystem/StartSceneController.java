package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.cse412financialmanagementsystem.DatabaseConnector.connect;

public class StartSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static Long user_id;
    private static String user_password;

    @FXML
    private TextField existing_textID;
    @FXML
    private PasswordField existing_textPassword;
    @FXML
    private Label errorMessage;

    //Validate the user's credentials with the information in the database
    public void checkExistingUser(ActionEvent event) throws IOException {

        //The status of the user login (default)
        String status = "The account does not exist!";

        try {
            //Save the user's credentials to keep track during entire runtime
            user_id = Long.parseLong(existing_textID.getText());
        } catch (NumberFormatException nfe){ user_id = (long) -1;}

        if(user_id == (-1)){status = "Account ID is incorrect!";}

        user_password = existing_textPassword.getText();

        //Gather the existing users
        List<Account> checkUsers = getAccounts();

        //Check every account for matching credentials
        for(Account User : checkUsers){

            System.err.println(User.getAccount_ID());
            //Update the status message if conditions are satisfied
            if(User.getAccount_ID().equals(user_id) && User.getPassword().equals(user_password)){
                status = "Success!";
            } else if (User.getAccount_ID().equals(user_id) && User.getPassword().equals(user_password)) {
                status = "Valid username but incorrect password!";
            }
        }

        //The user has logged in successfully then switch the scene
        if(status == "Success!"){
            //Upon successful login, switch to the main scene
            switchToMainScene(event);
        }
        else{ errorMessage.setText(status);};
    }

    //Check that the user's input is valid before creating their account
    public void accountCreation(ActionEvent event) throws IOException {

        //Enter the user's credentials into the database

        //Save the current user's information

        //Upon successful account creation, switch to the main scene
        switchToMainScene(event);
    }

    //Switch to the main scene where user can perform actions or select to view account info
    public void switchToMainScene(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return accounts;
            }

            String sql = "SELECT account_id, balance, name, zip, password FROM account";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Extract data and create an Account object
                    Account curr_account = extractAccountInfo(resultSet);
                    accounts.add(curr_account);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }
        return accounts;
    }

    //Extract the account information within the table
    private static Account extractAccountInfo(ResultSet resultSet) throws SQLException {

        Long account_id = (resultSet.getLong("account_id"));
        double balance = resultSet.getDouble("balance");
        String name = resultSet.getString(("name"));
        int zip = resultSet.getInt("zip");
        String password = resultSet.getString("password");

        return new Account(account_id, balance, name, zip, password);
    }
}