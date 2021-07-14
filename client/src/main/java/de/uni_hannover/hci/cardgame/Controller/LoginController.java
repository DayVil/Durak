package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.Network.ClientNetwork;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controls the login screen and login of the client to the server. Gets the user given network settings, checks those on consistency and starts the connection on button click.
 *
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */

public class LoginController implements ControllerInterface
{
    @FXML
    private TextField IPAddress;

    @FXML
    private TextField Port;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField UserName;

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        PaneResizer.resizePane(scene.getHeight(), true);
    }

    @FXML
    private void goToHome()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    /**
     * Check login credentials and establish server client connection
     */
    @FXML
    private void checkForEntrance()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        IPAddress = (TextField) scene.lookup("#IPAddress");
        String ip = IPAddress.getText();

        Port = (TextField) scene.lookup("#Port");
        String port = Port.getText();

        UserName = (TextField) scene.lookup("#UserName");
        String user = UserName.getText();
        user = user.replace(' ', '_');

        Password = (PasswordField) scene.lookup("#Password");
        String password = Password.getText();

        boolean isValidIP = validateIP(ip);
        boolean isValidUserName = user.length() > 0;
        boolean isValidPassword = true;

        if (!isValidIP)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText("Invalid IP");
            alert.setContentText("Use the format xxx.xxx.xxx.xxx \nExample: 192.168.000.001");
            alert.setResizable(true);
            alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
        }
        else if (!isValidUserName)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText("Invalid User Name");
            alert.setContentText("Please enter a Username");
            alert.setResizable(true);
            alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
        }
        else if (!isValidPassword)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText("Invalid Server Password");
            alert.setContentText("PLease enter the servers password");
            alert.setResizable(true);
            alert.onShownProperty().addListener(e -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
        }
        else
        {
            boolean success = ClientNetwork.startConnection(ip, port, password, user);
            if (success) fxmlNavigator.loadFxml(fxmlNavigator.GAME);
        }
    }

    /**
     * Checks whether an ip address is valid
     *
     * @param ip address as string
     * @return boolean for valid ip
     */
    private static boolean validateIP(String ip)
    {
        return ip.matches("^(([01]\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}([01]\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }
}
