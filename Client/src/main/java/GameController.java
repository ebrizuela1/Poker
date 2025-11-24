import PlayingCards.Deck;
import PlayingCards.Card;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GameController implements Initializable {
    Client clientConnection;

    @FXML
    private ComboBox<String> menuComboBox;
    @FXML
    private TextField wagerField;
    @FXML
    private TextField pairPlusField;

    @FXML private ImageView playerCardOne, playerCardTwo, playerCardThree;
    @FXML private ImageView dealerCardOne, dealerCardTwo, dealerCardThree;

    public void initialize(URL location, ResourceBundle resources){
    }

    public void connectClient(String ip, int port) {
        clientConnection = new Client(ip, port, data -> {
            Platform.runLater(() -> {
                PokerInfo info = (PokerInfo) data;
                System.out.println(info.gameMessage);
            });
        });
        clientConnection.start();
    }

    public void handleMenu(ActionEvent event) {
        String selected = menuComboBox.getValue();
        if(selected == null) return;
        switch (selected) {
            case "New Look":
                System.out.println("Changing Theme...");
                break;
            case "Fresh Start":
                System.out.println("Resetting Game...");
                break;
            case "Exit":
                System.out.println("Exiting...");
                Platform.exit();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void setWager(){
        Integer enteredAmount = Integer.parseInt(wagerField.getText());
        if(enteredAmount >= 5 && enteredAmount <= 25) {
            System.out.println("Entered Amount: " + enteredAmount);
        } else {
            System.out.println("Invalid Amount");
        }
    }

    public void setPairPlus(){
        Integer enteredAmount = Integer.parseInt(pairPlusField.getText());
        if(enteredAmount >= 5 && enteredAmount <= 25) {
            System.out.println("Entered Amount: " + enteredAmount);
        } else  {
            System.out.println("Invalid Amount");
        }
    }

    public void updatePlayerCards(PokerInfo info){
        ArrayList<Card> pHand = info.getClientHand();
        playerCardOne.setImage(new Image(getClass().getResourceAsStream("/Cards/" + pHand.get(0).getPath())));
        playerCardTwo.setImage(new Image(getClass().getResourceAsStream("/Cards/" + pHand.get(1).getPath())));
        playerCardThree.setImage(new Image(getClass().getResourceAsStream("/Cards/" + pHand.get(2).getPath())));
    }

    public void updateDealerCards(PokerInfo info){
        ArrayList<Card> dHand = info.getDealerHand();
        dealerCardOne.setImage(new Image(getClass().getResourceAsStream("/Cards/" + dHand.get(0).getPath())));
        dealerCardTwo.setImage(new Image(getClass().getResourceAsStream("/Cards/" + dHand.get(1).getPath())));
        dealerCardThree.setImage(new Image(getClass().getResourceAsStream("/Cards/" + dHand.get(2).getPath())));
    }
}