package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeScreenController implements ControllerInterface
{

	@FXML
	private Pane Home;

	@FXML
	private Pane Content;

	@FXML
	private Button SettingsButton;

	@FXML
	private Button PlayButton;

	@FXML
	private Button QuitButton;

	@FXML
	private Button CreditButton;
	
	@FXML
	private Label label;
	
	@FXML 
	private void goToSettings()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	}

	@FXML
	private void goToCredits()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.CREDITS);
	}

	@FXML
	private void goToLogin()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOGIN);
	}

	@FXML
	private void shutDown()
	{

		gameClient.shutDown();
	}

	@Override
	public void init()
	{
		PaneResizer.oldSceneHeight = 400.0;
		PaneResizer.oldSceneWidth = 600.0;
		Stage stage = gameClient.stage_;
		Scene scene = stage.getScene();
		PaneResizer.resizePane(scene.getHeight(), true);

/*
		PauseTransition pause = new PauseTransition(Duration.millis(100));
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

		Content = (Pane) scene.lookup("#Content");
		Content.setPrefHeight(sH);
		Content.setPrefWidth(sW/3.0);

		SettingsButton = (Button) scene.lookup("#SettingsButton");
		PaneResizer.resizeNode(sW, sH, SettingsButton, true);

		PlayButton = (Button) scene.lookup("#PlayButton");
		PaneResizer.resizeNode(sW, sH, PlayButton, true);

		CreditButton = (Button) scene.lookup("#CreditButton");
		PaneResizer.resizeNode(sW, sH, CreditButton, true);

		QuitButton = (Button) scene.lookup("#QuitButton");
		PaneResizer.resizeNode(sW, sH, QuitButton, true);

		label = (Label) scene.lookup("#label");
		PaneResizer.resizeNode(sW, sH, label, true);

		PaneResizer.oldSceneWidth = sW;
		PaneResizer.oldSceneHeight = sH;
	}
*/
}
