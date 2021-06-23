package de.uni_hannover.hci.cardgame.Controller;
//This class will Control every action of the GameBoard.fxml file

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameBoardController implements ControllerInterface
{

    @FXML
    private Pane GameBoard;

    @FXML
    private Pane GameActionsBackground;

    @FXML
    private Pane DeckBackground;

    @FXML
    private Label label;

    @FXML
    private Button Take;

    @FXML
    private Button Pass;

    @FXML
    private Button HomeButton;

    @FXML
    private Button Menu;

    @FXML
    private ImageView leftoverDeck;

    @FXML
    private void openMenu(ActionEvent event)
    {
        System.out.println("I am the menu button!");
    }
    
    @FXML
    private void goToHome(ActionEvent event)
    {
    	fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    @Override
    public void resize(Stage stage)
    {
        // TODO: ImageView is resizing very strange on initial load of it, afterwards on manual resizing it's fine
        //       It seems like the two extra panes arent loaded when resize is called for the first time through init
        // The Pane of the Scene, that has got everything
        Scene scene = stage.getScene();
        GameBoard = (Pane) scene.lookup("#GameBoard");
        GameBoard.setPrefWidth(scene.getWidth());
        GameBoard.setPrefHeight(scene.getHeight());

        GameActionsBackground = (Pane) scene.lookup("#GameActionsBackground");
        double actionsBackWidth = GameActionsBackground.getWidth();
        double actionsBackHeight = GameActionsBackground.getHeight();
        GameActionsBackground.setPrefWidth(scene.getWidth());
        GameActionsBackground.setPrefHeight(scene.getHeight() / 5.0);
        GameActionsBackground.setLayoutX(0.0);
        GameActionsBackground.setLayoutY(0.0);

        DeckBackground = (Pane) scene.lookup("#DeckBackground");
        double deckBackWidth = DeckBackground.getWidth();
        double deckBackHeight = DeckBackground.getHeight();
        DeckBackground.setPrefWidth(scene.getWidth() / 7.0);
        DeckBackground.setPrefHeight(scene.getHeight() / 3.3333);
        DeckBackground.setLayoutX(((scene.getWidth() - deckBackWidth) / 10.0) * 9.0);
        DeckBackground.setLayoutY((scene.getHeight() - deckBackHeight) / 2.0);

        Take = (Button) scene.lookup("#Take");
        double TakeButtonWidth = 75;
        double TakeButtonHeight = 25;
        Take.setPrefWidth(TakeButtonWidth);
        Take.setPrefHeight(TakeButtonHeight);
        Take.setLayoutX(((scene.getWidth() - TakeButtonWidth) / 10.0) * 1.25);
        Take.setLayoutY(((scene.getHeight() - TakeButtonHeight) / 10.0) * 9.0);

        Pass = (Button) scene.lookup("#Pass");
        double PassButtonWidth = 75;
        double PassButtonHeight = 25;
        Pass.setPrefWidth(PassButtonWidth);
        Pass.setPrefHeight(PassButtonHeight);
        Pass.setLayoutX(((scene.getWidth() - PassButtonWidth) / 10.0 ) * 9.0);
        Pass.setLayoutY(((scene.getHeight() - PassButtonHeight) / 10.0) * 9.0);

        HomeButton = (Button) scene.lookup("#HomeButton");
        double HomeButtonWidth = 75;
        double HomeButtonHeight = 25;
        HomeButton.setPrefWidth(HomeButtonWidth);
        HomeButton.setPrefHeight(HomeButtonHeight);
        HomeButton.setLayoutX(((actionsBackWidth - HomeButtonWidth) / 10.0 ) * 1.25);
        HomeButton.setLayoutY((actionsBackHeight - HomeButtonHeight) / 2.0);

        Menu = (Button) scene.lookup("#Menu");
        double MenuButtonWidth = 75;
        double MenuButtonHeight = 25;
        Menu.setPrefWidth(MenuButtonWidth);
        Menu.setPrefHeight(MenuButtonHeight);
        Menu.setLayoutX(((actionsBackWidth - MenuButtonWidth) / 10.0) * 9.0);
        Menu.setLayoutY((actionsBackHeight - MenuButtonHeight) / 2.0);

        leftoverDeck = (ImageView) scene.lookup("#leftoverDeck");
        leftoverDeck.setFitWidth((deckBackWidth / 4.0) * 2.0);
        leftoverDeck.setFitHeight((deckBackHeight / 10.0) * 6.0);
        leftoverDeck.setLayoutX((deckBackWidth - leftoverDeck.getFitWidth()) / 2.0);
        leftoverDeck.setLayoutY((deckBackHeight - leftoverDeck.getFitHeight()) / 2.0);

        label = (Label) scene.lookup("#label");
        double labelWidth = 70;
        double labelHeight = 35;
        label.setPrefWidth(labelWidth);
        label.setPrefHeight(labelHeight);
        label.setLayoutX(leftoverDeck.getLayoutX());
        label.setLayoutY(leftoverDeck.getLayoutY() + leftoverDeck.getFitHeight());

    }

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        resize(stage);
    }

}

