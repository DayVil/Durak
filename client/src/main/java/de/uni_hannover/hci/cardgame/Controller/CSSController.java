package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.gameClient;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class CSSController
{

    public static void changeTheme()
    {
        Stage stage = gameClient.stage_;
        // The Pane of the Scene, that has got everything
        Scene scene = stage.getScene();


        String themeBlue = Objects.requireNonNull(CSSController.class.getClassLoader().getResource("styles/cardGameStyle.css")).toExternalForm();
        String themeRed = Objects.requireNonNull(CSSController.class.getClassLoader().getResource("styles/cardGameStyle_red.css")).toExternalForm();

        Pane Main = (Pane) scene.lookup("#fxmlHolder");
        Main.getStylesheets().clear();

        Main.getStylesheets().add(themeRed);

        stage.setScene(scene);
        stage.setTitle("Test");
        stage.show();
    }
}
