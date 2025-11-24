import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class Controller {
    @FXML private Label numberConnections;
    @FXML private TextField portTextField; // Matches fx:id in ServerDisplay.fxml
    @FXML private Button serverButton;
    @FXML private ListView<String> logList;
    @FXML private Label statusLabel;

    private ServerThread serverThread;

    public void initialize() {
        logList.getItems().add("Server Application Started");
    }

    public void toggleServer() {
        // Turn off server
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.stopServer();
            serverButton.setText("Turn ON");
            statusLabel.setText("Status: Stopped");
            statusLabel.setStyle("-fx-text-fill: red");
            portTextField.setDisable(false);
        } else { // turn on server
            try {
                int port = parseInt(portTextField.getText());
                serverThread = new ServerThread(port, this);
                serverThread.start();
                serverButton.setText("Turn OFF");
                statusLabel.setText("Status: Running on Port " + port);
                statusLabel.setStyle("-fx-text-fill: green");
                portTextField.setDisable(true);
            } catch (NumberFormatException e) {
                logList.getItems().add("Invalid Port Number");
            }
        }
    }

    // Helper to log from all the clients
    public void updateLog(String msg) {
        Platform.runLater(() -> {
            logList.getItems().add(msg);
            logList.scrollTo(logList.getItems().size() - 1);
        });
    }

    public void updateConnections(int count) {

        Platform.runLater(()->{
            numberConnections.setText(String.valueOf(Integer.parseInt(numberConnections.getText() ) + count ) );
        });
    }

    public class ServerThread extends Thread {
        private int port;
        private Controller callback;
        private ServerSocket serverSocket;
        private boolean isRunning = true;

        public ServerThread(int port, Controller callback) {
            this.port = port;
            this.callback = callback;
            this.setDaemon(true);
        }

        @Override
        public void run() {
            try (ServerSocket mysocket = new ServerSocket(port)) {
                serverSocket = mysocket;
                callback.updateLog("Server listening on port: " + port);

                while (isRunning) {
                    Socket connection = mysocket.accept();
                    // Accepted new Client
                    callback.updateConnections(1);
                    callback.updateLog("Client connected: " + connection.getPort());
                    // Pass 'callback' so ClientHandler can log too
                    ClientHandler handler = new ClientHandler(connection, callback);
                    Thread t = new Thread(handler);
                    t.start();
                }
            } catch (IOException e) {
                if(isRunning) callback.updateLog("Server Error: " + e.getMessage());
            }
        }



        public void stopServer() {
            isRunning = false;
            try {
                if(serverSocket != null) serverSocket.close();
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}