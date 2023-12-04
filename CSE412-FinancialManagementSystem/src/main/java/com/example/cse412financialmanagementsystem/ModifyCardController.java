package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.cse412financialmanagementsystem.DatabaseConnector.connect;
import static com.example.cse412financialmanagementsystem.StartSceneController.user_id;

public class ModifyCardController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField number_text;
    @FXML
    private TextField id_text;
    @FXML
    private TextField exp_text;
    @FXML
    private TextField cvv_text;
    @FXML
    private Label error_label;

    //User does not want to add or edit anymore so return them to previous scene
    public void cancelSubmission(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("AccountInformation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void submitPaymentCard(ActionEvent event) throws IOException {



        //Check that every field has text
        if(!number_text.getText().isEmpty() && !id_text.getText().isEmpty()
                && !exp_text.getText().isEmpty() && !cvv_text.getText().isEmpty()){

            //Add the new payment card to the database
            String number = number_text.getText();
            String exp = exp_text.getText();
            int cvv = Integer.parseInt(cvv_text.getText());
            Long account_id = Long.parseLong(id_text.getText());

            //Incorrect account number
            if(!user_id.equals(account_id)){
                error_label.setText("Account ID does not match!");
                return;
            }

            try (Connection connection = connect()) {
                if (connection == null) {
                    System.err.println("Connection is null. Check your database connection.");
                }
                String sql = "INSERT INTO card (number, exp, cvv, account_id) VALUES ('" + number + "', '" + exp + "', '" + cvv + "', '" + account_id +"')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

                //Return the user to their account info
                root = FXMLLoader.load(getClass().getResource("AccountInformation.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //Return the user to the log in screen and remove the saved credentials
    public void logOutUser(ActionEvent event) throws IOException {

        //Remove the current user's information

        //Return the user to the sign in scene
        root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
