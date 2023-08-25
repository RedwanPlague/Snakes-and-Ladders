package player;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class CreateAccount implements Initializable {
    private Main main;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private PasswordField confirm;
    @FXML private Label warning;
    @FXML private Button register;
    @FXML private Button back;
    @FXML private MediaView mediaViewObj;

    void setMain(Main main) {
        this.main = main;
    }

    @FXML private void register() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        if(password.getText().equals(confirm.getText())) {
            Main.nc.write(State.CREATE);
            Main.nc.write(userName.getText());
            Main.nc.write(password.getText());
            boolean found = (boolean) Main.nc.read();
            if(found) {
                userName.setText("");
                password.setText("");
                confirm.setText("");
                warning.setText("User Name already taken");
            }
            else {
                if((this.userName.getText().equals("") || this.password.getText().equals(""))) {
                    userName.setText("");
                    password.setText("");
                    confirm.setText("");
                    warning.setText("Invalid Input - Try Again");
                }
                else {
                    main.showMenu();
                    Main.data = new Data(userName.getText(), password.getText());
                }
            }
        }
        else {
            password.setText("");
            confirm.setText("");
            warning.setText("Passwords do not Match");
        }
    }

    @FXML private void back() {
        /*For sound effect*/
        String path = new File("src/MediaFile/button.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        main.showHome();
    }

    /*An abstract Method from Initializable interface, implemented here*/
    @FXML public void initialize(URL location, ResourceBundle resourceBundleObj) {
        System.out.println("=> opening scene opened");

        /*NOTICE & NOTICE*/
        this.register.setOnMouseEntered(event -> hoverEnter(this.register));
        this.register.setOnMouseExited(event -> hoverExit(this.register));
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
