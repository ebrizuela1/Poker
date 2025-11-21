import PlayingCards.Card;
import PlayingCards.Deck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MyTest {
    //
	@Test
	void initDeck() {
		Deck myDeck = new Deck();
        ArrayList<Card> deck = myDeck.getDeck();
        for (Card card : deck){
            System.out.println( card.toString() );
        }
        Assertions.assertEquals(52, myDeck.getDeck().size());
	}
    @Test
    void checkShuffle(){
        Deck myDeck = new Deck();
        if (!myDeck.getDeck().isEmpty()) {
            Card firstPull = myDeck.getDeck().get(0);


            myDeck.shuffle();

            Card afterShuffle= myDeck.getDeck().get(0);
            // Checked, unique shuffle
            System.out.println("Before shuffle:" + firstPull.toString() + "  After shuffle:" + afterShuffle);
            Assertions.assertNotEquals(firstPull, afterShuffle);
        }
    }

    @Test
    void handSort(){
        PokerInfo game = new PokerInfo();
        game.drawClient();

        ArrayList<Card> clientCards = game.getClientHand();

        System.out.println("Unsorted : " + clientCards);
        game.sortHand(clientCards);
        System.out.println("Sorted : " + clientCards);
        Assertions.assertNotEquals(clientCards, game.getClientHand());
    }

}
