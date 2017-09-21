package com.company.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;

public class SocketGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SocketGUI_fxml.fxml"));
        primaryStage.setTitle("Socket GUI");
        primaryStage.setScene(new Scene(root, 640, 460));
        primaryStage.show();

    }
}
