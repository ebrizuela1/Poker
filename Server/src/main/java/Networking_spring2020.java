import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Networking_spring2020 {


    private static boolean isServer = false;


    public static void main(String[] args)throws IOException, ClassNotFoundException{
        // TODO Auto-generated method stub

        Networking_spring2020 myNet = new Networking_spring2020();

        if(isServer) {

            myNet.serverCode();

        }
        else {

            myNet.clientCode();

        }


    }

    public void serverCode()throws IOException, ClassNotFoundException{

        ServerSocket mysocket = new ServerSocket(5555);
        System.out.println("Server is waiting for a client!");

        Socket connection = mysocket.accept();

        System.out.println("Server has a client!!!");
        System.out.println("This is the remote port the client is using: " + connection.getPort());


        ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
        connection.setTcpNoDelay(true);

        while(true) {

            //* try with string objects

            String data = in.readObject().toString();
            System.out.println("Server received: " + data);
            out.writeObject(data.toUpperCase());

        }

        //*/

	    	/* with a serializable class
		    	InfoPass info = (InfoPass)in.readObject();

		    	System.out.println("Server received this InfoPass object: ");
		    	System.out.println("p1: " + info.p1Choic + " p2: " + info.p2Choice);
		    	System.out.println("p1Score: "+info.p1Score+" p2Score: "+info.p2Score);
		    	System.out.println("password: " + info.password);
		    	info.p1Score=12;
		    	info.p2Score=13;
		    	info.password = "cozmo cleared";
		    	out.writeObject(info);
		    	break;

	    }
 	*/

	    	/*try with arraylist: works because arraylist is serializable
	    	ArrayList<Integer> list = (ArrayList<Integer>)in.readObject();

	    	System.out.println("Server received this arraylist: ");
	    	list.forEach(i->System.out.println(i));
	    	//out.writeObject(list.add(500));
	    	list.add(500);
	    	out.writeObject(list);
	    	break;
	    */


        // }	//uncomment when doing networking example instead of exceptions


    }//end of method

    public void clientCode() throws IOException, ClassNotFoundException{

        Socket socketClient= new Socket("127.0.0.1",5555);
        System.out.println("Client: "+"Connection Established");

        System.out.println("This is the remote address client is connected to: " +socketClient.getRemoteSocketAddress());
        System.out.println("And the remote port: " + socketClient.getPort());

        Scanner scanner = new Scanner(System.in);
        ObjectOutputStream out = new ObjectOutputStream(socketClient.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socketClient.getInputStream());
        socketClient.setTcpNoDelay(true);


	    /*try with a class that is serializable

	    while(scanner.hasNextLine()) {
	    	InfoPass info = new InfoPass();
	    	info.p1Choic = "odds";
	    	info.p2Choice = "evens";
	    	info.p1Score = 7;
	    	info.p2Score = 8;
	    	info.password = "cozmo";

	    	out.writeObject(info);
	    	info = (InfoPass)in.readObject();
	    	System.out.println("score: " + info.p1Score + " " + info.p2Score);
	    	System.out.println("password: " + info.password);

	    	break;
	    }
	    socketClient.close();
	    scanner.close();
	   */

	    /*try with arrayList. works because arraylist is serializable
	    while(scanner.hasNextLine()) {
	    	ArrayList<Integer> list = new ArrayList<Integer>();

	    	list.add(20);
	    	list.add(30);
	    	list.add(40);

	    	out.writeObject(list);
	    	list = (ArrayList<Integer>)in.readObject();
	    	list.forEach(e->System.out.println(e));


	    	break;
	    }
	   socketClient.close();
	   scanner.close();

	  */

        //* try with strings
        while(scanner.hasNextLine()) {
            out.writeObject(scanner.nextLine());
            String data = in.readObject().toString();
            System.out.println("client received: " + data);
        }

        scanner.close();
        socketClient.close();
        //*/
    }

}

class InfoPass implements Serializable {

    private static final long serialVersionUID = 1L;
    int p1Score;
    int p2Score;
    String p1Choic, p2Choice;
    transient String password;
    //String password;

}