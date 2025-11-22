package PlayingCards;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable {
    private static final long serialVersionUID = 1L;
    ArrayList<Card> cards;

    public Deck(){
        this.cards = buildDeck();
    }

    // Getters / Setters
    public ArrayList<Card> getDeck(){
        return this.cards;
    }

    private ArrayList<Card> buildDeck(){
        ArrayList<Card> list = new ArrayList<Card>();
        for (Suit suit : Suit.values()){
            for (Rank rank : Rank.values()){
                Card currCard = new Card(suit, rank);
                list.add(currCard);
            }
        }
        return list;
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    public Card getCard(){
        shuffle();
        return cards.get(0);
    }

}