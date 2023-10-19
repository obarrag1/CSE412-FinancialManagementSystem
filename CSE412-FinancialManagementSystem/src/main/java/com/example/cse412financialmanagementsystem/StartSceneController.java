package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Validate the user's credentials with the information in the database
    public void checkExistingUser(ActionEvent event) throws IOException {

        //Save the user's credentials to keep track during entire runtime

        //Upon successful login, switch to the main scene
        switchToMainScene(event);
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
}