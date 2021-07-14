package de.uni_hannover.hci.cardgame;

import de.uni_hannover.hci.cardgame.Controller.MainController;

/**
 * This class is The giver of all navigation that will be used to change the overlay/gui dynamically
 *
 * @author Yann Bernhard &lt;yann.bernhard@stud.uni-hannover.de&gt;
 * @author Sebastian Kiel &lt;sebastian.kiel@stud.uni-hannover.de&gt;
 * @author Patrick Schewe &lt;p.schewe@stud.uni-hannover.de&gt;
 * @author Robert Witteck &lt;robert.witteck@stud.uni-hannover.de&gt;
 */

public class fxmlNavigator
{
    public static final String MAIN = "Main.fxml";
    public static final String STARTUP = "StartupScreen.fxml";
    public static final String LOADING = "LoadingScreen.fxml";
    public static final String LOGIN = "loginScreen.fxml";
    public static final String HOME = "HomeScreen.fxml";
    public static final String GAME = "GameBoard.fxml";
    public static final String SETTINGS = "Settings.fxml";
    public static final String CREDITS = "Credits.fxml";
    private static MainController mainController_;

    public static void setMainController(MainController mainController)
    {
        fxmlNavigator.mainController_ = mainController;
    }

    public static void loadFxml(String fxml)
    {
        mainController_.setFxml(fxml);
    }
}
