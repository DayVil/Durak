package de.uni_hannover.hci.cardgame;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartupScreenController
{
	@FXML
	private Pane Startup;
	@FXML
	private Text StartupTitle;
	@FXML
	private Text CreatorTitle;
	@FXML
	private Text ContinueTitle;
	@FXML
	private Button NextPaneButton;

	@FXML
	public void goToLoading(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
	}

	public void resize (Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Pane startupPane = (Pane) scene.lookup("#Startup");
		startupPane.setPrefWidth(stage.getWidth());
		startupPane.setPrefHeight(stage.getHeight());

		// The Main Title stating the name of the game
		Text title = (Text) scene.lookup("#StartupTitle");
		double titleWidth = title.getBoundsInLocal().getWidth();
		double titleHeight = title.getBoundsInLocal().getHeight();
		title.setLayoutX((startupPane.getWidth() - titleWidth) / 2.0);
		title.setLayoutY((startupPane.getHeight() - titleHeight) / 5.0);

		// The Creator Title stating who created the game
		Text creatorTitle = (Text) scene.lookup("#CreatorTitle");
		double creatorTitleWidth = creatorTitle.getBoundsInLocal().getWidth();
		double creatorTitleHeight = creatorTitle.getBoundsInLocal().getHeight();
		creatorTitle.setLayoutX((startupPane.getWidth() - creatorTitleWidth) / 2.0);
		creatorTitle.setLayoutY(((startupPane.getHeight() - creatorTitleHeight) / 5.0) + titleHeight);

		// The Button to click to go to the next Pane				// Done before the title down below as title down below is dependant on button
		Button but = (Button) scene.lookup("#NextPaneButton");
		double butWidth = but.getBoundsInLocal().getWidth();
		double butHeight = but.getBoundsInLocal().getHeight();
		but.setLayoutX((startupPane.getWidth() - butWidth) / 2.0);
		but.setLayoutY((startupPane.getHeight() - butHeight) / 1.2);

		// The Continue Title stating what to do to continue to the next Pane
		Text continueTitle = (Text) scene.lookup("#ContinueTitle");
		double continueTitleWidth = continueTitle.getBoundsInLocal().getWidth();
		//double continueTitleHeight = continueTitle.getBoundsInLocal().getHeight();	// Unused as dependant on button height
		continueTitle.setLayoutX((startupPane.getWidth() - continueTitleWidth) / 2.0);
		continueTitle.setLayoutY(((startupPane.getHeight() - butHeight) / 1.25) );
	}
}
