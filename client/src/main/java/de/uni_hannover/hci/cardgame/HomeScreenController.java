package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeScreenController
{

	@FXML
	private Pane Home;
	
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

	}
}
