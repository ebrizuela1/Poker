import PlayingCards.Card;
//import PlayingCards.Deck;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class ClientHandler implements Runnable {
    public Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public Controller serverController;

    private PokerInfo gameInfo;
    private int playerID;

    ClientHandler(Socket socket, Controller controller, int playerID) throws IOException {
        this.connection = socket;
        this.serverController = controller;
        this.playerID = playerID;
    }

    /**
     * run method handles receiving the user poker info object and handling logic
     * */
    @Override
    public void run() {
        try {
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                connection.setTcpNoDelay(true);
            while (true) {
                PokerInfo data = (PokerInfo) in.readObject();
                String action = data.gameMessage;
                serverController.updateLog("Player " + playerID + ": " + action);
                System.out.println("Receiving data from Client #: " + connection.getPort());

                // mimicking API endpoints for simplicity
                if ("DEAL".equals(action)) {
                    handleDeal(data);
                } else if ("PLAY".equals(action)) {
                    handlePlay(data);
                } else if ("FOLD".equals(action)) {
                    handleFold(data);
                } else if ("FRESH_START".equals(action)) {
                    handleFreshStart();
                }

//                out.writeObject(data);
//                out.reset();
            }
        } catch (Exception e) {
            Platform.runLater(()->{
                serverController.updateConnections(-1);
                serverController.updateLog("Player " + playerID + " disconnected:" + connection.getPort() );
            });
            System.out.println("Client : " + connection.getPort() + "disconnected on an error");
            System.out.println("Exception occured: " + e);
        } finally {
            try { in.close(); out.close(); connection.close(); } catch (IOException ignored) {}
        }
    }

    /**
     * simple deal drawing cards. Also made changes inside of draw____ that removes card from deck so no redraws
     * might become a problem later if game never has a resolution
     * */
    void handleDeal(PokerInfo info){
        info.drawClient();
        info.drawDealer();

        info.gameMessage = "Hand Dealt. Play or Fold.";
        serverController.updateLog("Player " + playerID + ": " + info.gameMessage);
        send(info);
    }

    /**
     * if the user plays hand calculates logic but catches if the dealer
     * does not have a queen on their hand so pushes bets to next hand
     * otherwise handles logic but tie does the same thing
     * */
    void handlePlay(PokerInfo info) {

        int roundWinnings = ThreeCardLogic.evalPPWinnings(info.getClientHand(), info.getPairPlusBet());
        // Return val: 0 for neither, 1 if dealer wins, 2 if player wins
        if (isDealerQualified(info.getDealerHand())) {
//            info.drawClient();
//            info.drawDealer();
//            info.gameMessage = "Redrawing!";
//            serverController.updateLog("Dealer hand not qualified... Redraw");
//            send(info);
//            return;
//        }
            int result = ThreeCardLogic.compareHands(info.getDealerHand(), info.getClientHand());

            switch (result) {
                case 0:
                    info.gameMessage = "TIE";
                    roundWinnings += info.getAnteBet(); // return ante to user
                    break;
                case 1:
                    info.gameMessage = "LOSE";
                    roundWinnings -= info.getAnteBet(); // subtract ante
                    break;
                case 2:
                    info.gameMessage = "WIN"; // player wins anteBet * 2
                    roundWinnings += info.getAnteBet() * 2; //
                    break;
                default:
                    System.out.println("INCORRECT USAGE IN HANDLE PLAY");
                    break;
            }
        }else {
            info.gameMessage = "Dealer is not qualifies"; // message read into GameController : resetGameUI;
        }
        info.setTotalWinnings(info.getTotalWinnings() + roundWinnings);
        serverController.updateLog("Player " + playerID + ": " + info.gameMessage + " " + info.getTotalWinnings());
        send(info);
    }


    /**
     * if the user sends FOLD then they just lose everything
     * */
    void handleFold(PokerInfo info){
        this.gameInfo = info;
        int totalWinnings = 0;

        totalWinnings -= info.getAnteBet();

        if (info.getPairPlusBet() > 0) {
            totalWinnings -= info.getPairPlusBet();
        }

        info.setTotalWinnings(totalWinnings);
        info.gameMessage = "FOLDED. You lost your wagers.";
        serverController.updateLog("Player " + playerID + ": " + info.gameMessage);
        send(info);
    }

    /**
     * initializing a new pokerInfo object to start from scratch
     * */
    void handleFreshStart(){
        this.gameInfo = new PokerInfo();
        this.gameInfo.gameMessage = "Fresh Start successful. Ready for bets.";
        serverController.updateLog("Player " + playerID + " initiated Fresh Start.");
        send(gameInfo);
    }

    /**
     * abstracts the whole sending process for code readability
     * */
    void send(PokerInfo info){
        try {
            out.writeObject(info);
            out.reset();
            out.flush();
        } catch (IOException e) {
            System.out.println("Error sending data to Player" + playerID + ": " + e.getMessage());
        }
    }

    /**
     * util method to check if dealer has a queen or higher
     * */
    private boolean isDealerQualified(ArrayList<Card> hand) {
        Collections.sort(hand);

        int highestRank = hand.get(2).getRank().getPower();

        return highestRank >= 12;
    }
}