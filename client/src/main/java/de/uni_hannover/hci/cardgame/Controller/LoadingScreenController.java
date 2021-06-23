package de.uni_hannover.hci.cardgame.Controller;

import de.uni_hannover.hci.cardgame.ControllerInterface;
import de.uni_hannover.hci.cardgame.fxmlNavigator;
import de.uni_hannover.hci.cardgame.gameClient;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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

	double progressBarValue = 0.0;
	String[] ImageNameList = {"/textures/cards/diamonds/ten_of_diamonds.png", "/textures/cards/clubs/ace_of_clubs.png", "/textures/cards/clubs/six_of_clubs.png", "/textures/cards/hearts/nine_of_hearts.png"};
	int    ImageNr = 0;
	

	@FXML
	private void goToHome(ActionEvent event)
	{
		fxmlNavigator.loadFxml(fxmlNavigator.HOME);
	}

	@Override
	public void resize(Stage stage)
	{
		// The Pane of the Scene, that has got everything
		Scene scene = stage.getScene();
		Loading = (Pane) scene.lookup("#Loading");
		double sceneWidth = scene.getWidth();
		double sceneHeight = scene.getHeight();
		Loading.setPrefWidth(sceneWidth);
		Loading.setPrefHeight(sceneHeight);


		// The progressbar, that has not yet been correctly implemented
		LoadingBar = (ProgressBar) scene.lookup("#LoadingBar");
		double loadingBarWidth = sceneWidth * 0.99;
		double loadingBarHeight = sceneHeight * 0.05;
		LoadingBar.setPrefWidth(loadingBarWidth);
		LoadingBar.setPrefHeight(loadingBarHeight);
		LoadingBar.setLayoutX((sceneWidth - loadingBarWidth) / 2.0);
		LoadingBar.setLayoutY((sceneHeight - loadingBarHeight) / 2.0);

		// The text that is hovering above the loading bar
		LoadingTitle = (Text) scene.lookup("#LoadingTitle");
		double loadingTitleWidth = LoadingTitle.getBoundsInLocal().getWidth();
		LoadingTitle.setLayoutX((sceneWidth - loadingTitleWidth) / 2.0);
		LoadingTitle.setLayoutY(((sceneHeight - loadingBarHeight) / 2.0) - loadingBarHeight);

		// The button that will bring the user to the next pane
		LoadingNextButton = (Button) scene.lookup("#LoadingNextButton");
		double loadingNextButtonWidth = 550;
		double loadingNextButtonHeight = 120;
		LoadingNextButton.setPrefWidth(loadingNextButtonWidth);
		LoadingNextButton.setPrefHeight(loadingNextButtonHeight);
		LoadingNextButton.setLayoutX((sceneWidth - loadingNextButtonWidth) / 2.0);
		LoadingNextButton.setLayoutY((sceneHeight - loadingNextButtonHeight) / 1.2);
	}

	@Override
	public void init ()
	{
		Stage stage = gameClient.stage_;
		resize(stage);
		double sW = stage.getWidth();
		double sH = stage.getHeight();
		PauseTransition pause = new PauseTransition(Duration.millis(500));
		pause.setOnFinished
				(
						e ->
						{
							if (ImageNr < ImageNameList.length)
							{
								System.out.println(String.format("Nach Pause Bild %d", ImageNr));

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
								System.out.println(String.format("Pause %.3f", progressBarValue));
								progressBarValue = progressBarValue + 0.1;
							}

							if (ImageNr ==  ImageNameList.length)
							{
								System.out.println(String.format("Nun nur Pause %.3f", progressBarValue));
								ImageNr++;
								progressBarValue = 0.0;
							}

							LoadingBar.setProgress(progressBarValue);

							if (ImageNr < ImageNameList.length || progressBarValue < 1.0)    pause.playFromStart();
						}
				);

		pause.play();
	}
}
