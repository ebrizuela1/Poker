import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML TextField ipField;
    @FXML TextField portField;
    public void initialize(URL location, ResourceBundle resources){
    }

    public void handleJoinButton(){
        System.out.println("Entered IP: " + ipField.getText() + "\nEntered port: " + portField.getText());
    }
}
