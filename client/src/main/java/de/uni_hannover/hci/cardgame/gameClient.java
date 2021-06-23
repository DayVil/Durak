package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.Controller.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * gameClient
 */
public class gameClient extends Application
{
	
	public static Stage stage_;

	// for @Sebaty who added the to-do for this: this throws a IntelliJ warning as the parameter never changes
	private void stageResizable(Boolean resizable)
	{
		stage_.setResizable(resizable);
	}
	// for @Sebaty who added the to-do for this: this throws a IntelliJ warning as the parameter never changes
	private void stageMinWidth(double width)
	{
		stage_.setMinWidth(width);
	}
	// for @Sebaty who added the to-do for this: this throws a IntelliJ warning as the parameter never changes
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
//		stage_.getIcons().add(new Image("textures/game_symbol.png", 32, 32, true, true, false)); // does not work on linux version

		setScene(new Scene(loadMainPane()));
		fxmlNavigator.loadFxml(fxmlNavigator.STARTUP);
		setEventListener();

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

			//PauseTransition pause = new PauseTransition(Duration.millis(4000));
			//pause.setOnFinished
			//(
			//	pauseFinishedEvent ->
			//	{
			//		try
			//		{
			//			fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
			//		}
			//		catch (Exception e)
			//		{
			//			e.printStackTrace();
			//		}
			//	}
			//);
			//pause.play();
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

	private void setEventListener ()
	{
		Scene scene = stage_.getScene();

		scene.widthProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
			{
				changeSize(oldSceneWidth, newSceneWidth, false);
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
			{
				changeSize(oldSceneWidth, newSceneWidth, true);
			}
		});
	}

	private void changeSize(Number oldValue, Number newValue, Boolean isHeight)
	{
		Scene scene = stage_.getScene();
		String fxml = "";
		try
		{
			if (scene.lookup("#Startup") != null)
			{
				fxml = fxmlNavigator.STARTUP;
			}
			else if (scene.lookup("#Loading") != null)
			{
				fxml = fxmlNavigator.LOADING;
			}
			else if (scene.lookup("#Home") != null)
			{
				fxml = fxmlNavigator.HOME;
			}
			else if (scene.lookup("#Settings") != null)
			{
				fxml = fxmlNavigator.SETTINGS;
			}
			else if (scene.lookup("#GameBoard") != null)
			{
				fxml = fxmlNavigator.GAME;
			}

			if(!(fxml.equals("")))
			{
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
				loader.load();
				ControllerInterface controller = loader.getController();
				controller.resize(newValue, isHeight);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
