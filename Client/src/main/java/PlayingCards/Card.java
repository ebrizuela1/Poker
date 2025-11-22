package PlayingCards;

public class Card implements Comparable<Card> {
    private Suit suit; // Ace, Spade, Heart, Diamond
    private Rank rank; // 2, . . . J, Q, K

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString(){
        return (this.rank.toString() + this.suit.toString());
    }

    public String getSuitRank(){
        return (this.suit.getPower() + this.rank.getPower());
    }

    @Override
    public int compareTo(Card other) {
        int rankComparison = Integer.compare(this.rank.getPower(), other.rank.getPower());
        if (rankComparison == 0){ return this.suit.compareTo(other.suit);} // Ranks equal, Comp Suit
        return rankComparison;
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }
}