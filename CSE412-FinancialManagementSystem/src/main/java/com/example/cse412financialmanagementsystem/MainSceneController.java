package com.example.cse412financialmanagementsystem;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.cse412financialmanagementsystem.StartSceneController.user_id;
import static com.example.cse412financialmanagementsystem.StartSceneController.user_password;

public class MainSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableColumn<Receipt, Long> purIdColumn;
    @FXML
    private TableColumn<Receipt, Boolean> incomingColumn;

    @FXML
    private TableColumn<Receipt, Double> amountColumn;

    @FXML
    private TableColumn<Receipt, String> dateColumn;

    @FXML
    private TableColumn<Receipt, Boolean> subscrColumn;

    @FXML
    private TableColumn<Receipt, Long> accountIdColumn;

    @FXML
    private TableColumn<Receipt, Long> vendorIdColumn;

    @FXML
    private TableView<Receipt> receiptTable;

    @FXML
    private void initialize() throws IOException {
        purIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getPurId()).asObject());
        incomingColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isIncoming()));
        amountColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDate()));
        subscrColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isRecurring()));
        accountIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getAccountId()).asObject());
        vendorIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getVendorId()).asObject());

        // Call the DatabaseConnector to get the list of receipts
        List<Receipt> receipts = DatabaseConnector.getReceipts();
        List<Receipt> user_receipts = new ArrayList<Receipt>();

        if(receipts.size() != 0) {
            for (Receipt purchase : receipts) {

                //Look for matching receipts according to the account id
                if (purchase.getAccountId().equals(user_id)) {
                    user_receipts.add(purchase);
                }
            }

            if (user_receipts != null) {
                //Only print if there are existing receipts
                receiptTable.getItems().addAll(user_receipts);
            }
        }
    }

    //Access the user's account information
    public void switchToAccountInfo(ActionEvent event) throws IOException {

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("AccountInformation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Transfer the user to the add/edit receipt scene to enter new values
    public void addReceipt(ActionEvent event) throws IOException {

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("ModifyReceipt.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Save the selected receipts information and change the scene to allow the user to edit
    public void editReceipt(ActionEvent event) throws IOException {

        //Save the information of the selected receipt so it is carried over to the next scene
        Receipt selectedReceipt = receiptTable.getSelectionModel().getSelectedItem();

        // Change the scene and populate fields in the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyReceipt.fxml"));
        Parent root = loader.load();

        // Access the controller of the next scene
        ModifyReceiptController modifyReceiptController = loader.getController();

        // Populate fields with the selected receipt information
        modifyReceiptController.populateFields(selectedReceipt);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Remove the selected receipt from the lists
    public void deleteReceipt(ActionEvent event) throws SQLException {
        //Delete the receipt from the database
        Receipt selectedReceipt = receiptTable.getSelectionModel().getSelectedItem(); // gets the selected receipt from the table
        if(selectedReceipt != null) {
            DatabaseConnector.deleteReceipt(selectedReceipt); //Removing from database
            receiptTable.getItems().remove(selectedReceipt); // remove from the table view
        }else{
            System.out.println("Receipt is null");
        }
    }

    //Return the user to the login scene and remove the saved credentials
    public void logOutUser(ActionEvent event) throws IOException {

        //Remove the user's current information
        user_id = null;
        user_password = null;

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
