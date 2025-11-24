import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class ResultController {
    @FXML private Label resultMessage;
    @FXML private Label winningsDisplay;

    private GameController myGameCtrl;

    public void displayResult(int winnings, GameController gameController) {
        resultMessage.setText(winnings >= 0 ? "PLAYER WINS!" : "PLAYER LOST!");
        winningsDisplay.setText("Winnings for this round: $" + winnings);
        this.myGameCtrl = gameController;
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