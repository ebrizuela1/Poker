import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    public Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public Controller serverController;

    ClientHandler(Socket socket, Controller controller) throws IOException {
        this.connection = socket;
        this.serverController = controller;
    }

    @Override
    public void run() {
        try {
                out = new ObjectOutputStream(connection.getOutputStream());
                in = new ObjectInputStream(connection.getInputStream());
                connection.setTcpNoDelay(true);
            while (true) {
                PokerInfo data = (PokerInfo) in.readObject();
                System.out.println("Receiving data from Client #: " + connection.getPort());
                data.gameMessage = "Connected to the server Client Number : " + connection.getPort();
                out.writeObject(data);
                System.out.println("Data : " + data);
                out.reset();
            }
        } catch (Exception e) {
            Platform.runLater(()->{
                serverController.updateConnections(-1);
                serverController.updateLog("Client disconnected:" + connection.getPort() );
            });
            System.out.println("Client : " + connection.getPort() + "disconnected on an error");
            System.out.println("Exception occured: " + e);
        }
    }
}