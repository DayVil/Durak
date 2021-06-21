package de.uni_hannover.hci.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoadingScreenController
{
	
	@FXML
	private Pane Loading;
	
	@FXML
	private ProgressBar Initial_Load; 
	

	@FXML
	public void goToHome(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	public void resize(Stage stage)
	{

	}
}
