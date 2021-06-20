package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class LoadingScreenController
{
	
	@FXML
	private StackPane LoadingScreen;
	
	@FXML
	private ProgressBar Initial_Load; 
	

	@FXML
	public void goToHome(ActionEvent event) throws IOException
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}
}
