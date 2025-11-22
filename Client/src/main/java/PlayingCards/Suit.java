package PlayingCards;
public enum Suit{
    hearts("H"),
    diamonds("D"),
    spades("S"),
    clubs("C");

    private final String power;
    Suit(String power){
        this.power = power;
    }
    public String getPower(){
        return power;
    };
}