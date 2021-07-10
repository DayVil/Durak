package de.uni_hannover.hci.cardgame;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class PaneResizer
{
    public static void resizePane(Number newValue, Boolean isHeight)
    {
        Scene scene = gameClient.stage_.getScene();

        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        if (isHeight)
        {
            sceneHeight = (double) newValue;
        }
        else
        {
            sceneWidth = (double) newValue;
        }

        if (sceneHeight <= 0.0 || sceneWidth <= 0.0)
        {
            return;
        }

        double newFactor = sceneWidth / gameClient.stageMinWidth_;
        if (newFactor > sceneHeight / gameClient.stageMinHeight_)
        {
            newFactor = sceneHeight / gameClient.stageMinHeight_;
        }

        double translateX = (gameClient.stageMinWidth_ * newFactor - gameClient.stageMinWidth_) / 2.0;
        double translateY = (gameClient.stageMinHeight_ * newFactor - gameClient.stageMinHeight_) / 2.0;

        if (gameClient.stageMinWidth_ * newFactor < sceneWidth)	translateX = translateX + (sceneWidth - gameClient.stageMinWidth_ * newFactor) / 2.0;
        if (gameClient.stageMinHeight_ * newFactor < sceneHeight)	translateY = translateY + (sceneHeight - gameClient.stageMinHeight_ * newFactor) / 2.0;


        Pane pane = null;

        if (scene.lookup("#Startup") != null)	pane = (Pane)scene.lookup("#Startup");
        if (scene.lookup("#Loading") != null)	pane = (Pane)scene.lookup("#Loading");
        if (scene.lookup("#Home") != null)		pane = (Pane)scene.lookup("#Home");
        if (scene.lookup("#Settings") != null)	pane = (Pane)scene.lookup("#Settings");
        if (scene.lookup("#GameBoard") != null)	pane = (Pane)scene.lookup("#GameBoard");
        if (scene.lookup("#Login") != null)		pane = (Pane)scene.lookup("#Login");
        if (scene.lookup("#Credits") != null)	pane = (Pane)scene.lookup("#Credits");

        Objects.requireNonNull(pane).setTranslateX(translateX);
        pane.setTranslateY(translateY);

        pane.setScaleX(newFactor);
        pane.setScaleY(newFactor);
    }
}
