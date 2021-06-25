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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreditsController implements ControllerInterface
{

    @FXML
    private Pane Credits;

    @FXML
    private Label label;

    @FXML
    private Text CreditsText;

    @FXML
    private Button Back;

    @FXML
    private void goBack()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    @Override
    public void resize(Number newValue, Boolean isHeight)
    {
        Stage stage = gameClient.stage_;
        // The Pane of the Scene, that has got everything
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

        Credits = (Pane) scene.lookup("#Credits");
        Credits.setPrefWidth(sW);
        Credits.setPrefHeight(sH);

        label = (Label) scene.lookup("#label");
        NodeResizer.resizeObject(sW, sH, label, true);

        CreditsText = (Text) scene.lookup("#CreditsText");
        NodeResizer.resizeObject(sW, sH, CreditsText, true);

        Back = (Button) scene.lookup("#Back");
        NodeResizer.resizeObject(sW, sH, Back, true);

        NodeResizer.originalSceneWidth = sW;
        NodeResizer.originalSceneHeight = sH;
    }

    @Override
    public void init()
    {
        NodeResizer.originalSceneHeight = 400.0;
        NodeResizer.originalSceneWidth = 600.0;
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished
                (
                        pauseFinishedEvent -> resize(scene.getHeight(), true)
                );
        pause.play();
    }
}
