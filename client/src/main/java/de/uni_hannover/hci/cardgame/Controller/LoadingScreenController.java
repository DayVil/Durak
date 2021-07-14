package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreenController implements ControllerInterface
{
    double progressBarValue;
    int imageNumber;

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        ProgressBar progressBar = (ProgressBar) scene.lookup("#LoadingBar");
        progressBar.setProgress(0);

        progressBarValue = 0.0;
        imageNumber = 0;

        PauseTransition pause = new PauseTransition(Duration.millis(1));
        pause.setOnFinished
                (
                        e ->
                        {
                            if (imageNumber < Cards.getImageBufferSize())
                            {
                                Cards.getImage(imageNumber);
                                progressBarValue = 1.0 * imageNumber / Cards.getImageBufferSize();
                                progressBar.setProgress(progressBarValue);
                                imageNumber++;
                                pause.playFromStart();
                            }
                            else
                            {
                                fxmlNavigator.loadFxml(fxmlNavigator.HOME);
                            }
                        }
                );
        pause.play();

        PaneResizer.resizePane(scene.getHeight(), true);
    }
}
