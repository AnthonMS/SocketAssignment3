package com.company.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketGUIController {


    public TextArea inputArea;
    public TextArea displayArea;
    public Button sendBtn;
    public Button countBtn;
    public Button connectBtn;


    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Scanner scanServerOutput;
    private PrintWriter writeServer;
    private int duration = 5000;
    private int cycleCount = 100;


    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        setInputOutput();
        if (!inputArea.getText().isEmpty()) {
            //inputArea is not empty
            writeServer.println(inputArea.getText());

            //boolean done = false;
            //while (!done)
            //{
            if (scanServerOutput.hasNextLine()) {
                displayArea.appendText(scanServerOutput.nextLine() + "\n");
            }
            //else
            //{
            //done = true;
            //}
            //}
        }
    }

    @FXML
    public void handleCountButtonAction(ActionEvent actionEvent) {
        //setInputOutput();
        //writeServer.println("COUNT:");
        //displayArea.appendText(scanServerOutput.nextLine() + "\n");
    }

    @FXML
    public void handleStartButtonAction(ActionEvent actionEvent) {
        try {
            socket = new Socket("127.0.0.1", 8001);
            displayArea.appendText("** Tilsluttet Server **\n");


            setInputOutput();
            timer();

            displayArea.appendText(scanServerOutput.nextLine() + "\n");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInputOutput() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            scanServerOutput = new Scanner(inputStream);

            writeServer = new PrintWriter(outputStream, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void counter() {
        setInputOutput();
        writeServer.println("COUNT:");
        displayArea.appendText(scanServerOutput.nextLine() + "\n");
    }

    public void timer() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(duration), ae -> counter()));
        timeline.setCycleCount(cycleCount);
        timeline.play();
    }
}


