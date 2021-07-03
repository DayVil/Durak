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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class StartupScreenController implements ControllerInterface
{

	@FXML
	private Pane Startup;

	private static ArrayList<Node> nodeRescaleArrayList_;
	private static ArrayList<Node> nodeArrayList_;

	@Override
	public void init()
	{
		nodeRescaleArrayList_ = new ArrayList<>();
		nodeArrayList_ = new ArrayList<>();

		NodeResizer.oldSceneHeight = 400.0;
		NodeResizer.oldSceneWidth = 600.0;

		Stage stage = gameClient.stage_;
		Scene scene = stage.getScene();

		nodeRescaleArrayList_.add(scene.lookup("#StartupTitle"));
		nodeRescaleArrayList_.add(scene.lookup("#StartupSubTitle"));
		nodeRescaleArrayList_.add(scene.lookup("#StartupPressX"));
		nodeRescaleArrayList_.add(scene.lookup("#StartupContinueButton"));

		ImageView iv1 = new ImageView();
		Image image = new Image("/textures/cards/diamonds/ace_of_diamonds.png", 200, 10000, true, true);
		iv1.setImage(image);
		iv1.setCache(true);
		iv1.setLayoutX(205.0);
		iv1.setLayoutY(150.0);
		iv1.setPreserveRatio(true);
		iv1.setFitWidth(75);
		iv1.setRotate(330.0);
		Startup.getChildren().add(iv1);
		nodeRescaleArrayList_.add(iv1);

		ImageView iv2 = new ImageView();
		image = new Image("/textures/cards/hearts/ace_of_hearts.png", 200, 10000, true, true);
		iv2.setImage(image);
		iv2.setCache(true);
		iv2.setLayoutX(245.0);
		iv2.setLayoutY(135.0);
		iv2.setPreserveRatio(true);
		iv2.setFitWidth(75);
		iv2.setRotate(350.0);
		Startup.getChildren().add(iv2);
		nodeRescaleArrayList_.add(iv2);

		ImageView iv3 = new ImageView();
		image = new Image("/textures/cards/clubs/ace_of_clubs.png", 200, 10000, true, true);
		iv3.setImage(image);
		iv3.setCache(true);
		iv3.setLayoutX(285.0);
		iv3.setLayoutY(135.0);
		iv3.setPreserveRatio(true);
		iv3.setFitWidth(75);
		iv3.setRotate(10.0);
		Startup.getChildren().add(iv3);
		nodeRescaleArrayList_.add(iv3);

		ImageView iv4 = new ImageView();
		image = new Image("/textures/cards/spades/ace_of_spades.png", 200, 10000, true, true);
		iv4.setImage(image);
		iv4.setCache(true);
		iv4.setLayoutX(325.0);
		iv4.setLayoutY(150.0);
		iv4.setPreserveRatio(true);
		iv4.setFitWidth(75);
		iv4.setRotate(30.0);
		Startup.getChildren().add(iv4);
		nodeRescaleArrayList_.add(iv4);

/*
		PauseTransition pause = new PauseTransition(Duration.millis(100));
 		pause.setOnFinished
				(
						pauseFinishedEvent -> resize(scene.getHeight(), true)
				);
		pause.play();
*/
	}

	@Override
	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
		if (stage.getScene() != null)
		{
			// The Pane of the Scene, that has got everything
			Scene scene = stage.getScene();

			double sceneWidth = scene.getWidth();
			double sceneHeight = scene.getHeight();

			if (isHeight)
			{
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

			double newFactor = sceneWidth / gameClient.stageMinWidth_;
			if (newFactor > sceneHeight / gameClient.stageMinHeight_)
			{
				newFactor = sceneHeight / gameClient.stageMinHeight_;
			}

			Startup = (Pane) scene.lookup("#Startup");
/*
			Startup.setScaleX(newFactor);
			Startup.setScaleY(newFactor);
			Startup.setTranslateX(60.0);
			Startup.setTranslateY(60.0);
*/

			Startup.setPrefWidth(sceneWidth);
 			Startup.setPrefHeight(sceneHeight);

			if (nodeRescaleArrayList_ != null) NodeResizer.resizeNodeList(sceneWidth, sceneHeight, nodeRescaleArrayList_, true);
			if (nodeArrayList_ != null) NodeResizer.resizeNodeList(sceneWidth, sceneHeight, nodeArrayList_, false);

			NodeResizer.oldSceneWidth = sceneWidth;
			NodeResizer.oldSceneHeight = sceneHeight;
		}
		else
		{
			System.out.println("Scene was null");
		}
	}

	@FXML
	private void goToLoading()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOADING);
	}
}
