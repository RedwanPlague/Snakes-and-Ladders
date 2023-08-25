package player;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import util.Data;
import util.State;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private MediaView mediaViewObj;
    @FXML private Button exit;
    @FXML private Button log;
    @FXML private Button account;

    void setMain(Main main) {
        this.main = main;
    }

    @FXML private void logIn() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        if(userName.getText().equals("") || password.getText().equals("")) {
            userName.setText("");
            password.setText("");
            return;
        }

        Main.nc.write(State.LOGIN);
        Main.nc.write(userName.getText());
        Main.nc.write(password.getText());
        boolean found = (boolean) Main.nc.read();
        if(found) {
            Main.data = (Data) Main.nc.read();
            main.showMenu();
        }
        else {
            userName.setText("");
            password.setText("");
        }
    }

    @FXML private void create() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        main.showCreate();
    }

    @FXML private void exit() {
        Main.nc.write(State.EXIT);
        main.closeWindow();
    }

    /*An abstract Method from Initializable interface, implemented here*/
    @FXML public void initialize(URL location, ResourceBundle resourceBundleObj) {
        System.out.println("=> opening scene opened");

        /*NOTICE & NOTICE*/
        this.log.setOnMouseEntered(event -> hoverEnter(this.log));
        this.log.setOnMouseExited(event -> hoverExit(this.log));
        this.account.setOnMouseEntered(event -> hoverEnter(this.account));
        this.account.setOnMouseExited(event -> hoverExit(this.account));
        this.exit.setOnMouseEntered(event -> hoverEnter(this.exit));
        this.exit.setOnMouseExited(event -> hoverExit(this.exit));
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
