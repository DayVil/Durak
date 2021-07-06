package de.uni_hannover.hci.cardgame.Network;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class ClientNetwork
{


    private static Socket clientSocket_ = null;
    private static BufferedWriter bufferOut_ = null;
    private static BufferedReader bufferIn_ = null;

    private static boolean loggedIn_ = false;

    public static Socket getClientSocket_()
    {
        return clientSocket_;
    }

    public static BufferedWriter getBufferOut_()
    {
        return bufferOut_;
    }

    public static BufferedReader getBufferIn_()
    {
        return bufferIn_;
    }

    public static boolean isLoggedIn_()
    {
        return loggedIn_;
    }

    public static boolean startConnection(String ip, String port, String password, String user)
    {
        if (loggedIn_)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("Already logged into Server");
            alert.setContentText("Do you want to reconnect");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.setResizable(true);
            alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeNo) return true;
        }
        loggedIn_ = false;

        try
        {
            clientSocket_ = new Socket(ip, Integer.parseInt(port));
        }
        catch (IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("No connection to server");
            alert.setContentText("Please check the IP-address and your internet connection");
            alert.setResizable(true);
            alert.onShownProperty().addListener(ebox -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return false;
        }

        try
        {
            bufferOut_ = new BufferedWriter(new OutputStreamWriter(clientSocket_.getOutputStream()));
            bufferIn_ = new BufferedReader(new InputStreamReader(clientSocket_.getInputStream()));
        }
        catch (IOException e)
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("Could not establish read-write stream");
            alert.setContentText("Please check the IP-address and your internet connection");
            alert.setResizable(true);
            alert.onShownProperty().addListener(ebox -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return false;
        }

        String logInMessage = "Password: " + password + " User: " + user;

        if (!sendMessage(logInMessage)) return false;

        String answer = getMessage();
        System.out.println(answer);

        if (answer.equals("failed"))
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("Server refused connection");
            alert.setContentText("Please check credentials");
            alert.setResizable(true);
            alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return false;
        }
        if (!answer.equals("logged in"))
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("Server send incorrect answer");
            alert.setContentText("Please check IP-address");
            alert.setResizable(true);
            alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return false;
        }
        loggedIn_ = true;
        return true;
    }

    public static boolean sendMessage(String msg)
    {
        try
        {
            bufferOut_.write(msg + "\n");
            bufferOut_.flush();
        }
        catch (IOException e)
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Network Error");
            alert.setHeaderText("Could not send message to server");
            alert.setContentText("Try to reconnect");
            alert.setResizable(true);
            alert.onShownProperty().addListener(ebox -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static String getMessage()
    {
        if (bufferIn_ == null) return "error";
        String answer = null;
        try
        {
            while(answer == null)
            {
                answer = bufferIn_.readLine();
                if (answer != null && answer.equals("disconnect"))
                {
                    stopConnection();
                    return answer;
                }
            }
        }
        catch (IOException e)
        {
            stopConnection();
            return "connection_stopped";
        }
        return answer;
    }

    public static void stopConnection()
    {
        loggedIn_ = false;
        try
        {
            if (bufferIn_ != null)
            {
                bufferIn_.close();
                bufferIn_ = null;
            }
            if (bufferOut_ != null)
            {
                bufferOut_.close();
                bufferOut_ = null;
            }
            if (clientSocket_ != null)
            {
                clientSocket_.close();
                clientSocket_ = null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Exception while closing socket");
        }
    }
}
