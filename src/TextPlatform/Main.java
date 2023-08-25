package TextPlatform;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private Scanner in = new Scanner(System.in);
    Text textCounter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Start");
        btn.setOnAction(event -> startScheduledExecutorService());

        textCounter = new Text();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(btn, textCounter);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("This");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startScheduledExecutorService(){

        final ScheduledExecutorService scheduler
                = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
            new Runnable(){

                int counter = 0;

                @Override
                public void run() {
                    counter++;
                    if(counter<=10){

                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                textCounter.setText(counter + "");
                                TranslateTransition transition = new TranslateTransition(Duration.seconds(1), textCounter);
                                textCounter.setTranslateX(0);
                                textCounter.setTranslateY(0);
                                transition.setToX(300);
                                transition.setToY(300);
                                transition.setAutoReverse(true);
                                transition.setCycleCount(2);
                                transition.play();
                            }
                        });


                    }else{
                        scheduler.shutdown();
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                textCounter.setText(counter + "");
                            }
                        });
                    }

                }
            },
            1,
            2,
            TimeUnit.SECONDS);
    }
}