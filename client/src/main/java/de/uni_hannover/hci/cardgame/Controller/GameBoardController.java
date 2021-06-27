package de.uni_hannover.hci.cardgame.Controller;
//This class will Control every action of the GameBoard.fxml file

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.Network.ClientNetwork;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


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

    private ImageView iv1;

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
        GameActionsBackGround.setPrefWidth(sW);
        GameActionsBackGround.setPrefHeight(sH/5.0);

        Menu = (Button) scene.lookup("#Menu");
        NodeResizer.resizeObject(sW, sH, Menu, true);

        Take = (Button) scene.lookup("#Take");
        NodeResizer.resizeObject(sW, sH, Take, true);

        Pass = (Button) scene.lookup("#Pass");
        NodeResizer.resizeObject(sW, sH, Pass, true);

        NodeResizer.originalSceneWidth = sW;
        NodeResizer.originalSceneHeight = sH;

    }

    public void executeLine(String line)
    {
        System.out.println("Message from server:" + line);
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

        iv1 = new ImageView();
        Image i1 = new Image("/textures/cards/diamonds/ace_of_diamonds.png", 200, 10000, true, true);
        iv1.setImage(i1);
        iv1.setCache(true);
        iv1.setLayoutX(205.0);
        iv1.setLayoutY(150.0);
        iv1.setPreserveRatio(true);
        iv1.setFitWidth(75);
        iv1.setRotate(330.0);
        iv1.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                cardClicked();
            }
        });
        iv1.setId("iv1");
        GameBoard.getChildren().add(iv1);

        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished
                (
                        pauseFinishedEvent -> resize(scene.getHeight(), true)
                );
        pause.play();

        networkHandler task = new networkHandler();
        new Thread(task).start();
    }

    public void cardClicked()
    {
        ClientNetwork.sendMessage("11\n");
    }

    class networkHandler implements Runnable
    {
        Socket socket_;
        BufferedReader inputBuffer_;
        BufferedWriter outputBuffer_;

        networkHandler()
        {
            socket_ = ClientNetwork.getClientSocket_();
            inputBuffer_ = ClientNetwork.getBufferIn_();
            outputBuffer_ = ClientNetwork.getBufferOut_();
        }

        @Override
        public void run()
        {
            try
            {
                System.out.printf("In NetworkHandler Run\n");
                while (true)
                {
                    System.out.printf("Waiting for input from server\n");
                    String line = inputBuffer_.readLine();
                    System.out.printf("Got Message %s\n", line);

                    if (line.equals("disconnect")) break;

                    Platform.runLater(() -> executeLine(line));
                }
                socket_.close();
            }
            catch (IOException e)
            {
                System.err.println(e);
            }
        }
    }
}

