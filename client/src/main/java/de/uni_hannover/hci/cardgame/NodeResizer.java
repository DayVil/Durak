package de.uni_hannover.hci.cardgame;

import javafx.scene.Node;

import java.util.ArrayList;

public class NodeResizer
{

    public static double oldSceneWidth;
    public static double oldSceneHeight;

    public static void resizeNodeList(double sceneWidth, double sceneHeight, ArrayList<Node> nodeList, Boolean isGettingRescaled)
    {
        for (Node node:nodeList)
        {
            resizeNode(sceneWidth, sceneHeight, node, isGettingRescaled);
        }
    }

    public static void resizeNode(double sceneWidth, double sceneHeight, Node node, Boolean isGettingRescaled)
    {
        double newFactor = sceneWidth / gameClient.stageMinWidth_;
        if (newFactor > sceneHeight / gameClient.stageMinHeight_)
        {
            newFactor = sceneHeight / gameClient.stageMinHeight_;
        }

        double oW = node.getBoundsInLocal().getWidth();
        double oH = node.getBoundsInLocal().getHeight();

        if (oW <= 0.0 || oH <= 0.0)
        {
            return;
        }

        if (isGettingRescaled)
        {
            rescaleObject(node, newFactor);
        }

        double newX = ((node.getLayoutX() + oW / 2.0) * (sceneWidth / oldSceneWidth)) - oW / 2.0;
        double newY = ((node.getLayoutY() + oH / 2.0) * (sceneHeight / oldSceneHeight)) - oH / 2.0;

        if (newX > 0.0)
        {
            node.setLayoutX(newX);
        }

        if (newY > 0.0)
        {
            node.setLayoutY(newY);
        }
    }

    public static void rescaleObject(Node node, double factor)
    {
        node.setScaleX(factor);
        node.setScaleY(factor);
    }

}
