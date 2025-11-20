import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Welcome.fxml"));
            primaryStage.setTitle("Poker");
            Scene s1 = new Scene(root,700,700);
            s1.getStylesheets().add("/CSS/Welcome.css");
            primaryStage.setScene(s1);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {launch(args);}
}
