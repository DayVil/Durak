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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeScreenController implements ControllerInterface
{

	@FXML
	private Pane Home;

	@FXML
	private Button SettingsButton;

	@FXML
	private Button GameBoardButton;
	
	@FXML
	private Label label;

	@FXML
	private void goToGame()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.GAME);
	}
	
	@FXML 
	private void goToSettings()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
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

		Home = (Pane) scene.lookup("#Home");
		Home.setPrefWidth(sW);
		Home.setPrefHeight(sH);

		// The button that will bring the user to the next pane
		SettingsButton = (Button) scene.lookup("#SettingsButton");
		NodeResizer.resizeObject(sW, sH, SettingsButton, true);

		// The button that will bring the user to the next pane
		GameBoardButton = (Button) scene.lookup("#GameBoardButton");
		NodeResizer.resizeObject(sW, sH, GameBoardButton, true);

		label = (Label) scene.lookup("#label");
		NodeResizer.resizeObject(sW, sH, label, true);

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
		resize(scene.getHeight(), true);
	}
}
