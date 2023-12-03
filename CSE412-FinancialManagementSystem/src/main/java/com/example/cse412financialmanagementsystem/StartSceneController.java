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
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.cse412financialmanagementsystem.DatabaseConnector.connect;

public class StartSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static Long user_id;
    public static String user_password;
    public static Double balance;

    //GUI components for user login
    @FXML
    private TextField existing_textID;
    @FXML
    private PasswordField existing_textPassword;
    @FXML
    private Label errorMessage;

    //GUI components for new user
    @FXML
    private TextField name_text;
    @FXML
    private TextField newUser_id;
    @FXML
    private PasswordField password_1;
    @FXML
    private PasswordField password_2;
    @FXML
    private TextField zipcode;

    //Validate the user's credentials with the information in the database
    public void checkExistingUser(ActionEvent event) throws IOException {

        //The status of the user login (default)
        String status = "The account does not exist!";

        //Check that the ID is in the correct format
        try {
            //Save the user's credentials to keep track during entire runtime
            user_id = Long.parseLong(existing_textID.getText());
        } catch (NumberFormatException nfe){ user_id = (long) -1;}

        if(user_id.equals(-1)){status = "ID format is incorrect! (10 digits only) ";}

        //Get the password from the text box
        user_password = existing_textPassword.getText();

        //Gather the existing users
        List<Account> checkUsers = getAccounts();

        //Check every account for matching credentials
        for(Account User : checkUsers){

            //Update the status message if conditions are satisfied
            if(User.getAccount_ID().equals(user_id) && User.getPassword().equals(user_password)){
                status = "Success!";
                balance = User.getBalance();
            } else if (User.getAccount_ID().equals(user_id) && !User.getPassword().equals(user_password)) {
                status = "Valid username but incorrect password!";
            }
        }

        //The user has logged in successfully then switch the scene
        if(status == "Success!"){
            //Upon successful login, switch to the main scene
            switchToMainScene(event);
        }
        //Print why the user could  not log in
        else{ errorMessage.setText(status);};
    }

    //Check that the user's input is valid before creating their account
    public void accountCreation(ActionEvent event) throws IOException {

        String status = "Success!";

        //Enter the user's credentials into the database
        String user_name = name_text.getText();
        //------------------------USER ID-------------------------------------
        try {
            //Save the user's credentials to keep track during entire runtime
            user_id = Long.parseLong(newUser_id.getText());
        } catch (NumberFormatException nfe) {
            user_id = (long) -1;
        }

        //Gather the existing users
        List<Account> checkMatchingID = getAccounts();

        //Check every account for matching credentials
        for(Account User : checkMatchingID){

            //Update the status message if there is a matching ID
            if(User.getAccount_ID().equals(user_id)){
                status = "There is an existing user with that ID!";}
        }

        if (user_id.equals(-1)) { status = "That ID number is invalid! (10 digits only)"; }

        //--------------------------PASSWORD----------------------------------
        String password = password_1.getText();

        //Check that the passwords are matching
        if (!password.equals(password_2.getText())) {
            status = "The passwords do not match!";
        } else {
            user_password = password;
        }

        //---------------------ZIP--------------------------------------------
        int zip = Integer.parseInt(zipcode.getText());

        //Save the current user's information

        //-------------------------------------------------------------------
        if (status.equals("Success!")) {

            //Initialize the user's balance
            balance = 0.0;
            try (Connection connection = connect()) {
                if (connection == null) {
                    System.err.println("Connection is null. Check your database connection.");
                }
                String sql = "INSERT INTO account (account_id, balance, name, zip, password) VALUES ('" + user_id + "', '" + 0.0 + "', '" + user_name + "', '" + zip + "', '" + user_password +"')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                //Upon successful account creation, switch to the main scene
                switchToMainScene(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{ errorMessage.setText(status); }
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