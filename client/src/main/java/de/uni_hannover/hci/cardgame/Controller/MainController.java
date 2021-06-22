package de.uni_hannover.hci.cardgame.Controller;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements ControllerInterface
{
	@FXML
	private Pane fxmlHolder;

	@FXML
	public void setFxml(String fxml)
	{
		try
		{
			Node debugging_helper = FXMLLoader.load(fxmlNavigator.class.getClassLoader().getResource(fxml));
			fxmlHolder.getChildren().setAll(debugging_helper);
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
			loader.load();
			ControllerInterface Controller = loader.getController();

			Controller.init();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}



	}

	@Override
	public void resize(Stage stage)
	{

	}

	@Override
	public void init()
	{
		Stage stage = gameClient.stage_;
		resize(stage);
	}
}
