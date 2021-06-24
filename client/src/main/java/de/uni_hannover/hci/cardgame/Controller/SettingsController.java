package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsController implements ControllerInterface
{
	
	@FXML
	private Pane Settings;
	
	@FXML
	private SplitMenuButton Resolution;
	
	@FXML
	private MenuItem res_1;
	
	@FXML
	private MenuItem res_2;

	@FXML
	private Label label;

	@FXML
	private Button HomeButton;

	@FXML
	private ImageView picture;
	
	@FXML
	private void goToHome()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	@FXML
	public void ChangeResolution(ActionEvent event)
	{
		if(event.getSource().equals(res_1))		// If event source (selected button of resolution changer) is res_1 (600 x 400) do following
		{
			gameClient.stage_.setWidth(600);
			gameClient.stage_.setHeight(400);
		}
		else if (event.getSource().equals(res_2))	// If event source (selected button of resolution changer) is res_2 (1200 x 800) do following
		{
			gameClient.stage_.setWidth(1200);
			gameClient.stage_.setHeight(800);
		}
		gameClient.stage_.centerOnScreen();
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

		Resolution = (SplitMenuButton) scene.lookup("#Resolution");
		NodeResizer.resizeObject(sW, sH, Resolution, true);

		label = (Label) scene.lookup("#label");
		NodeResizer.resizeObject(sW, sH, label, true);

		HomeButton = (Button) scene.lookup("#HomeButton");
		NodeResizer.resizeObject(sW, sH, HomeButton, true);

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

		resize(scene.getHeight(), true);
	}
}