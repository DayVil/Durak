package de.uni_hannover.hci.cardgame.Controller;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class MainController implements ControllerInterface
{
	@FXML
	private Pane fxmlHolder;

	@FXML
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

	}

	@Override
	public void init()
	{

	}
}
