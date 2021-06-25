package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsController implements ControllerInterface
{

	@FXML
	private Pane Settings;

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
			//FIXME: doesnt really work like i thought it would, leaving this empty so it cannot be resized for now in fullscreen
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

		Settings = (Pane) scene.lookup("#Settings");
		Settings.setPrefWidth(sW);
		Settings.setPrefHeight(sH);

		Content = (Pane) scene.lookup("#Content");
		Content.setPrefHeight(sH);
		Content.setPrefWidth(sW/3.0);

		SoundSlider = (Slider) scene.lookup("#SoundSlider");
		NodeResizer.resizeObject(sW, sH, SoundSlider, true);

		FullScreenCheckBox = (CheckBox) scene.lookup("#FullScreenCheckBox");
		NodeResizer.resizeObject(sW, sH, FullScreenCheckBox, true);

		Resolution = (SplitMenuButton) scene.lookup("#Resolution");
		NodeResizer.resizeObject(sW, sH, Resolution, true);

		label = (Label) scene.lookup("#label");
		NodeResizer.resizeObject(sW, sH, label, true);

		SoundLabel = (Label) scene.lookup("#SoundLabel");
		NodeResizer.resizeObject(sW, sH, SoundLabel, true);

		BackButton = (Button) scene.lookup("#BackButton");
		NodeResizer.resizeObject(sW, sH, BackButton, true);

		picture = (ImageView) scene.lookup("#picture");
		NodeResizer.resizeObject(sW, sH, picture, true);

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

		picture = (ImageView) scene.lookup("#picture");
		Image image = new Image("/textures/cards/card_back_lowsat.png", 75, 200, true, true);
		picture.setImage(image);

		PauseTransition pause = new PauseTransition(Duration.millis(10));
		pause.setOnFinished
				(
						pauseFinishedEvent -> resize(scene.getHeight(), true)
				);
		pause.play();
	}
}