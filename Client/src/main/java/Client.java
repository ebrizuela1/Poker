import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Consumer<Serializable> callback;

    private String ip;
    private int port;

    // moved connection attempt to constructor to catch exception if connection fails
    public Client(String ip, int port, Consumer<Serializable> callback) throws IOException {
        this.clientSocket = new Socket(ip,port);
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.callback = callback;
        this.clientSocket.setTcpNoDelay(true);
    }

    @Override
    public void run() {
        try {
            while(true){
                Serializable data = (Serializable) in.readObject();
                callback.accept(data);
            }
        } catch (Exception e) {
            System.out.println("Connection lost: " + e.getMessage());
        }
    }

    public void send(Serializable data){
        try {
            out.writeObject(data);
            out.reset();
            out.flush();
        } catch (IOException e) {
            System.out.println("Failed to send data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}