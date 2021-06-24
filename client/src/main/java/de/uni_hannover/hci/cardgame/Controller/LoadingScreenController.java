package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreenController implements ControllerInterface
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
	private void goToHome()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	@Override
	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();

		double sW = scene.getWidth();
		double sH = scene.getHeight();

		if (isHeight) {
			sH = (double) newValue;
		}
		else
		{
			sW = (double) newValue;
		}

		if (sH <= 0.0 || sW <= 0.0)
		{
			return;
		}
		Loading = (Pane) scene.lookup("#Loading");
		Loading.setPrefWidth(sW);
		Loading.setPrefHeight(sH);

		// The text that is hovering above the loading bar
		LoadingTitle = (Text) scene.lookup("#LoadingTitle");
		NodeResizer.resizeObject(sW, sH, LoadingTitle, true);

		// The button that will bring the user to the next pane
		LoadingNextButton = (Button) scene.lookup("#LoadingNextButton");
		NodeResizer.resizeObject(sW, sH, LoadingNextButton, true);

		// The progressbar, that has not yet been correctly implemented
		LoadingBar = (ProgressBar) scene.lookup("#LoadingBar");
		NodeResizer.resizeObject(sW, sH, LoadingBar, true);

		NodeResizer.originalSceneWidth = sW;
		NodeResizer.originalSceneHeight = sH;
	}

	@Override
	public void init ()
	{
		NodeResizer.originalSceneHeight = 400.0;
		NodeResizer.originalSceneWidth = 600.0;
		Stage stage = gameClient.stage_;
		Scene scene = stage.getScene();

		PauseTransition pause = new PauseTransition(Duration.millis(10));
		pause.setOnFinished
				(
						pauseFinishedEvent -> resize(scene.getHeight(), true)

				);
		pause.play();
	}
}
