package PlayingCards;

import java.io.Serializable;

public enum Suit implements Serializable {
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
    private static final long serialVersionUID = 1L;
}