package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.Cards;
import de.uni_hannover.hci.cardgame.gameLogic.Cards.SpecialTexture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsController implements ControllerInterface
{

	@FXML
	private Pane Settings;

	private static ArrayList<Node> nodeRescaleArrayList_;
	private static ArrayList<Node> nodeArrayList_;

	@FXML
	private Pane Content;
	
	@FXML
	private SplitMenuButton Resolution;
	
	@FXML
	private MenuItem res_1;
	
	@FXML
	private MenuItem res_2;

	@FXML
	private Label label;

	@FXML
	private ImageView picture;

	@FXML
	private CheckBox FullScreenCheckBox;

	@FXML
	private Slider SoundSlider;

	@FXML
	private Button BackButton;

	@FXML
	private Label SoundLabel;

	@FXML
	private SplitMenuButton Theme;

	@FXML
	private MenuItem ThemeBlue_;

	@FXML
	private MenuItem ThemeRed_;

	@Override
	public void init()
	{
		nodeRescaleArrayList_ = new ArrayList<>();
		nodeArrayList_ = new ArrayList<>();

		Stage stage = gameClient.stage_;
		if (stage.isFullScreen())
		{
			FullScreenCheckBox.setSelected(true);
		}
		Scene scene = stage.getScene();

		nodeRescaleArrayList_.add(scene.lookup("#SoundSlider"));
		nodeRescaleArrayList_.add(scene.lookup("#FullScreenCheckBox"));
		nodeRescaleArrayList_.add(scene.lookup("#Resolution"));
		nodeRescaleArrayList_.add(scene.lookup("#Theme"));
		nodeRescaleArrayList_.add(scene.lookup("#label"));
		nodeRescaleArrayList_.add(scene.lookup("#SoundLabel"));
		nodeRescaleArrayList_.add(scene.lookup("#BackButton"));
		nodeRescaleArrayList_.add(scene.lookup("#picture"));

		picture = (ImageView) scene.lookup("#picture");
		Image image = Cards.getSpecialImage(SpecialTexture.BackLowsat);
		picture.setImage(image);

		PaneResizer.resizePane(scene.getHeight(), true);

/*
		PauseTransition pause = new PauseTransition(Duration.millis(10));
 		pause.setOnFinished
				(
						pauseFinishedEvent -> resize(scene.getHeight(), true)
				);
		pause.play();
*/
	}

/*
	@Override
 	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();

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

		Content = (Pane) scene.lookup("#Content");
		Content.setPrefWidth(sceneWidth/3.0);
		Content.setPrefHeight(sceneHeight);

		Settings = (Pane) scene.lookup("#Settings");
		Settings.setPrefWidth(sceneWidth);
		Settings.setPrefHeight(sceneHeight);

		if (nodeRescaleArrayList_ != null) PaneResizer.resizeNodeList(sceneWidth, sceneHeight, nodeRescaleArrayList_, true);

		if (nodeArrayList_ != null) PaneResizer.resizeNodeList(sceneWidth, sceneHeight, nodeArrayList_, false);

		PaneResizer.oldSceneWidth = sceneWidth;
		PaneResizer.oldSceneHeight = sceneHeight;
	}
*/

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
		if(gameClient.stage_.isFullScreen())
		{
			//FIXME: doesn't really work like i thought it would, leaving this empty so it cannot be resized for now in fullscreen
			System.out.println("Left empty resolution change while fullscreen is activated");
		}
		else
		{
			if(event.getSource().equals(res_1))		// If event source (selected button of resolution changer) is res_1 (600 x 400) do following
			{
				gameClient.stage_.setWidth(600.0);
				gameClient.stage_.setHeight(400.0);
			}
			else if (event.getSource().equals(res_2))	// If event source (selected button of resolution changer) is res_2 (1200 x 800) do following
			{
				gameClient.stage_.setWidth(1200.0);
				gameClient.stage_.setHeight(800.0);
			}
			gameClient.stage_.centerOnScreen();
		}
	}

	public void changeStyle(ActionEvent event)
	{
		if(event.getSource().equals(ThemeBlue_))		// If event source (selected button of resolution changer) is res_1 (600 x 400) do following
		{
			CSSController.changeTheme("ThemeBlue");
		}
		else if (event.getSource().equals(ThemeRed_))	// If event source (selected button of resolution changer) is res_2 (1200 x 800) do following
		{
			CSSController.changeTheme("ThemeRed");
		}
		else
		{
			CSSController.changeTheme("ThemeBlue");
		}
	}
}