package de.uni_hannover.hci.cardgame.Controller;
//This class will Control every action of the GameBoard.fxml file

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameBoardController implements ControllerInterface
{

    // Basic controller structure
    @FXML
    private Label label;
    
    @FXML
    private Pane GameBoard;

    // handle needs to be the action on the scenebuilder e.g. in this case handleButtonAction for the menu button
    @FXML
    private void openMenu(ActionEvent event)
    {
        System.out.println("I am the menu button!");
    }
    
    @FXML
    private void goToHome(ActionEvent event)
    {
    	fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    @Override
    public void resize(Stage stage)
    {

    }

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        resize(stage);
    }

}

