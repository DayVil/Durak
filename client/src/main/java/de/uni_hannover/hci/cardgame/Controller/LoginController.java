package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

// TODO: restructure controller and fxml to use resize function and to implement the init function
public class LoginController implements ControllerInterface
{

    @FXML
    private Button loginButton;

    @FXML
    private void goToLoading()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
    }

    @Override
    public void resize(Number newValue, Boolean isHeight)
    {

    }

    @Override
    public void init()
    {

    }
}
