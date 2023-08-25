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

public class Statistics implements Initializable {
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private MediaView mediaViewObj;
    @FXML private Button back;

    void setMain(Main main) {
        this.main = main;
    }

    @FXML private void back() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        main.showMenu();
    }

    /*An abstract Method from Initializable interface, implemented here*/
    @FXML public void initialize(URL location, ResourceBundle resourceBundleObj) {
        System.out.println("=> opening scene opened");

        /*NOTICE & NOTICE*/
        this.back.setOnMouseEntered(event -> hoverEnter(this.back));
        this.back.setOnMouseExited(event -> hoverExit(this.back));
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
