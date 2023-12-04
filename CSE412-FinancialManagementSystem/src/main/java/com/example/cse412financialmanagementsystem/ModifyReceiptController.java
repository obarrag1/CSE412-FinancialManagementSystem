package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyReceiptController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField purchaseIdTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField recurringTextField;

    @FXML
    private TextField incomingTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField accountIdTextField;

    @FXML
    private TextField vendorIdTextField;

    //User does not want to add or edit anymore so return them to previous scene
    public void cancelSubmission(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void submitReceipt(ActionEvent event) throws IOException {
        //Check every text box to see if it is empty
        if (purchaseIdTextField.getText().trim().isEmpty() || amountTextField.getText().trim().isEmpty() || recurringTextField.getText().trim().isEmpty() ||
                incomingTextField.getText().trim().isEmpty() || dateTextField.getText().trim().isEmpty() || accountIdTextField.getText().trim().isEmpty() ||
                vendorIdTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("All boxes must be filled out");
            alert.show();
            return;
        }
        //Check every text box to guarantee appropriate values
        long purchaseId;
        String purId = purchaseIdTextField.getText().trim();
        try{
            purchaseId = Long.parseLong(purId);
            if(purId.length() != 8){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Purchase ID must be 8 digits long");
                alert.show();
                return;
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Purchase ID must be an integer");
            alert.show();
            return;
        }
        double amount;
        try{
            amount = Double.parseDouble(amountTextField.getText().trim());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Amount must be a double");
            alert.show();
            return;
        }
        boolean recurring;
        try{
            recurring = Boolean.parseBoolean(recurringTextField.getText().trim());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Recurring must be true or false");
            alert.show();
            return;
        }
        boolean incoming;
        try{
            incoming = Boolean.parseBoolean(incomingTextField.getText().trim());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Incoming must be true or false");
            alert.show();
            return;
        }
        Date date;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(dateTextField.getText().trim());
        } catch (ParseException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Invalid Date Format. Ex: yyyy-MM-dd");
            alert.show();
            return;
        }
        long accountId;
        String accountIdLength = accountIdTextField.getText().trim();
        try{
            accountId = Long.parseLong(accountIdTextField.getText().trim());
            if(accountIdLength.length() != 10){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Account ID must be 10 digits long");
                alert.show();
                return;
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Account ID must be an integer");
            alert.show();
            return;
        }
        long vendorId;
        String vendorIDLength = vendorIdTextField.getText().trim();
        try{
            vendorId = Long.parseLong(vendorIdTextField.getText().trim());
            if(vendorIDLength.length() != 7){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Vendor ID must be 7 digits long");
                alert.show();
                return;
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vendor ID must be an integer");
            alert.show();
            return;
        }

        //Check for existing purchasing id before adding
        if(DatabaseConnector.isPurchaseIdExists(purchaseId)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Purchase ID already exists in the Database");
            alert.show();
            return;
        }
        //Check for valid account in the database before adding
        if(!DatabaseConnector.isAccountIdExists(accountId)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Account ID does not exist in the Database");
            alert.show();
            return;
        }
        //Check for valid vendorID in database before adding
        if(!DatabaseConnector.isVendorIdExists(vendorId)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Vendor ID does not exist in the Database");
            alert.show();
            return;
        }

        //Add the receipt to the database
        Receipt receipt = new Receipt(purchaseId, incoming, amount, date, recurring, accountId, vendorId);
        DatabaseConnector.addReceipt(receipt);

        // Alert of success
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Receipt successfully added");
        alert.show();

        //Return the user to the main scene
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //Return the user to the log in screen and remove the saved credentials
    public void logOutUser(ActionEvent event) throws IOException {

        //Remove the current user's information

        //Return to the log in scene
        root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
