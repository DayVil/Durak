package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * gameClient
 */
public class gameClient extends Application
{
	
	protected static Stage stage_;
	
    public static void main(String[] args)
	{
        launch(args);
    }

    @Override
    public void start(Stage stage)
	{
		/* TODO add setters and getters for stage_ access */
		gameClient.stage_ = stage;
		stage_.setResizable(true);
		stage_.setMinWidth(600.0);
		stage_.setMinHeight(400.0);
		stage_.setTitle("Cardgame");

		try
		{
			Scene MainPage = new Scene(loadMainPane());
			stage_.setScene(MainPage);
			// TODO Migrate Listener into seperate function and add calls to the separate controllers
			ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
			{
				System.out.println("Height: " +  stage_.getHeight() + " Width: " +  stage_.getWidth());

				Scene scene = stage_.getScene();
				Pane startupPane = (Pane) scene.lookup("#Startup");
				if (startupPane != null)
				{
					// Needed when porting to controllerPane startupPane = (Pane) scene.lookup("#Startup");
					startupPane.setPrefWidth(stage_.getWidth());
					startupPane.setPrefHeight(stage_.getHeight());

					/* needed for childs of pane in controller
					double startupPaneWidth = startupPane.getWidth();
					double startupPaneHeight = startupPane.getHeight();
					startupPane.setLayoutX((stage_.getWidth() - startupPaneWidth) / 2.0);
					startupPane.setLayoutY(stage_.getHeight() * 0.95 - startupPaneHeight);*/
				}
			};
			stage_.widthProperty().addListener(stageSizeListener);
			stage_.heightProperty().addListener(stageSizeListener);


			stage_.show();
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
    }
    
	public Pane loadMainPane() throws IOException
	{
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
    	Pane mainPane = loader.load();
    	MainController mainController = loader.getController();
    	fxmlNavigator.setMainController(mainController);
    	fxmlNavigator.loadFxml(fxmlNavigator.STARTUP);
    	return mainPane;
    }
	
	public static void setScene(Scene scene)
	{
		stage_.setScene(scene);
	}


}
