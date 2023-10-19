package com.example.cse412financialmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyReceiptController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //User does not want to add or edit anymore so return them to previous scene
    public void cancelSubmission(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void submitReceipt(ActionEvent event) throws IOException {

        //Check every text box to guarantee appropriate values

        //Add the receipt to the database

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
