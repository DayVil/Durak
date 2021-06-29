package de.uni_hannover.hci.cardgame.Controller;
//This class will Control every action of the GameBoard.fxml file

import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.ParsedServerMessage;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.SpecialTexture;
import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.Network.ClientNetwork;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class GameBoardController implements ControllerInterface
{
    @FXML
    public Label DrawPileCounter;

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

    private static ArrayList<ImageView> imageViewArrayList;

    @FXML
    private void openMenu()
    {
        // currently sending you back to home, has to be changed in the future
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    public void setToSize()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        double sW = scene.getWidth();
        double sH = scene.getHeight();

        resize(sW, false);
        resize(sH, true);
    }

    @Override
    public void resize(Number newValue, Boolean isHeight)
    {
        Stage stage = gameClient.stage_;
        // The Pane of the Scene, that has got everything
        Scene scene = stage.getScene();

        double sW = scene.getWidth();
        double sH = scene.getHeight();

        if (isHeight)
        {
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
        GameActionsBackGround.setPrefHeight(sH / 5.0);

        Menu = (Button) scene.lookup("#Menu");
        NodeResizer.resizeObject(sW, sH, Menu, true);

        Take = (Button) scene.lookup("#Take");
        NodeResizer.resizeObject(sW, sH, Take, true);

        Pass = (Button) scene.lookup("#Pass");
        NodeResizer.resizeObject(sW, sH, Pass, true);

        if (imageViewArrayList != null)
        {
            for (ImageView iv : imageViewArrayList)
            {
                NodeResizer.resizeObject(sW, sH, iv, true);
            }
        }
        else
        {
            System.out.printf("List ist null");
        }


        NodeResizer.originalSceneWidth = sW;
        NodeResizer.originalSceneHeight = sH;
    }

    public void executeLine(String line)
    {
        System.out.println("Message from server:" + line);
        ParsedServerMessage parsedServerMessage = new ParsedServerMessage(line);
        draw(parsedServerMessage);
        setToSize();
    }

    public void draw(ParsedServerMessage parsedServerMessage)
    {
        imageViewArrayList = new ArrayList<>();
        String drawPileText = String.format("%d", parsedServerMessage.getDrawPileHeight_());
        DrawPileCounter.setText(drawPileText);

        int handSize = parsedServerMessage.getHandCards_().size();
        for (int i = 0; i < handSize; i++)
        {
            drawCard(parsedServerMessage.getHandCards_().get(i), 100 + i * 10, 100, true /*true if not debugging*/);
        }

    }

    public void drawCard(int cardNumber, int x, int y, boolean isHandCard)
    {
        ImageView imageView = new ImageView();
        Image image = new Image(Cards.getCardTexture(cardNumber), 200, 10000, true, true);
        imageView.setImage(image);
        imageView.setCache(true);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(75);
        if (isHandCard)
        {
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    cardClicked(cardNumber);
                }
            });
        }
        GameBoard.getChildren().add(imageView);
        imageViewArrayList.add(imageView);
    }

    public void drawCard(int x, int y, int card)
    {
        String texture = Cards.getCardTexture(card);

    }

    @Override
    public void init()
    {
        NodeResizer.originalSceneHeight = 400.0;
        NodeResizer.originalSceneWidth = 600.0;
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();

        leftoverDeck = (ImageView) scene.lookup("#leftoverDeck");
        Image image = new Image(Cards.getSpecialTexture(SpecialTexture.BackLowsat), 200, 200, true, true);
        leftoverDeck.setImage(image);
        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished
                (
                        pauseFinishedEvent -> resize(scene.getHeight(), true)
                );
        pause.play();

        networkHandler task = new networkHandler();
        new Thread(task).start();
    }

    public void cardClicked(int nr)
    {
        ClientNetwork.sendMessage(String.format("%d\n", nr));
    }

    public void debugrequestgamestate(ActionEvent actionEvent)
    {
        ClientNetwork.sendMessage("Gimme Gamestate\n");
    }

    public void actionTake(ActionEvent actionEvent)
    {
        ClientNetwork.sendMessage("TakeAction\n");
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
                    String line = ClientNetwork.getMessage();
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

