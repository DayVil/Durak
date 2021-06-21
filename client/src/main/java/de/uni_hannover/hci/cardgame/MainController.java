package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class MainController
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
			if (fxml.equals(fxmlNavigator.LOADING))
			{
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
				loader.load();
				LoadingScreenController Controller = loader.getController();

				Controller.init();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}



	}
}
