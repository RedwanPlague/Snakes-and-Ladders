package util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Room {
    public List<Data> people;
    public Vector<NetworkUtil> ncs;
    public Vector<Boolean> validNc;
    public boolean valid;
    public int left;

    public Room(Data creator, NetworkUtil nc) {
        people = new ArrayList<>();
        people.add(creator);
        ncs = new Vector<>();
        validNc = new Vector<>();
        ncs.addElement(nc);
        validNc.add(true);
        valid = true;
        left = 0;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i=0; i<people.size(); i++) {
            if(i!=0) s = s.concat(",");
            s = s.concat(people.get(i).getUserName());
        }
        return s;
    }

    public void add(Data player, NetworkUtil nc) {
        people.add(player);
        ncs.addElement(nc);
        validNc.addElement(true);
    }

    public int size() {
        return people.size();
    }

}
