package PlayingCards;

public class Card {
    private Suit suit; // Ace, Spade, Heart, Diamond
    private Rank rank; // 2, . . . J, Q, K

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString(){
        String mySuit = this.suit.toString();
        String myRank = this.rank.toString();
        return (this.rank.toString() + this.suit.toString());
    }
}