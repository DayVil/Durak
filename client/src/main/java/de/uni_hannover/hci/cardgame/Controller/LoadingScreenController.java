package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.NodeResizer;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadingScreenController implements ControllerInterface
{
	
	@FXML
	private Pane Loading;
	
	@FXML
	private ProgressBar LoadingBar;

	@FXML
	private Text LoadingTitle;

	@FXML
	private Button LoadingNextButton;

	/*double progressBarValue = 0.0;
	String[] 	ImageNameList = {"/textures/cards/diamonds/ten_of_diamonds.png", "/textures/cards/clubs/ace_of_clubs.png", "/textures/cards/clubs/six_of_clubs.png", "/textures/cards/hearts/nine_of_hearts.png"};
	int    		ImageNr = 0;*/
	

	@FXML
	private void goToHome()
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	@Override
	public void resize (Number newValue, Boolean isHeight)
	{
		Stage stage = gameClient.stage_;
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
		Loading = (Pane) scene.lookup("#Loading");
		Loading.setPrefWidth(sW);
		Loading.setPrefHeight(sH);


		// The progressbar, that has not yet been correctly implemented
		LoadingBar = (ProgressBar) scene.lookup("#LoadingBar");
		NodeResizer.resizeObject(sW, sH, LoadingBar, true);

		// The text that is hovering above the loading bar
		LoadingTitle = (Text) scene.lookup("#LoadingTitle");
		NodeResizer.resizeObject(sW, sH, LoadingTitle, true);

		// The button that will bring the user to the next pane
		LoadingNextButton = (Button) scene.lookup("#LoadingNextButton");
		NodeResizer.resizeObject(sW, sH, LoadingNextButton, true);

		NodeResizer.originalSceneWidth = sW;
		NodeResizer.originalSceneHeight = sH;
	}

	@Override
	public void init ()
	{
		NodeResizer.originalSceneHeight = 400.0;
		NodeResizer.originalSceneWidth = 600.0;
		Stage stage = gameClient.stage_;

		resize(stage.getScene().getHeight(), true);

		/*PauseTransition pause = new PauseTransition(Duration.millis(500));
		pause.setOnFinished
		(
			e ->
			{
				if (ImageNr < ImageNameList.length)
				{
					System.out.printf("Nach Pause Bild %d%n", ImageNr);

					Image i = new Image(ImageNameList[ImageNr], sW / 10.0, sH, true, true);
					ImageView iv = new ImageView();
					iv.setImage(i);
					iv.setCache(true);
					iv.setLayoutX(sW * 0.1 + sW * 0.05 * ImageNr);
					iv.setLayoutY(sH * 0.6);
					Loading.getChildren().add(iv);
					ImageNr++;
					progressBarValue = progressBarValue + 1.0 * ImageNr / ImageNameList.length;
				}
				else
				{
					System.out.printf("Pause %.3f%n", progressBarValue);
					progressBarValue = progressBarValue + 0.1;
				}

				if (ImageNr ==  ImageNameList.length)
				{
					System.out.printf("Nun nur Pause %.3f%n", progressBarValue);
					ImageNr++;
					progressBarValue = 0.0;
				}

				LoadingBar.setProgress(progressBarValue);

				if (ImageNr < ImageNameList.length || progressBarValue < 1.0)    pause.playFromStart();
			}
		);

		pause.play();*/
	}
}
