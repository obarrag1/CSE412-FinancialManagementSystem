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
        FXMLLoader fxmlLoader = new FXMLLoader(WindowApplication.class.getResource("window-view.fxml"));

        //Save the values for the screen's dimensions
        //double windowWidth = Screen.getPrimary().getBounds().getWidth();
        //double windowHeight = Screen.getPrimary().getBounds().getHeight();

        //Set the window size to the fullscreen based on the user's screen
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}