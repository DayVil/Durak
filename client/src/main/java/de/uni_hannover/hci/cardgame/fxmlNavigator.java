package de.uni_hannover.hci.cardgame;

import java.io.IOException;

/**
 * This class is The giver of all navigations that will be used to change the overlay/gui dynamically
 * @author catgrilll
 *
 */
public class fxmlNavigator {

	public static final String MAIN = "Main.fxml";
	public static final String STARTUP = "StartupScreen.fxml";
	public static final String LOADING = "LoadingScreen.fxml";
	public static final String HOME = "HomeScreen.fxml";
	public static final String GAME = "GameBoard.fxml";
	public static final String SETTINGS = "Settings.fxml";
	private static MainController mainController_;
	
	public static void setMainController(MainController mainController)
	{
		fxmlNavigator.mainController_ = mainController;
	}
	
	public static void loadFxml(String fxml) throws IOException
	{
		mainController_.setFxml(fxml);
	}
}
