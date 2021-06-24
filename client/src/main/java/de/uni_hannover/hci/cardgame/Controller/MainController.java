package de.uni_hannover.hci.cardgame.Controller;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements ControllerInterface
{
	@FXML
	private Pane fxmlHolder;

	public void setFxml(String fxml)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
			Node debugging_helper = loader.load();
			fxmlHolder.getChildren().setAll(debugging_helper);
			ControllerInterface Controller = loader.getController();

			Controller.init();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
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

		fxmlHolder = (Pane) scene.lookup("#fxmlHolder");
		fxmlHolder.setPrefWidth(sW);
		fxmlHolder.setPrefHeight(sH);

		NodeResizer.originalSceneWidth = sW;
		NodeResizer.originalSceneHeight = sH;
	}

	@Override
	public void init()
	{
		NodeResizer.originalSceneHeight = 400.0;
		NodeResizer.originalSceneWidth = 600.0;
		resize(gameClient.stage_.getScene().getHeight(), true);
	}
}
