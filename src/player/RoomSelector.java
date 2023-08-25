package player;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import util.NetworkUtil;
import util.Room;
import util.State;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoomSelector implements Initializable {
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;
    private Vector<String> list;

    @FXML private ChoiceBox<String> choiceBox;
    @FXML private Button create;
    @FXML private Button back;
    @FXML private Button join;
    @FXML private MediaView mediaViewObj;

    void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Main.nc.write(State.ROOMINFO);
        list = (Vector<String>) Main.nc.read();

        for(String x: list) {
            choiceBox.getItems().add(x);
        }
        if(list.size()!=0)
            choiceBox.setValue(list.get(0));

        /*NOTICE & NOTICE*/
        this.create.setOnMouseEntered(event -> hoverEnter(this.create));
        this.create.setOnMouseExited(event -> hoverExit(this.create));
        this.join.setOnMouseEntered(event -> hoverEnter(this.join));
        this.join.setOnMouseExited(event -> hoverExit(this.join));
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

    @FXML private void back() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        main.showBoardSelector();
    }

    @FXML private void create() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        Main.nc.write(State.ROOM);
        Main.nc.refresh();
        Main.nc.write(Main.data);
        main.showWaiting((int)Main.nc.read());
    }

    @FXML private void join() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        if(list.size()==0) return;
        Main.nc.write(State.GAME);
        int number = choiceBox.getValue().charAt(0)-'0'-1;
        Main.nc.write(number);
        Main.nc.write(Main.data);
        int serial = (int)Main.nc.read();
        if(serial > 0)
            main.showWaiting(serial);
    }
}