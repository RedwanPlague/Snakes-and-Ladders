package util;

import java.io.Serializable;

public class Data implements Serializable {
    private String userName;
    private String password;

    public Data(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
