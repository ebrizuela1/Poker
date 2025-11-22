package PlayingCards;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private static final long serialVersionUID = 1L;
    private Suit suit; // Ace, Spade, Heart, Diamond
    private Rank rank; // 2, . . . J, Q, K
    public String imagePath;
    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
        this.imagePath = this.getPath();
    }

    @Override
    public String toString(){
        return (this.rank.toString() + this.suit.toString());
    }

    public String getPath(){
        return this.suit.toString() + "_" + this.rank.getPower() + ".png";
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