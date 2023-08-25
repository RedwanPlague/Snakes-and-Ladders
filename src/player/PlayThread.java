package player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class PlayThread implements Runnable {

    private NetworkUtil nc;
    int serial, mine;
    private Main main;

    PlayThread(NetworkUtil nc, int mine, Main main) {
        System.out.println("new one");
        this.nc = nc;
        Thread t = new Thread(this);
        t.start();
        serial = 0;
        this.mine = mine;
        this.main = main;
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);

        //in.next();

        //main.showBoard();

    }

}
