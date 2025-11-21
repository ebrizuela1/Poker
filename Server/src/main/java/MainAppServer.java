import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainAppServer {
    public static void main(String[] args)throws IOException, ClassNotFoundException{
        MainAppServer myNet = new MainAppServer();
        myNet.serverCode();
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
}

//public class MainApp extends Application {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/FXML/ServerDisplay.fxml"));
//            Scene scene = new Scene(root, 700, 700);
//            primaryStage.setTitle("Server Display");
////            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
//}