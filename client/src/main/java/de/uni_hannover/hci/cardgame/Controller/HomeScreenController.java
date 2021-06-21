package de.uni_hannover.hci.cardgame.Controller;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
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
	private void goToGame(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.GAME);
	}
	
	@FXML 
	private void goToSettings(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	}

	@Override
	public void resize(Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Home = (Pane) scene.lookup("#Home");
		Home.setPrefWidth(scene.getWidth());
		Home.setPrefHeight(scene.getHeight());

		// The button that will bring the user to the next pane
		SettingsButton = (Button) scene.lookup("#SettingsButton");
		double SettingsButtonWidth = 75;
		double SettingsButtonHeight = 25;
		SettingsButton.setPrefWidth(SettingsButtonWidth);
		SettingsButton.setPrefHeight(SettingsButtonHeight);
		SettingsButton.setLayoutX((scene.getWidth() - SettingsButtonWidth) / 1.05);
		SettingsButton.setLayoutY(scene.getHeight() - ((scene.getHeight() - SettingsButtonHeight)));

		// The button that will bring the user to the next pane
		GameBoardButton = (Button) scene.lookup("#GameBoardButton");
		double GameBoardButtonWidth = 100;
		double GameBoardButtonHeight = 25;
		GameBoardButton.setPrefWidth(GameBoardButtonWidth);
		GameBoardButton.setPrefHeight(GameBoardButtonHeight);
		GameBoardButton.setLayoutX((scene.getWidth() - GameBoardButtonWidth) / 2.0);
		GameBoardButton.setLayoutY((scene.getHeight() - GameBoardButtonHeight) / 1.2);

		label = (Label) scene.lookup("#label");
		double labelWidth = 275;
		double labelHeight = 55;
		label.setPrefWidth(labelWidth);
		label.setPrefHeight(labelHeight);
		label.setLayoutX((scene.getWidth() - labelWidth) / 2.0);
		label.setLayoutY((scene.getHeight() - labelHeight) / 2.0);
	}

	@Override
	public void init()
	{
		Stage stage = gameClient.stage_;
		resize(stage);
	}
}
