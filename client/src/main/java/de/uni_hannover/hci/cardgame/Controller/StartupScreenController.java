package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartupScreenController implements ControllerInterface
{

	@FXML
	private Pane Startup;
	@FXML
	private Text StartupTitle;
	@FXML
	private Text StartupSubTitle;
	@FXML
	private Text StartupPressX;
	@FXML
	private Button StartupContinueButton;

	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;
	private ImageView iv4;

	@FXML
	private void goToLoading()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.LOGIN);
	}

	@Override
	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
		if (stage.getScene() != null)
		{
			// The Pane of the Scene, that has got everything
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

			Startup = (Pane) scene.lookup("#Startup");

			Startup.setPrefWidth(sW);
			Startup.setPrefHeight(sH);

			// The Main Title stating the name of the game
			StartupTitle = (Text) scene.lookup("#StartupTitle");
			NodeResizer.resizeObject(sW, sH, StartupTitle, true);

			// The Creator Title stating who created the game
			StartupSubTitle = (Text) scene.lookup("#StartupSubTitle");
			NodeResizer.resizeObject(sW, sH, StartupSubTitle, true);

			// The Button to click to go to the next Pane				// Done before the StartupTitle down below as StartupTitle down below is dependant on button
			StartupContinueButton = (Button) scene.lookup("#StartupContinueButton");
			NodeResizer.resizeObject(sW, sH, StartupContinueButton, true);

			// The Continue Title stating what to do to continue to the next Pane
			StartupPressX = (Text) scene.lookup("#StartupPressX");
			NodeResizer.resizeObject(sW, sH, StartupPressX, true);

			iv1 = (ImageView) scene.lookup("#iv1");
			NodeResizer.resizeObject(sW, sH, iv1, true);

			iv2 = (ImageView) scene.lookup("#iv2");
			NodeResizer.resizeObject(sW, sH, iv2, true);

			iv3 = (ImageView) scene.lookup("#iv3");
			NodeResizer.resizeObject(sW, sH, iv3, true);

			iv4 = (ImageView) scene.lookup("#iv4");
			NodeResizer.resizeObject(sW, sH, iv4, true);

			NodeResizer.originalSceneWidth = sW;
			NodeResizer.originalSceneHeight = sH;
		}
		else
		{
			System.out.println("Scene was null");
		}
	}

	@Override
	public void init()
	{
		NodeResizer.originalSceneHeight = 400.0;
		NodeResizer.originalSceneWidth = 600.0;
		//Stage stage = gameClient.stage_;
		//Scene scene = stage.getScene();

		iv1 = new ImageView();
		Image image = new Image("/textures/cards/diamonds/ace_of_diamonds.png", 75, 200, true, true);
		iv1.setImage(image);
		iv1.setCache(true);
		iv1.setLayoutX(150.0);
		iv1.setLayoutY(150.0);
		iv1.setRotate(330.0);
		iv1.setId("iv1");
		Startup.getChildren().add(iv1);

		iv2 = new ImageView();
		image = new Image("/textures/cards/hearts/ace_of_hearts.png", 75, 200, true, true);
		iv2.setImage(image);
		iv2.setCache(true);
		iv2.setLayoutX(190.0);
		iv2.setLayoutY(150.0);
		iv2.setRotate(350.0);
		iv2.setId("iv2");
		Startup.getChildren().add(iv2);

		iv3 = new ImageView();
		image = new Image("/textures/cards/clubs/ace_of_clubs.png", 75, 200, true, true);
		iv3.setImage(image);
		iv3.setCache(true);
		iv3.setLayoutX(230.0);
		iv3.setLayoutY(150.0);
		iv3.setRotate(10.0);
		iv3.setId("iv3");
		Startup.getChildren().add(iv3);

		iv4 = new ImageView();
		image = new Image("/textures/cards/spades/ace_of_spades.png", 75, 200, true, true);
		iv4.setImage(image);
		iv4.setCache(true);
		iv4.setLayoutX(270.0);
		iv4.setLayoutY(150.0);
		iv4.setRotate(30.0);
		iv4.setId("iv4");
		Startup.getChildren().add(iv4);
	}
}
