package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class NetworkUtil {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public NetworkUtil(Socket socket) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("In Constructor NetworkUtil: " + e.toString());
            e.printStackTrace();
        }
    }

    public Object read() {
        Object o = null;
        try {
            o = ois.readObject();
        } catch (Exception e) {
            System.out.println("In read NetworkUtil: " + e.toString());
            e.printStackTrace();
            close();
        }
        return o;
    }

    public void write(Object o) {
        try {
            oos.writeObject(o);
        } catch (IOException e) {
            System.out.println("In write NetworkUtil: " + e.toString());
            e.printStackTrace();
            close();
        }
    }

    public void refresh() {
        try {
            oos.reset();
        } catch (IOException e) {
            System.out.println("In refresh NetworkUtil: " + e.toString());
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            ois.close();
            oos.close();
        } catch (IOException e) {
            System.out.println("In close NetworkUtil " + e.toString());
            e.printStackTrace();
        }
    }

}
