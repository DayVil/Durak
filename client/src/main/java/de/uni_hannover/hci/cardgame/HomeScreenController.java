package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeScreenController
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
	public void goToGame(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.GAME);
	}
	
	@FXML 
	public void goToSettings(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	}

	public void resize(Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Home = (Pane) scene.lookup("#Home");
		Home.setPrefWidth(stage.getWidth());
		Home.setPrefHeight(stage.getHeight());

		// The button that will bring the user to the next pane
		SettingsButton = (Button) scene.lookup("#SettingsButton");
		double SettingsButtonWidth = SettingsButton.getWidth();
		double SettingsButtonHeight = SettingsButton.getHeight();
		SettingsButton.setLayoutX((Home.getWidth() - SettingsButtonWidth) / 1.05);
		SettingsButton.setLayoutY(Home.getHeight() - ((Home.getHeight() - SettingsButtonHeight)));

		// The button that will bring the user to the next pane
		GameBoardButton = (Button) scene.lookup("#GameBoardButton");
		double GameBoardButtonWidth = GameBoardButton.getWidth();
		double GameBoardButtonHeight = GameBoardButton.getHeight();
		GameBoardButton.setLayoutX((Home.getWidth() - GameBoardButtonWidth) / 2.0);
		GameBoardButton.setLayoutY((Home.getHeight() - GameBoardButtonHeight) / 1.2);

		label = (Label) scene.lookup("#label");
		double labelWidth = label.getWidth();
		double labelHeight = label.getHeight();
		label.setLayoutX((Home.getWidth() - labelWidth) / 2.0);
		label.setLayoutY((Home.getHeight() - labelHeight) / 2.0);
	}
}
