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
		Loading.setPrefWidth(stage.getWidth());
		Loading.setPrefHeight(stage.getHeight());

		// The progressbar, that has not yet been correctly implemented
		LoadingBar = (ProgressBar) scene.lookup("#LoadingBar");
		double loadingBarWidth = LoadingBar.getBoundsInLocal().getWidth();
		double loadingBarHeight = LoadingBar.getBoundsInLocal().getHeight();
		LoadingBar.setLayoutX((Loading.getWidth() - loadingBarWidth) / 2.0);
		LoadingBar.setLayoutY((Loading.getHeight() - loadingBarHeight) / 2.0);

		// The text that is hovering above the loading bar
		LoadingTitle = (Text) scene.lookup("#LoadingTitle");
		double loadingTitleWidth = LoadingTitle.getBoundsInLocal().getWidth();
		LoadingTitle.setLayoutX((Loading.getWidth() - loadingTitleWidth) / 2.0);
		LoadingTitle.setLayoutY((Loading.getHeight() - loadingBarHeight) / 2.15);

		// The button that will bring the user to the next pane
		LoadingNextButton = (Button) scene.lookup("#LoadingNextButton");
		double LoadingNextButtonWidth = LoadingNextButton.getWidth();
		double LoadingNextButtonHeight = LoadingNextButton.getHeight();
		LoadingNextButton.setLayoutX((Loading.getWidth() - LoadingNextButtonWidth) / 2.0);
		LoadingNextButton.setLayoutY((Loading.getHeight() - LoadingNextButtonHeight) / 1.2);
	}
}
