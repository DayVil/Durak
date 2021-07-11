package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.gameLogic.Cards.*;
import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.Network.ClientNetwork;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private ImageView leftoverDeck;

    private final ArrayList<Node> addedNodesArrayList = new ArrayList<>();

    boolean killClientNetworkHandler;

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        Scene scene = stage.getScene();
        //GameBoard.setStyle("-fx-background-color: #0000ff");

        leftoverDeck = (ImageView) scene.lookup("#leftoverDeck");
        Image image = Cards.getSpecialImage(SpecialTexture.BackLowsat);
        leftoverDeck.setImage(image);

        PaneResizer.resizePane(scene.getHeight(), true);

        killClientNetworkHandler = false;
        networkHandler task = new networkHandler();
        new Thread(task).start();
    }

    public void disconnect()
    {
        if (ClientNetwork.isLoggedIn_())
        {
            System.out.println("Shutdown client");
            ClientNetwork.sendMessage("disconnect");
            killClientNetworkHandler = true;
        }
        ClientNetwork.stopConnection();
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    public void cardClicked(int nr)
    {
        ClientNetwork.sendMessage(String.format("%d", nr));
    }

    public void pass()
    {
        ClientNetwork.sendMessage("pass");
    }

    public void take()
    {
        ClientNetwork.sendMessage("take");
    }

    public void executeLine(String line)
    {
        System.out.println("Message from server:" + line);
        if (line.equals("connection_stopped"))
        {
            return;
        }
        if (line.equals("error"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Network Error");
            alert.setHeaderText("Server did not answer");
            alert.setContentText("Please try again");
            alert.setResizable(true);
            alert.onShownProperty().addListener(ebox -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return;
        }
        if (line.equals("disconnect"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message from Server");
            alert.setHeaderText("Server Disconnected");
            alert.setContentText("Please try to reconnect");
            alert.setResizable(true);
            alert.onShownProperty().addListener(ebox -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
            return;
        }
        ParsedServerMessage parsedServerMessage = new ParsedServerMessage(line);
        draw(parsedServerMessage);
        PaneResizer.resizePane(gameClient.stage_.getScene().getHeight(), true);
    }


    public void draw(ParsedServerMessage parsedServerMessage)
    {
        undrawOldNodes();

        //CARDSTACKCOUNT
        String drawPileText = String.format("%d", parsedServerMessage.getDrawPileHeight_());
        DrawPileCounter.setText(drawPileText);

        //TRUMP
        drawTrump(parsedServerMessage.getTrumpColor_());

        //PLAYERLIST
        int playerStart = 60;
        int playerEnd = 460;
        int playerVerticalTop = 5;
        int playerVerticalBottom = playerVerticalTop + 40;

        int playerCount = parsedServerMessage.getPlayers_().size();

        int playerSpace = (playerEnd - playerStart) / ((playerCount + 1) / 2);

        for (int i = 0; i < playerCount; i++)
        {
            int x;
            int y;
            if(i < (playerCount + 1) / 2)
            {
                x = playerStart + i * playerSpace;
                y = playerVerticalTop;
            }
            else
            {
                x = playerStart + (i - (playerCount + 1) / 2) * playerSpace;
                y = playerVerticalBottom;
            }

            drawPlayer(parsedServerMessage.getPlayers_().get(i), x, y, (playerSpace * 0.95));
        }

        //VISIBLECARDS
        int visibleCardsHorizontalSpacing = 80;
        int visibleCardsVerticalTop = 90;
        int visibleCardsVerticalBottom = visibleCardsVerticalTop + 110;

        int visibleCardsCount = parsedServerMessage.getVisibleCards_().size();
        for (int i = 0; i < visibleCardsCount; i++)
        {
            int[] arr = parsedServerMessage.getVisibleCards_().get(i);
            int x = 140 + (i % 3) * visibleCardsHorizontalSpacing;
            drawCard(arr[0], x, (i < 3) ? visibleCardsVerticalTop : visibleCardsVerticalBottom, false);
            if (arr[1] >= 11)
            {
                drawCard(arr[1], x, (i < 3) ? visibleCardsVerticalTop + 15 : visibleCardsVerticalBottom + 15, false);
            }
        }

        //HANDCARDS
        int handStart = 120;
        int handEnd = 480;
        int cardSize = 55;
        int handSpace = handEnd - handStart - cardSize;

        int handSize = parsedServerMessage.getHandCards_().size();
        for (int i = 0; i < handSize; i++)
        {
            int x = handStart + i * (handSpace / handSize);
            int y = 315;
            drawCard(parsedServerMessage.getHandCards_().get(i), x, y, true);
        }

        // WAS SUCCESSFUL
        if (!parsedServerMessage.getWasSuccessful_())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Action");
            alert.setHeaderText("You tried to take an invalid action");
            alert.setContentText("Its either not your turn or the action you tried to do\nis not acceptable by the rules of the game\n");
            alert.setResizable(true);
            alert.onShownProperty().addListener(ebox -> Platform.runLater(() -> alert.setResizable(false)));
            alert.showAndWait();
        }
    }

    private void undrawOldNodes()
    {
        System.out.printf("Memory: %d\n", Runtime.getRuntime().freeMemory());
        for (Node n:addedNodesArrayList)
        {
            GameBoard.getChildren().remove(n);
        }
        addedNodesArrayList.clear();
    }

    private void drawPlayer(ParsedServerMessage.Player player, int x, int y, double width)
    {
        int cards = player.getHandCardAmount_();
        String name = player.getName_();

        String playerString = String.format(" %d    %s ", cards, name);

        Label playerLable = new Label();
        playerLable.setLayoutX(x);
        playerLable.setLayoutY(y);
        playerLable.setPrefWidth(width);
        playerLable.setText(playerString);
        Image image;
        if (player.isAttacker_())
        {
            image = Cards.getSpecialImage(SpecialTexture.SwordIcon);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(30);
            playerLable.setGraphic(imageView);

        }
        else if (player.isDefender_())
        {
            image = Cards.getSpecialImage(SpecialTexture.ShieldIcon);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(30);
            playerLable.setGraphic(imageView);
        }
        /*else
        {
            image = Cards.getSpecialImage(SpecialTexture.NullIcon);
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(30);
        playerLable.setGraphic(imageView);*/

        if (player.isActive_()) playerLable.setStyle("-fx-background-color: #a0f0a0");

        GameBoard.getChildren().add(playerLable);
        addedNodesArrayList.add(playerLable);
    }


    public void drawTrump(CardColor trump)
    {
        if (trump == null) return;
        ImageView imageView = new ImageView();
        Image image = Cards.getColorSymbolImage(trump);
        imageView.setImage(image);
        imageView.setCache(true);
        imageView.setLayoutX(10);
        imageView.setLayoutY(20);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(40);
        GameBoard.getChildren().add(imageView);
        addedNodesArrayList.add(imageView);
    }

    public void drawCard(int cardNumber, int x, int y, boolean isHandCard)
    {
        ImageView imageView = new ImageView();
        Image image = Cards.getImage(cardNumber);
        imageView.setImage(image);
        imageView.setCache(true);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(55);
        if (isHandCard)
        {
            imageView.setOnMouseClicked(event -> cardClicked(cardNumber));
        }
        GameBoard.getChildren().add(imageView);
        addedNodesArrayList.add(imageView);
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
            System.out.print("In NetworkHandler Run\n");
            while (!killClientNetworkHandler)
            {
                System.out.print("Waiting for input from server\n");
                String line = ClientNetwork.getMessage();
                System.out.printf("Got Message %s\n", line);

                if (line != null)
                {
                    Platform.runLater(() -> executeLine(line));
                    if (line.equals("disconnect")) break;
                    if (line.equals("error")) break;
                }
            }
            ClientNetwork.stopConnection();
        }
    }
}

