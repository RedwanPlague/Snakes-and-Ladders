package server;

import util.Data;
import util.NetworkUtil;
import util.Room;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static List<Room>[] rooms = new List[3];

    static List<Data> dataList = new ArrayList<>(); // For File stuff

    public static void main(String[] args) {

        rooms[0] = new ArrayList<>();
        rooms[1] = new ArrayList<>();
        rooms[2] = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Data.ser"))) {
            Data temp;
            while( (temp = (Data)ois.readObject()) != null ) {
                dataList.add(temp);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("in main server " + e.toString());
        }

        try {
            ServerSocket sc = new ServerSocket(33445);
            while(true) {
                Socket socket = sc.accept();
                NetworkUtil nc = new NetworkUtil(socket);
                new ServerThread(nc);
            }
        } catch (IOException e) {
            System.out.println("in main server " + e.toString());
        }
    }
}
