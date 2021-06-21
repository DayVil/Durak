package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartupScreenController implements ControllerInterface
{
	@FXML
	private Pane Startup;
	@FXML
	private Text StartupTitle;
	@FXML
	private Text StartupSubTitle;
	@FXML
	private Text StartupPressX;
	@FXML
	private Button StartupContinueButton;

	@FXML
	private void goToLoading(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
	}

	@Override
	public void resize (Stage stage)
	{
		if (stage.getScene() != null)
		{
			// The Pane of the Scene, that has got everything
			Scene scene = stage.getScene();
			Startup = (Pane) scene.lookup("#Startup");

			Startup.setPrefWidth(stage.getWidth());
			Startup.setPrefHeight(stage.getHeight());

			// The Main Title stating the name of the game
			StartupTitle = (Text) scene.lookup("#StartupTitle");
			double titleWidth = StartupTitle.getBoundsInLocal().getWidth();
			double titleHeight = StartupTitle.getBoundsInLocal().getHeight();
			StartupTitle.setLayoutX((Startup.getWidth() - titleWidth) / 2.0);
			StartupTitle.setLayoutY((Startup.getHeight() - titleHeight) / 5.0);

			// The Creator Title stating who created the game
			StartupSubTitle = (Text) scene.lookup("#StartupSubTitle");
			double subTitleWidth = StartupSubTitle.getBoundsInLocal().getWidth();
			double subTitleHeight = StartupSubTitle.getBoundsInLocal().getHeight();
			StartupSubTitle.setLayoutX((Startup.getWidth() - subTitleWidth) / 2.0);
			StartupSubTitle.setLayoutY(((Startup.getHeight() - subTitleHeight) / 5.0) + titleHeight);

			// The Button to click to go to the next Pane				// Done before the StartupTitle down below as StartupTitle down below is dependant on button
			StartupContinueButton = (Button) scene.lookup("#StartupContinueButton");
			double ContinueButtonWidth = StartupContinueButton.getWidth();
			double ContinueButtonHeight = StartupContinueButton.getHeight();
			StartupContinueButton.setLayoutX((Startup.getWidth() - ContinueButtonWidth) / 2.0);
			StartupContinueButton.setLayoutY((Startup.getHeight() - ContinueButtonHeight) / 1.2);

			// The Continue Title stating what to do to continue to the next Pane
			StartupPressX = (Text) scene.lookup("#StartupPressX");
			double continueMessageWidth = StartupPressX.getBoundsInLocal().getWidth();
			//double continueMessageHeight = continueMessage.getBoundsInLocal().getHeight();	// Unused as dependant on button height
			StartupPressX.setLayoutX((Startup.getWidth() - continueMessageWidth) / 2.0);
			StartupPressX.setLayoutY(((Startup.getHeight() - ContinueButtonHeight) / 1.25) );
		}
	}

	@Override
	public void init()
	{
		Stage stage = gameClient.stage_;
		resize(stage);
	}
}
