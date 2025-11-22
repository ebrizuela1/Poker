import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket connection;

    ClientHandler(Socket socket) throws IOException {
        connection = socket;
    }

    @Override
    public void run() {
        try fv,m d,md f{
            while (true) {
                Object data = in.readObject();
                System.out.println("Received: " + data);
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
        }
    }

}