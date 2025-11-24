import PlayingCards.Card;
import PlayingCards.Deck;
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

                if ("DEAL".equals(action)) {
                    handleDeal(data);
                } else if ("PLAY".equals(action)) {
                    handlePlay(data);
                } else if ("FOLD".equals(action)) {
                    handleFold(data);
                } else if ("FRESH_START".equals(action)) {
                    handleFreshStart();
                }

//                System.out.println("Receiving data from Client #: " + connection.getPort());
//                data.gameMessage = "Connected to the server Client Number : " + connection.getPort();
//                out.writeObject(data);
//                System.out.println("Data : " + data);
//                out.reset();
            }
        } catch (Exception e) {
            Platform.runLater(()->{
                serverController.updateConnections(-1);
                serverController.updateLog("Player" + playerID + "disconnected:" + connection.getPort() );
            });
            System.out.println("Client : " + connection.getPort() + "disconnected on an error");
            System.out.println("Exception occured: " + e);
        } finally {
            try { in.close(); out.close(); connection.close(); } catch (IOException ignored) {}
        }
    }

    void handleDeal(PokerInfo info){
        info.drawClient();
        info.drawDealer();

        info.gameMessage = "Hand Dealt. Play or Fold.";
        serverController.updateLog("Player " + playerID + ": " + info.gameMessage);
        send(info);
    }

    void handlePlay(PokerInfo info){
        // Return val: 0 for neither, 1 if dealer wins, 2 if player wins
        int result = ThreeCardLogic.compareHands(info.getDealerHand(),info.getClientHand());
        int winnings = ThreeCardLogic.evalPPWinnings(info.getClientHand(),info.getPairPlusBet());
        switch(result){
            case 0:
                info.gameMessage = "TIE";
                winnings += info.getAnteBet();
                break;
            case 1:
                info.gameMessage = "LOSE";
                winnings -= info.getAnteBet();
                break;
            case 2:
                info.gameMessage = "WIN";
                winnings += info.getAnteBet()  * 2;
                break;
            default:
                System.out.println("INCORRECT USAGE OCCURED IN HANDLE PLAY");
                break;
        }
        info.setTotalWinnings(info.getTotalWinnings() + winnings);
        send(info);
    }

    void handleFold(PokerInfo info){
        this.gameInfo = info;
        int totalWinnings = 0;

        totalWinnings -= info.getAnteBet();

        if (info.getPairPlusBet() > 0) {
            totalWinnings -= info.getPairPlusBet();
        }

        info.setTotalWinnings(totalWinnings);
        info.gameMessage = "FOLDED. You lost your wagers.";
        serverController.updateLog("P" + playerID + " Folded. Net loss: $" + (info.getAnteBet() + info.getPairPlusBet()));
        send(info);
    }

    void handleFreshStart(){
        this.gameInfo = new PokerInfo();
        this.gameInfo.gameMessage = "Fresh Start successful. Ready for bets.";
        serverController.updateLog("Player " + playerID + " initiated Fresh Start.");
        send(gameInfo);
    }
    void send(PokerInfo info){
        try {
            out.writeObject(info);
            out.reset();
            out.flush();
        } catch (IOException e) {
            System.out.println("Error sending data to Player" + playerID + ": " + e.getMessage());
        }
    }
    private boolean isDealerQualified(ArrayList<Card> hand) {
        Collections.sort(hand);

        int highestRank = hand.get(2).getRank().getPower();

        return highestRank >= 12;
    }
}