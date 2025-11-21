import PlayingCards.Card;
import PlayingCards.Deck;

import java.util.ArrayList;
import java.util.Collections;

public class PokerInfo {
    private ArrayList<Card> clientHand;
    private ArrayList<Card> dealerHand;
    private Deck gameDeck;

    public PokerInfo(){
        this.clientHand = new ArrayList<Card>();
        this.dealerHand = new ArrayList<Card>();
        this.gameDeck = new Deck();
    }
    // ------ Getters/Setters ----------
    public ArrayList<Card> getClientHand(){ return new ArrayList<Card>(this.clientHand); }
    public ArrayList<Card> getDealerHand(){ return new ArrayList<Card>(this.dealerHand); }


    public void drawClient(){
        for (int i = 0 ; i < 3 ; i++){
            clientHand.add(gameDeck.getCard());
        }
    }

    public void drawDealer(){
        for (int i = 0 ; i < 3 ; i++){
            dealerHand.add(gameDeck.getCard());
        }
    }

    public void sortHand(ArrayList<Card> hand){Collections.sort(hand);}

//    public String checkHand(ArrayList<Card> hand){
//
//    }

//    public boolean hasPair(ArrayList<Card> hand){
//        Collections.sort(hand);
//        for (int i = 0 ; i < 3 ; i++){
//            if (){}
//        }
//    }
}
