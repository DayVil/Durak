package de.uni_hannover.hci.cardgame;

import javafx.scene.Node;

public class NodeResizer
{

    public static double originalSceneWidth;
    public static double originalSceneHeight;

    public static void resizeObject(double sW, double sH, Node node, Boolean isGettingRescaled)
    {
        double oW = node.getBoundsInLocal().getWidth();
        double oH = node.getBoundsInLocal().getHeight();

        if (oW <= 0.0 || oH <= 0.0)
        {
            return;
        }

        if (isGettingRescaled)
        {
            rescaleObject(node, sH, sW);
        }

        double newX = ((node.getLayoutX() + oW / 2.0) * (sW / originalSceneWidth)) - oW / 2.0;
        double newY = ((node.getLayoutY() + oH / 2.0) * (sH / originalSceneHeight)) - oH / 2.0;

        if (newX > 0.0)
        {
            node.setLayoutX(newX);
        }

        if (newY > 0.0)
        {
            node.setLayoutY(newY);
        }
    }

    public static void rescaleObject(Node node, double sH, double sW)
    {
        double factor = sW / 600.0;
        if (factor > sH / 400.0)
        {
            factor = sH / 400.0;
        }
        node.setScaleX(factor);
        node.setScaleY(factor);
    }

}
