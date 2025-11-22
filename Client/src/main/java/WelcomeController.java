import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML TextField ipField;
    @FXML TextField portField;
    public void initialize(URL location, ResourceBundle resources){
    }

    public void handleJoinButton(ActionEvent event){
        String enteredIP = ipField.getText().trim();
        Integer enteredPort = Integer.parseInt(portField.getText());
        System.out.println("Connecting to: " + enteredIP + ":" + enteredPort);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
            Parent root = loader.load();
            GameController gameCtrl = loader.getController();
            Scene gameScene = new Scene(root, 700, 600);
            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

            currentStage.setScene(gameScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
