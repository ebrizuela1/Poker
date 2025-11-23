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

    private String ip;
    private int port;
    private Consumer<Serializable> callback;

    public Client(String ip, int port, Consumer<Serializable> call){
        this.ip = ip;
        this.port = port;
        this.callback = call;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            clientSocket.setTcpNoDelay(true);

            PokerInfo hello = new PokerInfo();
            hello.gameMessage = clientSocket.getPort() + " has Connected to the server";
            this.send(hello);

            while(true){
                Serializable data = (Serializable) in.readObject();
                callback.accept(data);
            }
        }catch(IOException e){
            System.out.println("Error : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    } // end run method

    public void send(Serializable data){
        try {
            out.writeObject(data);
            out.reset();
        }catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}