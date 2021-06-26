package de.uni_hannover.hci.cardgame.Network;

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
    private static int port_ = 8000;
    private static String ip_ ="";
    private static boolean loggedIn_ = false;

    public static boolean startConnection(String ip, String password, String user)
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
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeNo) return true;
        }
        loggedIn_ = false;

        try
        {
            clientSocket_ = new Socket(ip, port_);
        }
        catch (IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("No connection to server");
            alert.setContentText("Please check the IP-address and your internet connection");
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
            alert.showAndWait();
            return false;
        }

        String logInMessage = "User: " + user + " Password: " + password + "\n";

        if(!sendMessage(logInMessage)) return false;

        String answer = getMessage();
        if(answer != null && answer.equals("failed"))
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("Server refused connection");
            alert.setContentText("Please check credentials");
            alert.showAndWait();
            return false;
        }
        if(answer == null || !answer.equals("logged in"))
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connecting To Server");
            alert.setHeaderText("Server send incorrect answer");
            alert.setContentText("Please check IP-address");
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
            bufferOut_.write(msg);
        }
        catch(IOException e)
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Network Error");
            alert.setHeaderText("Could not send message to server");
            alert.setContentText("Try to reconnect");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static String getMessage()
    {
        String answer = null;
        try
        {
            answer = bufferIn_.readLine();
            if(answer.equals("disconnect"))
            {
                stopConnection();
                return null;
            }
        }
        catch(IOException e)
        {
            stopConnection();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Network Error");
            alert.setHeaderText("Server did not answer");
            alert.setContentText("Please try again");
            alert.showAndWait();
            return null;
        }
        return answer;
    }

    public static void stopConnection()
    {
        loggedIn_ = false;
        try
        {
            if(bufferIn_ != null)
            {
                bufferIn_.close();
                bufferIn_ = null;
            }
            if(bufferOut_ != null)
            {
                bufferOut_.close();
                bufferOut_ = null;
            }
            if(clientSocket_ != null)
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
