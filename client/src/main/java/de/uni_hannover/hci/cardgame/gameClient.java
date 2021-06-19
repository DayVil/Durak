package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * gameClient
 */
public class gameClient extends Application {
	
	protected static Stage stage_;
	protected String javaVersion_;
	protected String javafxVersion_;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
    	try {
    		this.javaVersion_ = System.getProperty("java.version");
            this.javafxVersion_ = System.getProperty("javafx.version");
            gameClient.stage_ = stage;
            stage_.setResizable(false);
            stage_.setTitle("Cardgame");
            Scene MainPage = new Scene(loadMainPane());
            stage_.setScene(MainPage);
            stage_.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        
    }
    
	public VBox loadMainPane() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlNavigator.MAIN));
    	VBox mainPane = (VBox)loader.load();
    	MainController mainController = loader.getController();
    	System.out.println(mainController);
    	fxmlNavigator.setMainController(mainController);
    	fxmlNavigator.loadFxml(fxmlNavigator.STARTUP);
    	return mainPane;
    }
	
	public static void setScene(Scene scene) {
		stage_.setScene(scene);
	}
}
