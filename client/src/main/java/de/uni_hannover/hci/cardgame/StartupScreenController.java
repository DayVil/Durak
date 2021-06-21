package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
public class StartupScreenController
{
	@FXML
	private Pane Startup;

	@FXML
	public void goToLoading(ActionEvent event) throws IOException
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
	}
}
