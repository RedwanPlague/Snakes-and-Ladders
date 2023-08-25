package server;

import util.NetworkUtil;
import util.State;

import java.util.Vector;

public class RoomThread implements Runnable {

    private Vector<NetworkUtil> ncs = new Vector<>();
    private Vector<NetworkUtil> serverNcs = new Vector<>();

    RoomThread(NetworkUtil nc, NetworkUtil myNc) {
        ncs.addElement(nc);
        serverNcs.addElement(myNc);
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        while(true) {

            /*state = (State) nc.read();

            if(state == State.LOGIN) {
                logIn();
            }
            else if(state == State.CREATE) {
                create();
            }
            else if(state == State.ROOM) {
                makeRoom();
            }*/

        }

    }

    void add(NetworkUtil nc) {

    }

}
