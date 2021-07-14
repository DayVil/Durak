package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.SpecialTexture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SettingsController implements ControllerInterface
{

    @FXML
    private MenuItem res_1;

    @FXML
    private MenuItem res_2;

    @FXML
    private ImageView picture;

    @FXML
    private CheckBox FullScreenCheckBox;

    @FXML
    private MenuItem ThemeBlue_;

    @FXML
    private MenuItem ThemeRed_;

    @Override
    public void init()
    {
        Stage stage = gameClient.stage_;
        if (stage.isFullScreen())
        {
            FullScreenCheckBox.setSelected(true);
        }
        Scene scene = stage.getScene();

        picture = (ImageView) scene.lookup("#picture");
        Image image = Cards.getSpecialImage(SpecialTexture.BackLowsat);
        picture.setImage(image);

        PaneResizer.resizePane(scene.getHeight(), true);
    }

    @FXML
    private void goToHome()
    {
        fxmlNavigator.loadFxml(fxmlNavigator.HOME);
    }

    @FXML
    private void changeSound()
    {
        //TODO: only if sound is ever added: change volume of sound to percentage value of soundslider
    }

    @FXML
    private void goFullScreen()
    {
        gameClient.stage_.setFullScreen(!gameClient.stage_.isFullScreen());
    }

    @FXML
    public void ChangeResolution(ActionEvent event)
    {
        if (gameClient.stage_.isFullScreen())
        {
            //FIXME: doesn't really work like i thought it would, leaving this empty so it cannot be resized for now in fullscreen
        }
        else
        {
            if (event.getSource().equals(res_1))        // If event source (selected button of resolution changer) is res_1 (600 x 400) do following
            {
                gameClient.stage_.setWidth(600.0);
                gameClient.stage_.setHeight(400.0);
            }
            else if (event.getSource().equals(res_2))    // If event source (selected button of resolution changer) is res_2 (1200 x 800) do following
            {
                gameClient.stage_.setWidth(1200.0);
                gameClient.stage_.setHeight(800.0);
            }
            gameClient.stage_.centerOnScreen();
        }
    }

    public void changeStyle(ActionEvent event)
    {
        if (event.getSource().equals(ThemeBlue_))        // If event source (selected button of resolution changer) is res_1 (600 x 400) do following
        {
            CSSController.changeTheme("ThemeBlue");
        }
        else if (event.getSource().equals(ThemeRed_))    // If event source (selected button of resolution changer) is res_2 (1200 x 800) do following
        {
            CSSController.changeTheme("ThemeRed");
        }
        else
        {
            CSSController.changeTheme("ThemeBlue");
        }
    }
}