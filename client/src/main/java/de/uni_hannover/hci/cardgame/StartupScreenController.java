package de.uni_hannover.hci.cardgame;

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
	public Pane Startup;
	@FXML
	public Text StartupTitle;
	@FXML
	public Text StartupSubTitle;
	@FXML
	public Text StartupPressX;
	@FXML
	public Button StartupContinueButton;

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
		Text subTitle = (Text) scene.lookup("#StartupSubTitle");
		double subTitleWidth = subTitle.getBoundsInLocal().getWidth();
		double subTitleHeight = subTitle.getBoundsInLocal().getHeight();
		subTitle.setLayoutX((startupPane.getWidth() - subTitleWidth) / 2.0);
		subTitle.setLayoutY(((startupPane.getHeight() - subTitleHeight) / 5.0) + titleHeight);

		// The Button to click to go to the next Pane				// Done before the title down below as title down below is dependant on button
		Button ContinueButton = (Button) scene.lookup("#StartupContinueButton");
		double ContinueButtonWidth = ContinueButton.getWidth();
		double ContinueButtonHeight = ContinueButton.getHeight();
		ContinueButton.setLayoutX((startupPane.getWidth() - ContinueButtonWidth) / 2.0);
		ContinueButton.setLayoutY((startupPane.getHeight() - ContinueButtonHeight) / 1.2);

		// The Continue Title stating what to do to continue to the next Pane
		Text continueMessage = (Text) scene.lookup("#StartupPressX");
		double continueMessageWidth = continueMessage.getBoundsInLocal().getWidth();
		//double continueMessageHeight = continueMessage.getBoundsInLocal().getHeight();	// Unused as dependant on button height
		continueMessage.setLayoutX((startupPane.getWidth() - continueMessageWidth) / 2.0);
		continueMessage.setLayoutY(((startupPane.getHeight() - ContinueButtonHeight) / 1.25) );
	}
}
