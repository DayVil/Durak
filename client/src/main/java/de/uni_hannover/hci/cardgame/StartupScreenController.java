package de.uni_hannover.hci.cardgame;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartupScreenController
{
	@FXML
	public Pane Startup;
	public Text StartupTitle;

	@FXML
	public void goToLoading(ActionEvent event) throws IOException
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
	}

	public void resize (Stage stage)
	{
		// PANE
		Scene scene = stage.getScene();
		Pane startupPane = (Pane) scene.lookup("#Startup");
		startupPane.setPrefWidth(stage.getWidth());
		startupPane.setPrefHeight(stage.getHeight());

		// TITLE
		Text title = (Text) scene.lookup("#StartupTitle");
		double titleWidth = title.getBoundsInLocal().getWidth();
		double titleHeight = title.getBoundsInLocal().getHeight();
		title.setLayoutX((startupPane.getWidth() - titleWidth) / 2.0);
		title.setLayoutY((startupPane.getHeight() - titleHeight) / 5.0);
	}
}
