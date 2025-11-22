import PlayingCards.Card;
import PlayingCards.Deck;
import PlayingCards.Rank;
import PlayingCards.Suit;
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
            System.out.println( card.toString() + " = " + card.getSuitRank());
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

    @Test
    void simplePairCheck(){ // Hand = [ S2, 4C, 2C ]
        ArrayList<Card> clientHand = new ArrayList<>();
        clientHand.add(new Card(Suit.Spades, Rank.two));
        clientHand.add(new Card(Suit.Clubs, Rank.two));
        clientHand.add(new Card(Suit.Clubs, Rank.four));
        PokerInfo game = new PokerInfo();
        Assertions.assertTrue(game.hasPair(clientHand));
    }

    @Test
    void noPairCheck(){ // Hand = [ S3, 4C, 2C ]
        ArrayList<Card> clientHand = new ArrayList<>();
        clientHand.add(new Card(Suit.Spades, Rank.three));
        clientHand.add(new Card(Suit.Clubs, Rank.two));
        clientHand.add(new Card(Suit.Clubs, Rank.four));
        PokerInfo game = new PokerInfo();
        Assertions.assertFalse(game.hasPair(clientHand));
    }

    @Test
    void tripleCheck(){
        ArrayList<Card> clientHand = new ArrayList<>();
        clientHand.add(new Card(Suit.Spades, Rank.three));
        clientHand.add(new Card(Suit.Clubs, Rank.three));
        clientHand.add(new Card(Suit.Diamonds, Rank.three));
        PokerInfo game = new PokerInfo();
        Assertions.assertTrue(game.hasTriple(clientHand));
    }

    @Test
    void straightCheck(){
        ArrayList<Card> clientHand = new ArrayList<>();
        clientHand.add(new Card(Suit.Spades, Rank.three));
        clientHand.add(new Card(Suit.Clubs, Rank.four));
        clientHand.add(new Card(Suit.Diamonds, Rank.five));
        PokerInfo game = new PokerInfo();
        Assertions.assertTrue(game.hasStraight(clientHand));
    }

    @Test
    void flushCheck(){
        ArrayList<Card> clientHand = new ArrayList<>();
        clientHand.add(new Card(Suit.Spades, Rank.three));
        clientHand.add(new Card(Suit.Spades, Rank.four));
        clientHand.add(new Card(Suit.Spades, Rank.five));
        PokerInfo game = new PokerInfo();
        Assertions.assertTrue(game.hasFlush(clientHand));
    }

    @Test
    void straightFlushCheck(){
        ArrayList<Card> clientHand = new ArrayList<>();
        clientHand.add(new Card(Suit.Spades, Rank.three));
        clientHand.add(new Card(Suit.Spades, Rank.four));
        clientHand.add(new Card(Suit.Spades, Rank.five));
        PokerInfo game = new PokerInfo();
        Assertions.assertTrue(game.hasStraightFlush(clientHand));
    }
}
