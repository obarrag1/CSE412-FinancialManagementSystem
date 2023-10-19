package com.example.cse412financialmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //Load the fxml file for the start window
        FXMLLoader fxmlLoader = new FXMLLoader(WindowApplication.class.getResource("StartScene.fxml"));

        //Set the window size and initialize it
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}