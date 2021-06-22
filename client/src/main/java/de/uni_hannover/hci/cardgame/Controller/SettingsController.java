package de.uni_hannover.hci.cardgame.Controller;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsController implements ControllerInterface
{
	
	@FXML
	private Pane Settings;
	
	@FXML
	private SplitMenuButton Resolution;
	
	@FXML
	private MenuItem res_1;
	
	@FXML
	private MenuItem res_2;

	@FXML
	private Label label;

	@FXML
	private Button HomeButton;

	@FXML
	private ImageView picture;
	
	@FXML
	private void goToHome(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	@FXML
	public void ChangeResolution(ActionEvent event) throws IOException
	{
		if(event.getSource().equals(res_1))		// If event source (selected button of resolution changer) is res_1 (600 x 400) do following
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
	    	Pane mainPane = loader.load();
	    	MainController mainController = loader.getController();
	    	fxmlNavigator.setMainController(mainController);
	    	fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	    	Scene MainPage = new Scene(mainPane, 600, 400);
            gameClient.setScene(MainPage);
		}
		else if (event.getSource().equals(res_2))	// If event source (selected button of resolution changer) is res_2 (1200 x 800) do following
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
	    	Pane mainPane = loader.load();
	    	MainController mainController = loader.getController();
	    	fxmlNavigator.setMainController(mainController);
	    	fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	    	Scene MainPage = new Scene(mainPane, 1200, 800);
            gameClient.setScene(MainPage);
		}
		gameClient.stage_.centerOnScreen();
	}

	@Override
	public void resize(Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Settings = (Pane) scene.lookup("#Settings");
		Settings.setPrefWidth(scene.getWidth());
		Settings.setPrefHeight(scene.getHeight());

		Resolution = (SplitMenuButton) scene.lookup("#Resolution");
		double resolutionWidth = 125;
		double resolutionHeight = 25;
		Resolution.setPrefWidth(resolutionWidth);
		Resolution.setPrefHeight(resolutionHeight);
		Resolution.setLayoutX((scene.getWidth() - resolutionWidth) / 10.0);
		Resolution.setLayoutY((scene.getHeight() - resolutionHeight) / 4.0);

		label = (Label) scene.lookup("#label");
		double labelWidth = 125;
		double labelHeight = 55;
		label.setPrefWidth(labelWidth);
		label.setPrefHeight(labelHeight);
		label.setLayoutX((scene.getWidth() - labelWidth) / 2.0);
		label.setLayoutY((scene.getHeight() - labelHeight) / 10.0);

		HomeButton = (Button) scene.lookup("#HomeButton");
		double homeButtonWidth = 100;
		double homeButtonHeight = 25;
		HomeButton.setPrefWidth(homeButtonWidth);
		HomeButton.setPrefHeight(homeButtonHeight);
		HomeButton.setLayoutX((scene.getWidth() - homeButtonWidth) / 10.0);
		HomeButton.setLayoutY((scene.getHeight() - homeButtonHeight) / 1.4);

		picture = (ImageView) scene.lookup("#picture");
		double pictureWidth = picture.getFitWidth();
		double pictureHeight = picture.getFitHeight();
		picture.setFitWidth(pictureWidth);
		picture.setFitHeight(pictureHeight);
		picture.setLayoutX((scene.getWidth() - pictureWidth) / 1.1);
		picture.setLayoutY((scene.getHeight() - pictureHeight) / 2.0);

	}

	@Override
	public void init()
	{
		Stage stage = gameClient.stage_;
		resize(stage);
	}
}