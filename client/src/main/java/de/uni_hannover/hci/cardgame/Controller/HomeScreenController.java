package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.PaneResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeScreenController implements ControllerInterface
{
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
		Stage stage = gameClient.stage_;
		Scene scene = stage.getScene();
		PaneResizer.resizePane(scene.getHeight(), true);
	}
}
