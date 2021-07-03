package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class LoadingScreenController implements ControllerInterface
{

	@FXML
	private Pane Loading;

	private static ArrayList<Node> nodeRescaleArrayList_;
	private static ArrayList<Node> nodeArrayList_;

	@Override
	public void init ()
	{
		nodeRescaleArrayList_ = new ArrayList<>();
		nodeArrayList_ = new ArrayList<>();

		Stage stage = gameClient.stage_;
		Scene scene = stage.getScene();

		nodeRescaleArrayList_.add(scene.lookup("#LoadingNextButton"));
		nodeRescaleArrayList_.add(scene.lookup("#LoadingTitle"));
		nodeRescaleArrayList_.add(scene.lookup("#LoadingBar"));

		NodeResizer.oldSceneHeight = 400.0;
		NodeResizer.oldSceneWidth = 600.0;

		PauseTransition pause = new PauseTransition(Duration.millis(100));
		pause.setOnFinished
				(
						pauseFinishedEvent -> resize(scene.getHeight(), true)

				);
		pause.play();
	}

	@Override
	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();

		double sceneWidth = scene.getWidth();
		double sceneHeight = scene.getHeight();

		if (isHeight) {
			sceneHeight = (double) newValue;
		}
		else
		{
			sceneWidth = (double) newValue;
		}

		if (sceneHeight <= 0.0 || sceneWidth <= 0.0)
		{
			return;
		}
		Loading = (Pane) scene.lookup("#Loading");
		Loading.setPrefWidth(sceneWidth);
		Loading.setPrefHeight(sceneHeight);

		if (nodeRescaleArrayList_ != null) NodeResizer.resizeNodeList(sceneWidth, sceneHeight, nodeRescaleArrayList_, true);

		if (nodeArrayList_ != null) NodeResizer.resizeNodeList(sceneWidth, sceneHeight, nodeArrayList_, false);

		NodeResizer.oldSceneWidth = sceneWidth;
		NodeResizer.oldSceneHeight = sceneHeight;
	}

	@FXML
	private void goToHome()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}
}
