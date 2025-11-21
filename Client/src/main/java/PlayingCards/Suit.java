package PlayingCards;
public enum Suit{
    Hearts("H"),
    Diamonds("D"),
    Spades("S"),
    Clubs("C");

    private final String power;
    Suit(String power){
        this.power = power;
    }
    public String getPower(){
        return power;
    };
}