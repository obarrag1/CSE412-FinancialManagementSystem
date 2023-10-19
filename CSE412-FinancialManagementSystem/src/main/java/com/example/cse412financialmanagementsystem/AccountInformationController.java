package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountInformationController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("ModifyCard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Remove the card from the list
    public void deletePaymentCard(ActionEvent event){

        //Delete the selected payment card from the database

    }

    //Return the user to the login scene and remove the saved credentials
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
