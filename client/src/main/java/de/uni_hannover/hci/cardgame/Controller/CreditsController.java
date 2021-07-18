package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This Controller is managing the initial load of this pane and a button-click-event called goBack()
 *
 * @version 18.07.2021
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */
public class CreditsController implements ControllerInterface
{
    /**
     * This method automatically resizes the content pane to the current stages size on its initial load
     */
    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        PaneResizer.resizePane(scene.getHeight(), true);
    }

    /**
     * This method will load the Home Screen
     */
    @FXML
    private void goBack()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }
}
