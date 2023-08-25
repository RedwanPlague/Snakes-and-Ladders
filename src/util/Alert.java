package util;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {

    public static void display(String name, int pos, boolean lost) {

        Stage window = new Stage();

        AnchorPane layout = new AnchorPane();

        Text text = new Text(name + " secured " + position(pos) + " position");
        text.setFont(Font.font(25));
        text.setLayoutX(50);
        text.setLayoutY(50);
        text.setFill(Color.GREEN);

        Button close = new Button("close");
        close.setLayoutX(210);
        close.setLayoutY(140);
        close.setFont(Font.font(20));
        close.setOnAction(event -> window.close());

        layout.getChildren().addAll(text, close);
        if(lost) {
            Text loser = new Text("And you have come last!");
            loser.setFont(Font.font(30));
            loser.setLayoutX(50);
            loser.setLayoutY(100);
            loser.setFill(Color.RED);
            layout.getChildren().add(loser);
        }

        window.setScene(new Scene(layout, 480, 200));
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();

    }

    private static String position(int a) {
        if(a == 1)
            return "1st";
        if(a == 2)
            return "2nd";
        return "3rd";
    }

}
