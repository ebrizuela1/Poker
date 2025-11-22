import PlayingCards.Card;
import PlayingCards.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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

    public void sortHand(ArrayList<Card> hand){ Collections.sort(hand); }

//    public String checkHand(ArrayList<Card> hand){
//
//    }

    public boolean hasPair(ArrayList<Card> hand){
        Collections.sort(hand);
        for (int i = 0 ; i < 2 ; i++){
            if (hand.get(i).getRank() == hand.get(i+1).getRank()){
                return true;
            }
        }
        return false;
    }

    public boolean hasFlush(ArrayList<Card> hand){
        return (Objects.equals(hand.get(0).getSuit().getPower(), hand.get(1).getSuit().getPower()) &&
                Objects.equals(hand.get(1).getSuit().getPower(), hand.get(2).getSuit().getPower()));
    }

    public boolean hasStraight(ArrayList<Card> hand){
        Collections.sort(hand);
        return hand.get(0).getRank().getPower() + 1 == hand.get(1).getRank().getPower() &&
                hand.get(1).getRank().getPower() + 1 == hand.get(2).getRank().getPower();
    }

    public boolean hasTriple(ArrayList<Card> hand) {
        Collections.sort(hand);
        return hand.get(0).getRank() == hand.get(1).getRank() && hand.get(1).getRank() == hand.get(2).getRank();
    }

    public boolean hasStraightFlush(ArrayList<Card> hand){
        if (hasStraight(hand)){
            return hasFlush(hand);
        }
        return false;
    }


}
