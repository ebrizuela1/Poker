import PlayingCards.Deck;
import PlayingCards.Card;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GameController implements Initializable {
    Client clientConnection;

    public void connectClient(String ip, int port) {
        clientConnection = new Client(ip, port, data -> {
            Platform.runLater(() -> {
                PokerInfo info = (PokerInfo) data;
                System.out.println(info.gameMessage);
            });
        });
        clientConnection.start();
    }

    public void initialize(URL location, ResourceBundle resources){
    }
}