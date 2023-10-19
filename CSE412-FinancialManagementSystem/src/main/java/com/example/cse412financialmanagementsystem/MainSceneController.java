package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("ModifyReceipt.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Remove the selected receipt from the list
    public void deleteReceipt(ActionEvent event){

        //Delete the receipt from the database

    }

    //Return the user to the login scene and remove the saved credentials
    public void logOutUser(ActionEvent event) throws IOException {

        //Remove the user's current information

        //Change the scene
        root = FXMLLoader.load(getClass().getResource("StartScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
