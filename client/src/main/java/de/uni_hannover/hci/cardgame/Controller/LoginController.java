package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements ControllerInterface
{

    @FXML
    private Pane Login;

    @FXML
    private Label LoginTitle;

    @FXML
    private Label UserTitle;

    @FXML
    private TextField UserTextField;

    @FXML
    private Button LoginButton;

    @Override
    public void init ()
    {
        NodeResizer.originalSceneHeight = 400.0;
        NodeResizer.originalSceneWidth = 600.0;
        resize(gameClient.stage_.getScene().getHeight(), true);
    }

    @Override
    public void resize (Number newValue, Boolean isHeight)
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        double sW = scene.getWidth();
        double sH = scene.getHeight();

        if (isHeight) {
            sH = (double) newValue;
        }
        else
        {
            sW = (double) newValue;
        }

        if (sH <= 0.0 || sW <= 0.0)
        {
            return;
        }

        Login = (Pane) scene.lookup("#Login");
        Login.setPrefWidth(sW);
        Login.setPrefHeight(sH);

        LoginTitle = (Label) scene.lookup("#LoginTitle");
        NodeResizer.resizeObject(sW, sH, LoginTitle, true);

        UserTitle = (Label) scene.lookup("#UserTitle");
        NodeResizer.resizeObject(sW, sH, UserTitle, true);

        UserTextField = (TextField) scene.lookup("#UserTextField");
        NodeResizer.resizeObject(sW, sH, UserTextField, true);

        LoginButton = (Button) scene.lookup("#LoginButton");
        NodeResizer.resizeObject(sW, sH, LoginButton, true);

        NodeResizer.originalSceneWidth = sW;
        NodeResizer.originalSceneHeight = sH;
    }

    @FXML
    private void goToLoading()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
    }


}
