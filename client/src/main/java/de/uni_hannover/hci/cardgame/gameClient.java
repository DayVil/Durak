package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.Controller.*;
import de.uni_hannover.hci.cardgame.Network.ClientNetwork;
import javafx.animation.PauseTransition;
import javafx.application.Application;
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
	public static final double stageMinWidth_ = 600.0;
	public static final double stageMinHeight_ = 400.0;


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
		stage_.setResizable(true);
		stage_.setMinWidth(stageMinWidth_);
		stage_.setMinHeight(stageMinHeight_);
		NodeResizer.oldSceneHeight = 400.0;
		NodeResizer.oldSceneWidth = 600.0;
		stageTitle("Cardgame");
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win"))
		{
			//This is windows os
			stage_.getIcons().add(new Image("/textures/game_symbol.png", 32, 32, true, true, false)); // does not work on linux version
		}
		else if (os.contains("osx"))
		{
			//this is apple
			stage_.getIcons().add(new Image("/textures/game_symbol.png", 32, 32, true, true, false)); // does not work on linux version
		}
		else if (os.contains("nix") || os.contains("aix") || os.contains("nux"))
		{
			//this is any linux/unix/*aix os
			System.out.println("I am working on any Unix, Linux or *AIX OS");
		}

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

			PauseTransition pause = new PauseTransition(Duration.millis(4000));
			pause.setOnFinished
			(
				pauseFinishedEvent ->
				{
					try
					{
						fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			);
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

		scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> changeSize(newSceneWidth, false));

		scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> changeSize(newSceneHeight, true));
	}

	private void changeSize(Number newValue, Boolean isHeight)
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
			else if (scene.lookup("#Login") != null)
			{
				fxml = fxmlNavigator.LOGIN;
			}
			else if (scene.lookup("#Credits") != null)
			{
				fxml = fxmlNavigator.CREDITS;
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

	public static void shutDown()
	{
		ClientNetwork.stopConnection();
		stage_.close();
		System.exit(0);
	}

	@Override
	public void stop()
	{
		//wird aufgerufen, wenn man mit dem X von der Stage das Programm beendet
		ClientNetwork.stopConnection();
		stage_.close();
	}
}
