package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SettingsController
{
	
	@FXML
	private StackPane Settings;
	
	@FXML
	private SplitMenuButton Resolution;
	
	@FXML
	private MenuItem res_1;
	
	@FXML
	private MenuItem res_2;

	@FXML
	private Label label;
	
	@FXML
	public void goToHome(ActionEvent event) throws IOException
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
}