package player;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private Button buttonObjEnter;
    @FXML private Button buttonObjStat;
    @FXML private Button buttonObjLogOut;
    @FXML private MediaView mediaViewObj;

    void setMain(Main main) {
        this.main = main;
    }

    @FXML private void newGame() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        main.showBoardSelector();
    }

    @FXML private void statistics() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        main.showStatistics();
    }

    @FXML private void logOut() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        Main.data = null;
        main.showHome();
    }

    @FXML public void initialize(URL location, ResourceBundle resourceBundleObj) {
        System.out.println("=> menu scene opened");

        /* NOTICE & NOTICE*/
        this.buttonObjEnter.setOnMouseEntered(event -> hoverEnter(this.buttonObjEnter));
        this.buttonObjEnter.setOnMouseExited(event -> hoverExit(this.buttonObjEnter));
        this.buttonObjLogOut.setOnMouseEntered(event -> hoverEnter(this.buttonObjLogOut));
        this.buttonObjLogOut.setOnMouseExited(event -> hoverExit(this.buttonObjLogOut));
        this.buttonObjStat.setOnMouseEntered(event -> hoverEnter(this.buttonObjStat));
        this.buttonObjStat.setOnMouseExited(event -> hoverExit(this.buttonObjStat));
    }

    /*NOTICE & NOTICE*/
    @FXML private void hoverEnter(Node node) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(200), node);
        transition.setToX(1.2);
        transition.setToY(1.2);
        transition.play();
    }

    @FXML private void hoverExit(Node node) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(50), node);
        transition.setToX(1);
        transition.setToY(1);
        transition.play();
    }

}
