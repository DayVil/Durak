package de.uni_hannover.hci.cardgame.Controller;
//This class will Control every action of the GameBoard.fxml file

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameBoardController implements ControllerInterface
{

    @FXML
    private Pane GameBoard;

    @FXML
    private Pane DeckBackground;

    @FXML
    private Pane GameActionsBackGround;

    @FXML
    private Button Take;

    @FXML
    private Button Pass;

    @FXML
    private Button Menu;

    @FXML
    private ImageView leftoverDeck;

    @FXML
    private void openMenu()
    {
        // currently sending you back to home, has to be changed in the future
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    @Override
    public void resize (Number newValue, Boolean isHeight)
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

        GameBoard = (Pane) scene.lookup("#GameBoard");
        GameBoard.setPrefWidth(sW);
        GameBoard.setPrefHeight(sH);

        DeckBackground = (Pane) scene.lookup("#DeckBackground");
        NodeResizer.resizeObject(sW, sH, DeckBackground, true);

        GameActionsBackGround = (Pane) scene.lookup("#GameActionsBackGround");
        NodeResizer.resizeObject(sW, sH, GameActionsBackGround, true);

        Take = (Button) scene.lookup("#Take");
        NodeResizer.resizeObject(sW, sH, Take, true);

        Pass = (Button) scene.lookup("#Pass");
        NodeResizer.resizeObject(sW, sH, Pass, true);

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

        leftoverDeck = (ImageView) scene.lookup("#leftoverDeck");
        Image image = new Image("/textures/cards/card_back_lowsat.png", 75, 200, true, true);
        leftoverDeck.setImage(image);

        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished
                (
                        pauseFinishedEvent -> resize(scene.getHeight(), true)
                );
        pause.play();
    }

}

