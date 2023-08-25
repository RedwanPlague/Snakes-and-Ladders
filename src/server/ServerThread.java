package server;

import util.Data;
import util.NetworkUtil;
import util.Room;
import util.State;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class ServerThread implements Runnable {

    private NetworkUtil nc;
    private State state;
    private int myRoomNumber;
    private int myBoard;

    ServerThread(NetworkUtil nc) {
        this.nc = nc;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        while(true) {

            state = (State) nc.read();

            if(state == State.LOGIN) {
                logIn();
            }
            else if(state == State.CREATE) {
                create();
            }
            else if(state == State.ROOM) {
                makeRoom();
            }
            else if(state == State.ROOMINFO) {
                inform();
            }
            else if(state == State.GAME) {
                game();
            }
            else if(state == State.START) {
                start();
            }
            else if(state == State.ROLL) {
                int a = (int)nc.read();
                distribute(a);
            }
            else if(state == State.LIST) {
                giveList();
            }
            else if(state == State.LEAVE) {
                leave();
            }
            else if(state == State.BOARD) {
                setBoard();
            }
            else if(state == State.EXIT) {
                save();
                nc.close();
                break;
            }

        }

        System.out.println("Dying");

    }

    private void setBoard() {
        myBoard = (int)nc.read();
    }

    private void leave() {
        Server.rooms[myBoard].get(myRoomNumber).validNc.set((int)nc.read(), false);
        Server.rooms[myBoard].get(myRoomNumber).left++;
        if(Server.rooms[myBoard].get(myRoomNumber).ncs.size() == Server.rooms[myBoard].get(myRoomNumber).left)
            Server.rooms[myBoard].remove(myRoomNumber);
        nc.write(1);
    }

    private void start() {
        Server.rooms[myBoard].get(myRoomNumber).valid = false;
        nc.write(Server.rooms[myBoard].get(myRoomNumber).size());
    }

    private void distribute(int a) {
        int i = 0;
        for(NetworkUtil x: Server.rooms[myBoard].get(myRoomNumber).ncs) {
            if(Server.rooms[myBoard].get(myRoomNumber).validNc.get(i))
                x.write(a);
            i++;
        }
    }

    private void giveList() {
        for(Data x: Server.rooms[myBoard].get(myRoomNumber).people) {
            nc.write(x.getUserName());
        }
    }

    private void game() {
        myRoomNumber = (int)nc.read();
        if(Server.rooms[myBoard].get(myRoomNumber).valid) {
            Server.rooms[myBoard].get(myRoomNumber).add((Data) nc.read(), nc);
            nc.write(Server.rooms[myBoard].get(myRoomNumber).size() - 1);
        }
        else {
            nc.write(0);
        }
    }

    private void makeRoom() {
        Room room = new Room((Data)nc.read(), nc);
        Server.rooms[myBoard].add(room);
        myRoomNumber = Server.rooms[myBoard].size()-1;
        nc.write(Server.rooms[myBoard].get(myRoomNumber).size()-1);
    }

    private void logIn() {
        String userName = (String) nc.read();
        String password = (String) nc.read();
        boolean found = false;
        for(Data x: Server.dataList) {
            if(x.getUserName().equals(userName) && x.getPassword().equals(password)) {
                found = true;
                nc.write(true);
                nc.write(x);
                break;
            }
        }
        if(!found) nc.write(false);
    }

    private void create() {
        String userName = (String) nc.read();
        String password = (String) nc.read();
        boolean found = false;
        for(Data x: Server.dataList) {
            if(x.getUserName().equals(userName)) {
                found = true;
                nc.write(true);
                break;
            }
        }
        if(!found) {
            nc.write(false);
            Server.dataList.add(new Data(userName, password));
        }
    }

    private void inform() {
        nc.refresh();
        Vector<String> list = new Vector<>();
        for(int i=0; i<Server.rooms[myBoard].size(); i++) {
            if(Server.rooms[myBoard].get(i).valid)
                list.add((i+1) + ". " + Server.rooms[myBoard].get(i).toString());
        }
        nc.write(list);
    }

    private void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Data.ser"))) {
            for(Data x: Server.dataList) {
                oos.writeObject(x);
            }
            oos.writeObject(null);
        } catch (IOException e) {
            System.out.println("in save " + e.toString());
        }
    }

}
