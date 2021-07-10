package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreditsController implements ControllerInterface
{
    @FXML
    private void goBack()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        PaneResizer.resizePane(scene.getHeight(), true);
    }
}
