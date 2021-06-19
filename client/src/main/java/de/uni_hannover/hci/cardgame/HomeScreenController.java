package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeScreenController {
	
	@FXML
	private Label label;

	@FXML
	public void goToGame(ActionEvent event) throws IOException {
		fxmlNavigator.loadFxml(fxmlNavigator.GAME);
	}
	
	@FXML 
	public void goToSettings(ActionEvent event) throws IOException {
		fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	}
}
