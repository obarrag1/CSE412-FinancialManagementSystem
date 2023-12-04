package com.example.cse412financialmanagementsystem;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.cse412financialmanagementsystem.DatabaseConnector.connect;
import static com.example.cse412financialmanagementsystem.StartSceneController.user_id;
import static com.example.cse412financialmanagementsystem.StartSceneController.user_password;
import static com.example.cse412financialmanagementsystem.StartSceneController.balance;

public class AccountInformationController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //GUI components
    @FXML
    private Label balance_amt;

    @FXML
    private TableView<Card> cardTable;
    @FXML
    private TableColumn<Card, String> cardNum;
    @FXML
    private TableColumn<Card, String> card_exp;
    @FXML
    private TableColumn<Card, Integer> card_cvv;
    @FXML
    private TableColumn<Card, Long> card_account;

    @FXML
    private void initialize() throws IOException {
        balance_amt.setText("$"+balance.toString());

        cardNum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        card_exp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardExp()));
        card_cvv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCardCVV()).asObject());
        card_account.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCardAccount()).asObject());

        // Call the DatabaseConnector to get the list of cards
        List<Card> cards = getCards();
        List<Card> user_cards = new ArrayList<Card>();

        if(cards.size() != 0){
            for (Card purchase : cards) {

                //Look for matching cards according to the account id
                if (purchase.getCardAccount().equals(user_id)) {
                    user_cards.add(purchase);
                }
            }

            if(user_cards != null) {
                //Only print if there are existing cards
                cardTable.getItems().addAll(user_cards);
            }
        }
    }

    //Return the user to the payment history scene
    public void switchToMainScene(ActionEvent event) throws IOException {

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Transfer the user to the add/edit payment card scene
    public void addPaymentCard(ActionEvent event) throws IOException {

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("ModifyCard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Save the selected card's information and change the scene to allow the user to edit
    public void editPaymentCard(ActionEvent event) throws IOException {

        //Save the current card's information so that it carries over to the next scene
        Card selectedCard = cardTable.getSelectionModel().getSelectedItem();

        // Change the scene and populate fields in the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCard.fxml"));
        Parent root = loader.load();

        // Access the controller of the next scene
        ModifyCardController modifyCardController = loader.getController();

        // Populate fields with the selected card information
        modifyCardController.populateFields(selectedCard);

        //Change the scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Remove the card from the list
    public void deletePaymentCard(ActionEvent event) throws SQLException {

        //Delete the selected payment card from the database
        //Delete the card from the database
        Card selectedCard = cardTable.getSelectionModel().getSelectedItem(); // gets the selected card from the table
        if(selectedCard != null) {
            deleteCard(selectedCard); //Removing from database
            cardTable.getItems().remove(selectedCard); // remove from the table view
        }else{
            System.out.println("Card is null");
        }
    }

    public static void deleteCard(Card card) throws SQLException {
        try(Connection connection = connect()){
            if (connection == null){
                System.err.println("Connection is null. Check your database connection.");
                return;
            }
            //Delete the selected card from the database
            String cardSQL = "DELETE FROM card WHERE number LIKE " + card.getCardNumber() + "::character varying AND " + "account_id=" + card.getCardAccount();
            try (PreparedStatement statement = connection.prepareStatement(cardSQL)){
                    statement.executeUpdate();
            }
        }catch (SQLException e){
            System.err.println("Error executing SQL query: " + e);
        }
    }

    //Return the user to the login scene and remove the saved credentials
    public void logOutUser(ActionEvent event) throws IOException {

        //Remove the current user's information
        user_id = null;
        user_password = null;

        //Return to the log in scene
        root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        try (Connection connection = connect()) {
            if (connection == null) {
                System.err.println("Connection is null. Check your database connection.");
                return cards;
            }

            String sql = "SELECT number, exp, cvv, account_id FROM card";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Extract data and create an Account object
                    Card curr_card = extractCardInfo(resultSet);
                    cards.add(curr_card);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }
        return cards;
    }

    //Extract the account information within the table
    private static Card extractCardInfo(ResultSet resultSet) throws SQLException {

        String number = (resultSet.getString("number"));
        String exp = (resultSet.getString("exp"));
        Integer cvv = (resultSet.getInt("cvv"));
        Long account_id = (resultSet.getLong("account_id"));

        return new Card(number, exp, cvv, account_id);
    }
}
