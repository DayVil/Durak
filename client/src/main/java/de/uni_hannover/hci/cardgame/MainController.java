package de.uni_hannover.hci.cardgame;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {
	

	@FXML
	private StackPane fxmlHolder;
	
	public void setFxml(String fxml) throws IOException {
		Node debugging_helper = FXMLLoader.load(fxmlNavigator.class.getClassLoader().getResource(fxml));
		fxmlHolder.getChildren().setAll(debugging_helper);
	}
}
