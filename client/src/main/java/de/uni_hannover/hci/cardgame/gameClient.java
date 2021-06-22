package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.Controller.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * gameClient
 */
public class gameClient extends Application
{
	
	public static Stage stage_;

	private void stageResizable(Boolean resizable)
	{
		stage_.setResizable(resizable);
	}

	private void stageMinWidth(double width)
	{
		stage_.setMinWidth(width);
	}

	private void stageMinHeight(double height)
	{
		stage_.setMinHeight(height);
	}

	public void stageTitle(String title)
	{
		stage_.setTitle(title);
	}
	
    public static void main(String[] args)
	{
        launch(args);
    }

    @Override
    public void start(Stage stage)
	{
		gameClient.stage_ = stage;
		stageResizable(true);
		stageMinWidth(600.0);
		stageMinHeight(400.0);
		stageTitle("Cardgame");
		stage_.getIcons().add(new Image("textures/game_symbol.png", 100, 100, true, true, false));


		Scene MainPage = new Scene(loadMainPane());
		setScene(MainPage);
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
		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> changeSize();

		// TODO: get this to work, listener itself is working, resizing not due to scene not maximizing to stage width and height
		ChangeListener<? super Boolean> maximizeListener = (observable, oldValue, newValue) -> changeSize();

		stage_.widthProperty().addListener(stageSizeListener);
		stage_.heightProperty().addListener(stageSizeListener);
		stage_.maximizedProperty().addListener(maximizeListener);
		// If we don't use a key pressed (like Minecraft F11) this is not needed
		// stage_.fullScreenProperty().addListener(maximizeListener);
	}

	private void changeSize()
	{
		// Added for debugging the maximizeListener
		// System.out.println("Stage Height: " + stage_.getHeight() + " Stage Width: " + stage_.getWidth());
		// System.out.println("Scene Height: " + stage_.getScene().getHeight() + " Scene Width: " + stage_.getScene().getWidth());
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
			}
			else if (loadingPane != null)
			{
				loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.LOADING));
				loader.load();
			}
			else if (homePane != null)
			{
				loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.HOME));
				loader.load();
			}
			else if (settingsPane != null)
			{
				loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.SETTINGS));
				loader.load();
			}
			else if (gamePane != null)
			{
				loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.GAME));
				loader.load();
			}
			ControllerInterface controller = loader.getController();
			controller.resize(stage_);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
