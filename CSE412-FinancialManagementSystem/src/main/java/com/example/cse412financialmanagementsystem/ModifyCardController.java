package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyCardController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //User does not want to add or edit anymore so return them to previous scene
    public void cancelSubmission(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("AccountInformation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void submitPaymentCard(ActionEvent event) throws IOException {

        //Check every text box to guarantee appropriate values

        //Add the new payment card to the database

        //Return the user to their account info
        root = FXMLLoader.load(getClass().getResource("AccountInformation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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
