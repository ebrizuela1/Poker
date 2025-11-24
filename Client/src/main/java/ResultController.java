import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class ResultController {
    @FXML private Label resultMessage;
    @FXML private Label winningsDisplay;

    private GameController myGameCtrl;
    // Use this method for custom messages such as "Dealer not Qualified"
    public void displayResult(String message, int winnings, GameController gameController) {
        resultMessage.setText(message);
        winningsDisplay.setText("Winnings for this round: $" + winnings);
        this.myGameCtrl = gameController;
    }
    // Used for typical win or lose situation, then calls the 3 argument method
    // displayResults(a,b,c)
    public void displayResult(int winnings, GameController gameController) {
        String message = (winnings >= 0) ? "PLAYER WINS!" : "PLAYER LOST!";
        displayResult(message, winnings, gameController);
    }

    public void handlePlayAgain(ActionEvent event) {
        myGameCtrl.handleFreshStart();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleExit() {
        System.exit(0);
    }
}