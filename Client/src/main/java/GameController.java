import PlayingCards.Card;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    Client clientConnection;
    private PokerInfo currentInfo;
    private boolean newLook = false;


    @FXML private ComboBox<String> menuComboBox;
    @FXML private TextField wagerField;
    @FXML private TextField pairPlusField;
    @FXML private Button playButton, foldButton, dealButton;
    @FXML private Label statusLabel, totalWinningsLabel;
    @FXML private TextArea gameLog;
    @FXML private BorderPane root;

    @FXML private ImageView playerCardOne, playerCardTwo, playerCardThree;
    @FXML private ImageView dealerCardOne, dealerCardTwo, dealerCardThree;
    // Data member to keep track of ante pushed to next hand
    // (Dealer has no queen)
    private int pushedAnteAmount = 0;
    /**
     * CURRENT MESSAGES SENT TO SERVER:
     *  DEAL
     *  PLAY
     *  FOLD
     *  FRESH_START
     * */
    public void startNextHand(){
        resetGameUI();
    }
    public void initialize(URL location, ResourceBundle resources){
        resetGameUI();
    }

    public void initClient(Client client) {
        this.clientConnection = client;

        clientConnection.start();

        PokerInfo hello = new PokerInfo();
        hello.gameMessage = "New player connected";
        clientConnection.send(hello);
    }

    public void handleDeal() {
        try {
            int ante = Integer.parseInt(wagerField.getText());
            int pairPlus = pairPlusField.getText().isEmpty() ? 0 : Integer.parseInt(pairPlusField.getText());

            if (ante < 5 || ante > 25 || (pairPlus != 0 && (pairPlus < 5 || pairPlus > 25))) {
                log("Error: Bets must be between $5 and $25.");
                return;
            }
            setBettingFieldsDisable(true);
            dealButton.setDisable(true);

            PokerInfo info = new PokerInfo();
            info.setAnteBet(ante);
            info.setPairPlusBet(pairPlus);
            info.gameMessage = "DEAL";

            log("Placing bets... Dealing hand.");
            statusLabel.setText("Dealing...");

            clientConnection.send(info);
            pushedAnteAmount = 0; // reset after dealing the hand
        } catch (NumberFormatException e) {
            log("Error: Invalid bet amount.");
        }
    }

    public void handlePlay() {
        if (currentInfo == null) return;
        animateDealerCards(currentInfo.getDealerHand());
        setPlayFoldDisable(true);

        currentInfo.gameMessage = "PLAY";
        log("Player chooses to PLAY. Revealing dealer's hand...");
        statusLabel.setText("Revealing Dealer Hand...");

        clientConnection.send(currentInfo);
    }

    public void handleFold(ActionEvent event) {
        if (currentInfo == null) return;
        animateDealerCards(currentInfo.getDealerHand());

        currentInfo.gameMessage = "FOLD";
        log("Player FOLDS. Losing all current wagers.");
        statusLabel.setText("You Folded.");
        clientConnection.send(currentInfo);
        setPlayFoldDisable(true);
    }

    public void updateGame(PokerInfo info) {
        this.currentInfo = info;
        String msg = info.gameMessage;

        if (msg != null && !msg.isEmpty()) {
            log("Server: " + msg);
        }
        // First check if the ante was pushed
        if ("Dealer is not qualified.".equals(msg)){
            this.pushedAnteAmount = info.getAnteBet();
            // Show the dealers hand so the user knows the dealer did not even have a queen
            updateHandImages(info.getDealerHand(), dealerCardOne, dealerCardTwo, dealerCardThree);
            // Run the ResultController alert
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ResultPopup.fxml"));
                Parent root = loader.load();
                Stage currentOwner = (Stage)this.root.getScene().getWindow();
                Stage resultStage = new Stage();
                resultStage.setTitle("Game Results");
                resultStage.initModality(Modality.APPLICATION_MODAL);
                resultStage.initOwner(currentOwner);
                Scene scene = new Scene(root);
                resultStage.setScene(scene);
                ResultController resultController = loader.getController();
                // use custom message displayResults(message, winning, controller)
                resultController.displayResult("Dealer Not Qualified!\nAnte was pushed to next hand.",
                        info.getTotalWinnings(), this);
                resultStage.showAndWait();
            } catch (IOException e) {
                log("Failed to load Result Scene: " + e.getMessage());
            }
        }

        if ("Hand Dealt. Play or Fold.".equals(msg)) {
//            resetCardImage(playerCardOne);
//            resetCardImage(playerCardTwo);
//            resetCardImage(playerCardThree);
//            resetCardImage(dealerCardOne);
//            resetCardImage(dealerCardTwo);
//            resetCardImage(dealerCardThree);
            animatePlayerCards(info.getClientHand());
            playButton.setDisable(false);
            foldButton.setDisable(false);
        }
        else if (msg != null && (msg.contains("WIN") || msg.contains("LOSE") || msg.contains("TIE") || msg.contains("FOLDED"))) {
            updateHandImages(info.getDealerHand(), dealerCardOne, dealerCardTwo, dealerCardThree);
            totalWinningsLabel.setText("Total Winnings: $" + info.getTotalWinnings());
            showGameOverAlert(info.getTotalWinnings());
        }
    }

    public void handleNewLook(){
        if(newLook){
            String originalStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #157347, #053A22);";
            root.setStyle(originalStyle);
        } else {
            String newStyle = "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #9F8000, #816317);";
            root.setStyle(newStyle);
        }
        newLook = !newLook;
    }

    public void handleFreshStart(){
        resetGameUI();
        currentInfo.gameMessage = "FRESH_START";

        clientConnection.send(currentInfo);
        log("Fresh Start! Starting over...");
    }

    public void handleExit(){
        Platform.exit();
        System.exit(0);
    }

    /*
    * BEGIN HELPER METHODS SECTION
    * */

    private void showGameOverAlert(int winnings) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ResultPopup.fxml"));
            Parent root = loader.load();

            Stage currentOwner = (Stage)this.root.getScene().getWindow();

            Stage resultStage = new Stage();
            resultStage.setTitle("Game Results");

            resultStage.initModality(Modality.APPLICATION_MODAL);
            resultStage.initOwner(currentOwner);

            Scene scene = new Scene(root);
            resultStage.setScene(scene);

            ResultController resultController = loader.getController();
            resultController.displayResult(winnings, this);

            resultStage.showAndWait();
        } catch (IOException e) {
            log("Failed to load Result Scene: " + e.getMessage());
        }
    }

    private void resetGameUI() {
        log("--- New Game ---");
        statusLabel.setText("Place your bets.");
        // Check if anything was pushed back to hand
        if (pushedAnteAmount > 0){
            //disable ante field
            wagerField.setText(String.valueOf(pushedAnteAmount));
            wagerField.setDisable(true);
            this.log("Ante of $" + pushedAnteAmount + " pushed from previous hand.");
        }else{
            wagerField.clear();
            wagerField.setDisable(false); // just in case
        }
        pairPlusField.clear();
        totalWinningsLabel.setText("Total Winnings: $0");

        resetCardImage(playerCardOne);
        resetCardImage(playerCardTwo);
        resetCardImage(playerCardThree);
        resetCardImage(dealerCardOne);
        resetCardImage(dealerCardTwo);
        resetCardImage(dealerCardThree);

        pairPlusField.setDisable(false);
        dealButton.setDisable(false);
        setPlayFoldDisable(true);
        currentInfo = null;
    }

    private void animatePlayerCards(ArrayList<Card> hand) {
        ImageView[] views = {playerCardOne, playerCardTwo, playerCardThree};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5 * (i + 1)));
            pause.setOnFinished(e -> setCardImage(views[index], hand.get(index)));
            pause.play();
        }
    }
    private void animateDealerCards(ArrayList<Card> hand) {
        ImageView[] views = {dealerCardOne, dealerCardTwo, dealerCardThree};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5 * (i + 1)));
            pause.setOnFinished(e -> setCardImage(views[index], hand.get(index)));
            pause.play();
        }
    }

    private void setCardImage(ImageView view, Card card) {
        try {
            view.setImage(new Image(getClass().getResourceAsStream("/Cards/" + card.getPath())));
            view.setStyle("-fx-background-color: cornsilk;");
        } catch (Exception e) {
            log("Error loading image: " + card.getPath());
        }
    }

    private void setBettingFieldsDisable(boolean disable) {
        wagerField.setDisable(disable);
        pairPlusField.setDisable(disable);
    }

    private void setPlayFoldDisable(boolean disable) {
        playButton.setDisable(disable);
        foldButton.setDisable(disable);
    }

    private void updateHandImages(ArrayList<Card> hand, ImageView card1, ImageView card2, ImageView card3) {
        if (hand == null || hand.size() < 3) return;
        setCardImage(card1, hand.get(0));
        setCardImage(card2, hand.get(1));
        setCardImage(card3, hand.get(2));
    }

    private void resetCardImage(ImageView view) {
        String CARD_BACK_PATH = "/Cards/back.png";
        view.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(CARD_BACK_PATH))));
    }

    private void log(String message) {
        gameLog.appendText(message + "\n");
    }
}