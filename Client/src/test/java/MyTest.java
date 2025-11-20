import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MyTest {
    //
	@Test
	void test() {
		Deck myDeck = new Deck();
        ArrayList<Card> deck = myDeck.getDeck();
        for (Card card : deck){
            System.out.println( card.toString() );
        }
	}

}
