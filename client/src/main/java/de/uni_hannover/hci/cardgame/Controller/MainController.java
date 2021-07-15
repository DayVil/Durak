package de.uni_hannover.hci.cardgame.Controller;

import java.io.IOException;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;


/**
 * The main controller starting the main stage
 *
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */

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
	public void init()
	{

	}
}
