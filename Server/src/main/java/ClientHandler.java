import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
//    ObjectInputStream in;
//    ObjectOutputStream out;
    Socket connection;

    ClientHandler(Socket socket) throws IOException {
        connection = socket;
    }

    @Override
    public void run() {
        try (
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
        ){
            while (true) {
                Object data = in.readObject();
                System.out.println("Received: " + data);
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
        }
    }

}