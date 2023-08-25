package player;

import javafx.fxml.FXML;

public class Settings {

    private Main main;

    void setMain(Main main) {
        this.main = main;
    }

    @FXML private void back() {
        main.showMenu();
    }

}