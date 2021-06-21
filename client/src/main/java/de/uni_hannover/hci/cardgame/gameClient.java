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


		Scene MainPage = new Scene(loadMainPane());
		stage_.setScene(MainPage);
		sizeChangeListener();
		stage_.show();

    }
    
	public Pane loadMainPane()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
			Pane mainPane = loader.load();
			MainController mainController = loader.getController();
			fxmlNavigator.setMainController(mainController);
			fxmlNavigator.loadFxml(fxmlNavigator.STARTUP);
			return mainPane;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

    	return new Pane();
    }
	
	public static void setScene(Scene scene)
	{
		stage_.setScene(scene);
	}

	private void sizeChangeListener ()
	{
		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
		{
			System.out.println("Height: " +  stage_.getHeight() + " Width: " +  stage_.getWidth());

			Scene scene = stage_.getScene();
			Pane startupPane = (Pane) scene.lookup("#Startup");
			Pane loadingPane = (Pane) scene.lookup("#Loading");
			Pane homePane = (Pane) scene.lookup("#Home");
			Pane settingsPane = (Pane) scene.lookup("#Settings");
			Pane gamePane = (Pane) scene.lookup("#GameBoard");
			FXMLLoader loader = null;
			try
			{
				if (startupPane != null)
				{
					loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.STARTUP));
					loader.load();
					StartupScreenController controller = loader.getController();
					controller.resize(stage_);
				}
				else if (loadingPane != null)
				{
					loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.LOADING));
					loader.load();
					LoadingScreenController controller = loader.getController();
					controller.resize(stage_);
				}
				else if (homePane != null)
				{
					loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.HOME));
					loader.load();
					HomeScreenController controller = loader.getController();
					controller.resize(stage_);
				}
				else if (settingsPane != null)
				{
					loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.SETTINGS));
					loader.load();
					SettingsController controller = loader.getController();
					controller.resize(stage_);
				}
				else if (gamePane != null)
				{
					loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.GAME));
					loader.load();
					GameBoardController controller = loader.getController();
					controller.resize(stage_);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		};
		stage_.widthProperty().addListener(stageSizeListener);
		stage_.heightProperty().addListener(stageSizeListener);
	}
}
