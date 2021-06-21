package de.uni_hannover.hci.cardgame;

import java.io.IOException;

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

public class SettingsController
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
	public void goToHome(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	@FXML
	public void ChangeResolution(ActionEvent event) throws IOException
	{
		if(event.getSource().equals(res_1))
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
	    	Pane mainPane = loader.load();
	    	MainController mainController = loader.getController();
	    	System.out.println(mainController);
	    	fxmlNavigator.setMainController(mainController);
	    	fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	    	Scene MainPage = new Scene(mainPane, 600, 400);
            gameClient.setScene(MainPage);
		}
		else if (event.getSource().equals(res_2))
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
	    	Pane mainPane = loader.load();
	    	MainController mainController = loader.getController();
	    	System.out.println(mainController);
	    	fxmlNavigator.setMainController(mainController);
	    	fxmlNavigator.loadFxml(fxmlNavigator.SETTINGS);
	    	Scene MainPage = new Scene(mainPane, 1200, 800);
            gameClient.setScene(MainPage);
		}
	}

	public void resize(Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Settings = (Pane) scene.lookup("#Settings");
		Settings.setPrefWidth(stage.getWidth());
		Settings.setPrefHeight(stage.getHeight());

		Resolution = (SplitMenuButton) scene.lookup("#Resolution");
		double resolutionWidth = Resolution.getWidth();
		double resolutionHeight = Resolution.getHeight();
		Resolution.setLayoutX((Settings.getWidth() - resolutionWidth) / 10.0);
		Resolution.setLayoutY((Settings.getHeight() - resolutionHeight) / 4.0);

		label = (Label) scene.lookup("#label");
		double labelWidth = label.getWidth();
		double labelHeight = label.getHeight();
		label.setLayoutX((Settings.getWidth() - labelWidth) / 2.0);
		label.setLayoutY((Settings.getHeight() - labelHeight) / 10.0);

		HomeButton = (Button) scene.lookup("#HomeButton");
		double homeButtonWidth = HomeButton.getWidth();
		double homeButtonHeight = HomeButton.getHeight();
		HomeButton.setLayoutX((Settings.getWidth() - homeButtonWidth) / 10.0);
		HomeButton.setLayoutY((Settings.getHeight() - homeButtonHeight) / 1.4);

		picture = (ImageView) scene.lookup("#picture");
		double pictureWidth = picture.getFitWidth();
		double pictureHeight = picture.getFitHeight();
		picture.setLayoutX((Settings.getWidth() - pictureWidth) / 1.1);
		picture.setLayoutY((Settings.getHeight() - pictureHeight) / 2.0);
	}
}