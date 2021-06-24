package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements ControllerInterface
{

    @Override
    public void init ()
    {
    }

    @Override
    public void resize (Number newValue, Boolean isHeight)
    {

    }
    @FXML
    private void goToLoading()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
    }


}
