package util;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Piece extends Group {

    private Circle circle;
    private Text text;
    private int position;
    private double del = WindowSize.HEIGHT/10;

    public Piece(int number, Paint color, double X, double Y) {
        position = 0;
        circle = new Circle(30);
        circle.setFill(color);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        text = new Text(number + "");
        text.setFont(Font.font("Times New Roman", 20));
        text.setFill(Color.WHITE);
        getChildren().addAll(circle);
        /*circle.setCenterX(X);
        circle.setCenterY(Y);
        text.setLayoutX(X-6);
        text.setLayoutY(Y+5);*/
        setLayoutX(X);
        setLayoutY(Y);
    }

    public int getPos() {
        return position;
    }

    public void move(int pos) {
        if (position == 0) {
            if (pos != 1) return;
            position = 1;
            setLayoutX(del/2);
            setLayoutY(9*del+del/2);
            //circle.setCenterX(20);
            //circle.setCenterY(380);
            setScaleX(0);
            setScaleY(0);
            ScaleTransition transition = new ScaleTransition(Duration.seconds(1), this);
            transition.setDelay(Duration.seconds(1));
            transition.setToX(1);
            transition.setToY(1);
            transition.play();
            return;
        }

        if(position+pos > 100) return;

        int oldX = (position-1)%10;
        int oldY = (position-1)/10;
        int newX = (position+pos-1)%10;
        int newY = (position+pos-1)/10;
        if(oldY%2==1)
            oldX = 9 - oldX;
        if(newY%2==1)
            newX = 9 - newX;
        //System.out.println(oldX + " " + oldY + " " + newX + " " + newY);
        if(newY == oldY) {
            Line line = new Line(del*oldX, -del*oldY, del*newX, -del*newY);
            PathTransition transition = new PathTransition(Duration.seconds(1), line, this);
            transition.setDelay(Duration.seconds(1));
            transition.play();
        }
        else {
            Polyline line = new Polyline(del*oldX, -del*oldY,
                    (oldY%2==0)?(9*del):0, -del*oldY,
                    (oldY%2==0)?(9*del):0, -del*newY,
                    del*newX, -del*newY);
            PathTransition transition = new PathTransition(Duration.seconds(1), line, this);
            transition.setDelay(Duration.seconds(1));
            transition.play();
        }
        position += pos;

        if(position == 100) {
            ScaleTransition transition = new ScaleTransition(Duration.seconds(1), this);
            transition.setDelay(Duration.seconds(2));
            transition.setToX(0);
            transition.setToY(0);
            transition.play();
        }

    }

    public void moveTo(int pos) {
        int oldX = (position-1)%10;
        int oldY = (position-1)/10;
        int newX = (pos-1)%10;
        int newY = (pos-1)/10;
        if(oldY%2==1)
            oldX = 9 - oldX;
        if(newY%2==1)
            newX = 9 - newX;
        Line line = new Line(del*oldX, -del*oldY, del*newX, -del*newY);
        PathTransition transition = new PathTransition(Duration.seconds(1), line, this);
        transition.setDelay(Duration.seconds(2));
        transition.play();
        position = pos;
    }

    public void moveAlong(Path path, int pos) {
        PathTransition transition = new PathTransition(Duration.seconds(1), path, this);
        transition.setDelay(Duration.seconds(2));
        transition.play();
        transition.setOnFinished(event -> position = pos);
    }
}

