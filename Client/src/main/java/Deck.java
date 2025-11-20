import java.util.ArrayList;

public class Deck {
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
}