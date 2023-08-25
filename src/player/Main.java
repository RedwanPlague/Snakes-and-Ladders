package player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    static Data data;
    static Stage window;
    static NetworkUtil nc;
    static boolean game = false;
    static int board;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 33445);
            nc = new NetworkUtil(socket);
        } catch (IOException e) {
            System.out.println("In player main " + e.toString());
        }
        launch();
        System.out.println("exiting now or not");
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        /*window.setMinWidth(200);
        window.setMinHeight(300);*/
        window.setOnCloseRequest(event -> {
            nc.write(State.EXIT);
        });
        showHome();
        System.out.println(window.getWidth() + " " + window.getHeight());
    }

    void showHome() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Home.fxml"));
            Parent root = loader.load();

            Home controller = loader.getController();
            controller.setMain(this);

            window.setTitle("Home");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In home " + e.toString());
        }

    }

    void showCreate() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CreateAccount.fxml"));
            Parent root = loader.load();

            CreateAccount controller = loader.getController();
            controller.setMain(this);

            window.setTitle("Create New Account");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In creation " + e.toString());
        }

    }

    void showMenu() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();

            Menu controller = loader.getController();
            controller.setMain(this);

            window.setTitle("Menu");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In menu " + e.toString());
        }

    }

    void showRoomSelector() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RoomSelector.fxml"));
            Parent root = loader.load();

            RoomSelector controller = loader.getController();
            controller.setMain(this);

            window.setTitle("RoomSelector");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In room selector " + e.toString());
            e.printStackTrace();
        }

    }

    void showBoardSelector() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BoardSelector.fxml"));
            Parent root = loader.load();

            BoardSelector controller = loader.getController();
            controller.setMain(this);

            window.setTitle("BoardSelector");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In board selector " + e.toString());
            e.printStackTrace();
        }

    }

    void showSettings() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Settings.fxml"));
            Parent root = loader.load();

            Settings controller = loader.getController();
            controller.setMain(this);

            window.setTitle("Settings");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In settings " + e.toString());
        }

    }

    void showStatistics() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Statistics.fxml"));
            Parent root = loader.load();

            Statistics controller = loader.getController();
            controller.setMain(this);

            window.setTitle("Statistics");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In statistics " + e.toString());
        }

    }

    void showBoard(int total, int serial) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Board" + board + ".fxml"));
            Parent root = loader.load();

            if(board == 1) {
                Board1 controller = loader.getController();
                controller.setMain(this);
                controller.setNumber(total, serial);
            }
            else if(board == 2) {
                Board2 controller = loader.getController();
                controller.setMain(this);
                controller.setNumber(total, serial);
            }
            else if(board == 3) {
                Board3 controller = loader.getController();
                controller.setMain(this);
                controller.setNumber(total, serial);
            }

            window.setTitle(data.getUserName());
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In Board " + board + " :" + e.toString());
            e.printStackTrace();
        }

    }

    void showWaiting(int serial) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Waiting.fxml"));
            Parent root = loader.load();

            Waiting controller = loader.getController();
            controller.setMain(this);
            controller.setNumber(serial);

            window.setTitle("Waiting");
            window.setScene(new Scene(root, WindowSize.WIDTH, WindowSize.HEIGHT));
            window.show();
        } catch (IOException e) {
            System.out.println("In Waiting " + e.toString());
        }

    }

    void closeWindow() {
        window.close();
    }

}
