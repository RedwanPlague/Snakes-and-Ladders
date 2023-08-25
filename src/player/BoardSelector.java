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
import util.State;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardSelector implements Initializable {
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private MediaView mediaViewObj;
    @FXML private Button board1;
    @FXML private Button board2;
    @FXML private Button board3;
    @FXML private Button back;


    @FXML private void board1() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        Main.nc.write(State.BOARD);
        Main.nc.write(0);
        Main.board = 1;
        main.showRoomSelector();
    }

    @FXML private void board2() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        Main.nc.write(State.BOARD);
        Main.nc.write(1);
        Main.board = 2;
        main.showRoomSelector();
    }

    @FXML private void board3() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        Main.nc.write(State.BOARD);
        Main.nc.write(2);
        Main.board = 3;
        main.showRoomSelector();
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

    public void setMain(Main main) {
        this.main = main;
    }

    /*An abstract Method from Initializable interface, implemented here*/
    @FXML public void initialize(URL location, ResourceBundle resourceBundleObj) {
        System.out.println("=> board selection scene opened");

        /*NOTICE & NOTICE*/
        this.board1.setOnMouseEntered(event -> hoverEnter(this.board1));
        this.board1.setOnMouseExited(event -> hoverExit(this.board1));
        this.board2.setOnMouseEntered(event -> hoverEnter(this.board2));
        this.board2.setOnMouseExited(event -> hoverExit(this.board2));
        this.board3.setOnMouseEntered(event -> hoverEnter(this.board3));
        this.board3.setOnMouseExited(event -> hoverExit(this.board3));
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
