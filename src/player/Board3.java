package player;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.*;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Board3 implements Initializable {
    private Main main;
    private Scene scene;
    private Media mediaObj;
    private MediaPlayer mediaPlayerObj;

    @FXML private AnchorPane layout;
    @FXML private Button roll;
    @FXML private Button temper;
    @FXML private Button leave;
    @FXML private Text temperLeft;
    @FXML private ChoiceBox<Integer> choiceBox;
    @FXML private ImageView frame;
    @FXML private ImageView face;
    @FXML private Path path16;
    @FXML private Path path46;
    @FXML private Path path49;
    @FXML private Path path62;
    @FXML private Path path64;
    @FXML private Path path74;
    @FXML private Path path89;
    @FXML private Path path92;
    @FXML private Path path95;
    @FXML private Path path99;
    @FXML private MediaView mediaViewObj;

    private Polygon triangle;
    private BoardClassic boardPath;
    private Piece[] pieces;
    private Text[] people;
    private boolean[] playing;
    private int total, serial, turn, left, chance;
    private int count;
    private Dice dice;
    private double del = WindowSize.HEIGHT/10;
    private ScheduledExecutorService scheduler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        boardPath = new BoardClassic();
        count = 0;
        chance = 10;
        roll.setOpacity(0.1);
        temper.setOpacity(0.1);
        choiceBox.setOpacity(0.1);
        temperLeft.setText("Temper Left : " + chance);
        frame.setFitWidth(10*del);
        frame.setFitHeight(10*del);
        /*face.setLayoutX(face.getLayoutX()*WindowSize.WIDTH/600);
        face.setLayoutY(face.getLayoutY()*WindowSize.HEIGHT/400);
        roll.setLayoutX(roll.getLayoutX()*WindowSize.WIDTH/600);
        roll.setLayoutY(roll.getLayoutY()*WindowSize.HEIGHT/400);*/
        leave.setOpacity(0);
        choiceBox.getItems().addAll(1,2,3,4,5,6);
        choiceBox.setValue(1);
        triangle = new Polygon(0, -25, 0, 25, 25, 0);
        triangle.setFill(Color.GOLD);
        layout.getChildren().add(triangle);
        WindowSize.enhance(path16);
        WindowSize.enhance(path46);
        WindowSize.enhance(path49);
        WindowSize.enhance(path62);
        WindowSize.enhance(path64);
        WindowSize.enhance(path74);
        WindowSize.enhance(path89);
        WindowSize.enhance(path92);
        WindowSize.enhance(path95);
        WindowSize.enhance(path99);

        update();

    }

    private void update() {

        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            if(count == 0) {
                Platform.runLater(() -> {
                    left = total;
                    pieces = new Piece[total];
                    people = new Text[total];
                    playing = new boolean[total];
                    for(int i=0; i<total; i++)
                        playing[i] = true;

                    Main.nc.write(State.LIST);

                    for(int i=0; i<total; i++) {
                        pieces[i] = new Piece(i+1, color(i),WindowSize.WIDTH*3/4 - 20, 5*del + del*i + 100);

                        people[i] = new Text((String)Main.nc.read());
                        people[i].setLayoutX(WindowSize.WIDTH*4/5 - 10);
                        people[i].setLayoutY(5*(del+1) + del*i + 110);
                        people[i].setFont(Font.font(50));
                        people[i].setFill(color(i));
                        layout.getChildren().addAll(pieces[i], people[i]);
                    }

                    scene = Main.window.getScene();

                    /*scene.widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> ob, Number oldWidth, Number newWidth) {
                            fixToRatio((double)oldWidth, (double)newWidth);
                        }
                    });
                    scene.heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> ob, Number oldHeight, Number newHeight) {
                            fixToRatio((double)oldHeight, (double)newHeight);
                        }
                    });*/

                    if(serial == 0) {
                        roll.setOpacity(1);
                        if(chance > 0) {
                            temper.setOpacity(1);
                            choiceBox.setOpacity(1);
                        }
                    }
                    triangle.setLayoutX(WindowSize.WIDTH*4/5 - 45);
                    triangle.setLayoutY(5*(del+1) + del*turn - 7 + 100);
                    //triangle.setStroke(color(0));
                    //triangle.setStrokeWidth(1);
                    dice = new Dice(serial);
                });
                count++;
            }
            else if(left > 1 || (total == 1 && left == 1)) {
                //System.out.println(count++);
                int a;
                a = (int) Main.nc.read();
                System.out.println(a);
                Platform.runLater(() -> {
                    //face.setText(a + "");
                    setImage(face, a);
                    //System.out.println("Turn is: " + turn);
                    pieces[turn].move(a);
                    if(boardPath.mapWay.containsKey(pieces[turn].getPos()))
                        pieces[turn].moveTo(boardPath.mapWay.get(pieces[turn].getPos()));
                    moveSnake(pieces[turn].getPos(), pieces[turn]);
                    if(pieces[turn].getPos() == 100) {
                        playing[turn] = false;
                        people[turn].setOpacity(0.4);
                        left--;
                        if(turn == serial) {
                            Alert.display("You have", total - left, false);
                        }
                        else if(left == 1) {
                            Alert.display(people[turn].getText() + " has", total - left, true);
                        }
                        else {
                            Alert.display(people[turn].getText() + " has", total - left, false);
                        }
                        people[turn].setText(people[turn].getText() + "  " + (total - left));
                        //System.out.println(left);
                    }
                    if(pieces[serial].getPos() == 100 || ( left == 1 && total != 1)) {
                        leave.setOpacity(1);
                    }
                    do {
                        turn = (turn+1)%total;
                    } while(!playing[turn]);

                    triangle.setLayoutX(WindowSize.WIDTH*4/5 - 45);
                    triangle.setLayoutY(5*(del+1) + del*turn - 7 + 100);
                    //triangle.setStroke(color(turn));
                    if(turn == serial) {
                        roll.setOpacity(1);
                        if(chance > 0) {
                            temper.setOpacity(1);
                            choiceBox.setOpacity(1);
                        }
                    }
                });
            }
            else {
                try {
                    scheduler.awaitTermination(0, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.out.println("while awaiting: " + e.toString());
                }
                scheduler.shutdownNow();
                leave.setOpacity(1);
                //roll.setTranslateX(-100);
                System.out.println("shutting down");
            }
        },0, 500, TimeUnit.MILLISECONDS);
    }

    private void moveSnake(int a, Piece piece) {
        if(pieces[turn].getPos() == 16) {
            pieces[turn].moveAlong(path16, 6);
        }
        else if(pieces[turn].getPos() == 46) {
            pieces[turn].moveAlong(path46, 25);
        }
        else if(pieces[turn].getPos() == 49) {
            pieces[turn].moveAlong(path49, 11);
        }
        else if(pieces[turn].getPos() == 62) {
            pieces[turn].moveAlong(path62, 19);
        }
        else if(pieces[turn].getPos() == 64) {
            pieces[turn].moveAlong(path64, 60);
        }
        else if(pieces[turn].getPos() == 74) {
            pieces[turn].moveAlong(path74, 53);
        }
        else if(pieces[turn].getPos() == 89) {
            pieces[turn].moveAlong(path89, 68);
        }
        else if(pieces[turn].getPos() == 92) {
            pieces[turn].moveAlong(path92, 88);
        }
        else if(pieces[turn].getPos() == 95) {
            pieces[turn].moveAlong(path95, 75);
        }
        else if(pieces[turn].getPos() == 99) {
            pieces[turn].moveAlong(path99, 80);
        }
    }

    @FXML private void roll() {
        /*For sound effect*/
        String path = new File("src/MediaFile/dice.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        if(turn != serial) return;
        //System.out.println(turn);
        Main.nc.write(State.ROLL);
        Main.nc.write(dice.roll());
        roll.setOpacity(0.1);
        temper.setOpacity(0.1);
        choiceBox.setOpacity(0.1);
    }

    @FXML private void temper() {
        /*For sound effect*/
        String path = new File("src/MediaFile/dice.mp3").getAbsolutePath();
        this.mediaObj = new Media(new File(path).toURI().toString());
        this.mediaPlayerObj = new MediaPlayer(this.mediaObj);
        this.mediaViewObj.setMediaPlayer(this.mediaPlayerObj);
        this.mediaPlayerObj.setAutoPlay(true);

        if(turn != serial) return;
        if(chance <= 0) return;
        //System.out.println(turn);
        Main.nc.write(State.ROLL);
        int a = choiceBox.getValue();
        Main.nc.write(dice.roll(a));
        roll.setOpacity(0.1);
        temper.setOpacity(0.1);
        choiceBox.setOpacity(0.1);
        chance--;
        temperLeft.setText("Temper Left : " + chance);
    }

    @FXML private void leave() {
        try {
            scheduler.awaitTermination(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("while awaiting: " + e.toString());
        }
        scheduler.shutdownNow();
        Main.nc.write(State.LEAVE);
        Main.nc.write(serial);
        main.showMenu();
    }

    private void setImage(ImageView image, int a) {
        /*ScaleTransition transition = new ScaleTransition(javafx.util.Duration.seconds(1), image);
        transition.setToX(0);
        transition.setToY(0);
        transition.play();
        transition.setOnFinished(event -> image.setImage(new Image("Face"+a+".jpg")));*/
        image.setImage(new Image("Face"+a+".jpg"));
        image.setScaleX(0);
        image.setScaleY(0);
        ScaleTransition transition1 = new ScaleTransition(javafx.util.Duration.seconds(1), image);
        //transition1.setDelay(javafx.util.Duration.seconds(1));
        transition1.setToX(1);
        transition1.setToY(1);
        transition1.play();
    }

    /*private void fixToRatio(double oldValue, double newValue) {
        double ratio = newValue/oldValue;
        frame.setFitHeight(frame.getFitHeight()*ratio);
        roll.setLayoutY(roll.getLayoutY()*ratio);
        frame.setFitWidth(frame.getFitWidth()*ratio);
        roll.setLayoutX(roll.getLayoutX()*ratio);
        for(int i=0; i<total; i++) {
            pieces[i].setLayoutX(pieces[i].getLayoutX()*ratio);
            people[i].setLayoutX(people[i].getLayoutX()*ratio);
            pieces[i].setLayoutY(pieces[i].getLayoutY()*ratio);
            people[i].setLayoutY(people[i].getLayoutY()*ratio);
        }
    }*/

    void setNumber(int total, int serial) {
        this.total = total;
        this.serial = serial;
        turn = 0;
    }

    void setMain(Main main) {
        this.main = main;
    }

    private Paint color(int number) {
        if(number == 0) return Color.RED;
        if(number == 1) return Color.BLUE;
        if(number == 2) return Color.GREEN;
        return Color.GOLDENROD;
    }

}
