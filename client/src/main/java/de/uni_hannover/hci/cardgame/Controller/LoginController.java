package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import java.net.*;
import java.io.*;

public class LoginController implements ControllerInterface {

    private Socket clientSocket;
    private PrintWriter bufferOut;
    private BufferedReader bufferIn;

    @FXML
    private Pane Login;

    @FXML
    private Pane Content;

    @FXML
    private TextField IPAddress;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField UserName;

    @FXML
    private Button PlayButton;

    @FXML
    private Button Back;

    @FXML
    private Label label;

    @FXML
    private void goToHome() {
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    /**
     * Check login credentials and establish server client connection
     */
    @FXML
    private void checkForEntrance() {
//        fxmlNavigator.loadFxml(fxmlNavigator.GAME);

        boolean isValidIP = validateIP(IPAddress.getCharacters().toString());
        boolean isValidUserName = UserName.getCharacters().length() > 0;
        boolean isValidPassword = Password.getCharacters().length() > 0;

        if (!isValidIP) {
            System.out.println("Please enter a valid IP");
        } else if (!isValidUserName) {
            System.out.println("Please enter a valid username");
        } else if (!isValidPassword) {
            System.out.println("Please enter a valid password");
        } else {
            System.out.println("Connecting to server " + IPAddress.getCharacters());

            startConnection("127.0.0.1", 8000);
            if (clientSocket.isConnected()) {
                System.out.println("Connecting successful");
            }
            String test = sendMessage("test");
            System.out.println("this return" + test);
        }

    }

    @Override
    public void resize(Number newValue, Boolean isHeight) {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        double sW = scene.getWidth();
        double sH = scene.getHeight();

        if (isHeight) {
            sH = (double) newValue;
        } else {
            sW = (double) newValue;
        }

        if (sH <= 0.0 || sW <= 0.0) {
            return;
        }

        Login = (Pane) scene.lookup("#Login");
        Login.setPrefWidth(sW);
        Login.setPrefHeight(sH);

        Content = (Pane) scene.lookup("#Content");
        Content.setPrefHeight(sH);
        Content.setPrefWidth(sW / 3.0);

        IPAddress = (TextField) scene.lookup("#IPAddress");
        NodeResizer.resizeObject(sW, sH, IPAddress, true);

        Password = (PasswordField) scene.lookup("#Password");
        NodeResizer.resizeObject(sW, sH, Password, true);

        UserName = (TextField) scene.lookup("#UserName");
        NodeResizer.resizeObject(sW, sH, UserName, true);

        label = (Label) scene.lookup("#label");
        NodeResizer.resizeObject(sW, sH, label, true);

        Back = (Button) scene.lookup("#Back");
        NodeResizer.resizeObject(sW, sH, Back, true);

        PlayButton = (Button) scene.lookup("#PlayButton");
        NodeResizer.resizeObject(sW, sH, PlayButton, true);

        NodeResizer.originalSceneWidth = sW;
        NodeResizer.originalSceneHeight = sH;
    }

    @Override
    public void init() {
        NodeResizer.originalSceneHeight = 400.0;
        NodeResizer.originalSceneWidth = 600.0;
        Scene scene = gameClient.stage_.getScene();
        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished
                (
                        pauseFinishedEvent -> resize(scene.getHeight(), true)
                );
        pause.play();
    }


    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            bufferOut = new PrintWriter(clientSocket.getOutputStream(), true);
            bufferIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("error");
        }

    }

    public String sendMessage(String msg) {
        try {
            bufferOut.println(msg);
            return bufferIn.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public void stopConnection() {
        try {
            bufferIn.close();
            bufferOut.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error");
        }

    }


    /**
     * Checks whether an ip adress is valid
     * @param ip address as string
     * @return boolean for valid ip
     */
    private static boolean validateIP(String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }
}
