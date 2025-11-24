import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML TextField ipField;
    @FXML TextField portField;
    @FXML Label errorLabel;
    public void initialize(URL location, ResourceBundle resources){
    }

    public void handleJoinButton(ActionEvent event){
        String enteredIP = ipField.getText().trim();
        if (portField.getText().isEmpty()){
            System.out.println("Port is empty!");
            return;
        }
        Integer enteredPort = Integer.parseInt(portField.getText());
        try{
            System.out.println("Attempting connection to: " + enteredIP + ":" + enteredPort);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
            Parent root = loader.load();

            GameController gameCtrl = loader.getController();
            Client client = new Client(enteredIP,enteredPort,data -> {
                Platform.runLater(() -> {
                    PokerInfo info = (PokerInfo) data;
                    gameCtrl.updateGame(info);
                    System.out.println(info.gameMessage);
                });
            });
            gameCtrl.initClient(client);

            Scene gameScene = new Scene(root, 700, 600);

            gameScene.getStylesheets().add("CSS/Game.css");
            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.setScene(gameScene);
            currentStage.show();

        } catch (Exception e) {
            errorLabel.setText("Something went wrong. Please try again.");
            e.printStackTrace();
        }
    }
}
