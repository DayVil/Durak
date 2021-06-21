package de.uni_hannover.hci.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadingScreenController
{
	
	@FXML
	private Pane Loading;
	
	@FXML
	private ProgressBar LoadingBar;

	@FXML
	private Text LoadingTitle;

	@FXML
	private Button LoadingNextButton;
	

	@FXML
	public void goToHome(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	public void resize(Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Loading = (Pane) scene.lookup("#Loading");
		double sceneWidth = stage.getWidth() - 16;
		double sceneHeight = stage.getHeight() - 39;
		Loading.setPrefWidth(sceneWidth);
		Loading.setPrefHeight(sceneHeight);


		// The progressbar, that has not yet been correctly implemented
		LoadingBar = (ProgressBar) scene.lookup("#LoadingBar");
		double loadingBarWidth = sceneWidth * 0.99;
		double loadingBarHeight = sceneHeight * 0.05;
		LoadingBar.setPrefWidth(loadingBarWidth);
		LoadingBar.setPrefHeight(loadingBarHeight);
		LoadingBar.setLayoutX((sceneWidth - loadingBarWidth) / 2.0);
		LoadingBar.setLayoutY((sceneHeight - loadingBarHeight) / 2.0);

		// The text that is hovering above the loading bar
		LoadingTitle = (Text) scene.lookup("#LoadingTitle");
		double loadingTitleWidth = LoadingTitle.getBoundsInLocal().getWidth();
		LoadingTitle.setLayoutX((sceneWidth - loadingTitleWidth) / 2.0);
		LoadingTitle.setLayoutY(((sceneHeight - loadingBarHeight) / 2.0) - loadingBarHeight);

		// The button that will bring the user to the next pane
		LoadingNextButton = (Button) scene.lookup("#LoadingNextButton");
		double loadingNextButtonWidth = 550;
		double loadingNextButtonHeight = 120;
		LoadingNextButton.setPrefWidth(loadingNextButtonWidth);
		LoadingNextButton.setPrefHeight(loadingNextButtonHeight);
		LoadingNextButton.setLayoutX((sceneWidth - loadingNextButtonWidth) / 2.0);
		LoadingNextButton.setLayoutY((sceneHeight - loadingNextButtonHeight) / 1.2);
	}

	public void init ()
	{
		Stage stage = gameClient.stage_;
		resize(stage);
	}
}
