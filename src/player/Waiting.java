package player;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import util.State;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Waiting implements Initializable {
    private int serial;
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private AnchorPane layout;
    @FXML private Button start;
    @FXML private MediaView mediaViewObj;
    private int counter;

    @FXML private void start() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        Main.nc.write(State.START);
        main.showBoard((int)Main.nc.read(), serial);
        update();
    }

    private void update() {
        final ScheduledExecutorService scheduler
                = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {

            });
        },0, 1, TimeUnit.SECONDS);
    }

    void setMain(Main main) {
        this.main = main;
    }

    void setNumber(int serial) { this.serial = serial; }

    /*An abstract Method from Initializable interface, implemented here*/
    @FXML public void initialize(URL location, ResourceBundle resourceBundleObj) {
        System.out.println("=> waiting scene opened");

        /*NOTICE & NOTICE*/
        this.start.setOnMouseEntered(event -> hoverEnter(this.start));
        this.start.setOnMouseExited(event -> hoverExit(this.start));
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
