import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import PlayingCards.*;

class ThreeCardLogicTest {
    // CARD(SUIT,RANK)
    private ArrayList<Card> buildHand(Card c1, Card c2, Card c3) {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(c1);
        hand.add(c2);
        hand.add(c3);
        return hand;
    }

    @Test
    void testEvalHandBuggy(){
        ArrayList<Card> hand = new ArrayList<>();
        assertEquals(-1, ThreeCardLogic.evalHand(hand), "should return -1");
        hand = buildHand(new Card(Suit.clubs,Rank.Jack),new Card(Suit.clubs,Rank.Jack),new Card(Suit.clubs,Rank.Jack));
        assertEquals(4, ThreeCardLogic.evalHand(hand), "should return 4");
        hand.add(new Card(Suit.clubs,Rank.Jack));
        assertEquals(-1, ThreeCardLogic.evalHand(hand), "should return -1");
    }

    @Test
    void testEvalHandStraightFlush(){
        ArrayList<Card> hand = buildHand(
                new Card(Suit.hearts, Rank.nine),
                new Card(Suit.hearts, Rank.ten),
                new Card(Suit.hearts, Rank.Jack)
        );
        assertEquals(5, ThreeCardLogic.evalHand(hand), "Should identify Straight Flush");
        hand = buildHand(
                new Card(Suit.hearts, Rank.Queen),
                new Card(Suit.hearts, Rank.King),
                new Card(Suit.hearts, Rank.Ace)
        );
        assertEquals(5, ThreeCardLogic.evalHand(hand), "QKA straight flush");
        hand = buildHand(
                new Card(Suit.hearts, Rank.Ace),
                new Card(Suit.hearts, Rank.two),
                new Card(Suit.hearts, Rank.three)
        );
        assertEquals(5, ThreeCardLogic.evalHand(hand), "A23 straight flush");
    }

    @Test
    void testEvalHandThreeOfKind(){
        ArrayList<Card> hand = buildHand(
                new Card(Suit.hearts, Rank.nine),
                new Card(Suit.spades, Rank.nine),
                new Card(Suit.clubs, Rank.nine)
        );
        assertEquals(4, ThreeCardLogic.evalHand(hand), "Should identify Three of a Kind");
    }

    @Test
    void testEvalHandStraight(){
        ArrayList<Card> hand = buildHand(
                new Card(Suit.hearts, Rank.ten),
                new Card(Suit.spades, Rank.Jack),
                new Card(Suit.clubs, Rank.Queen)
        );
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Should identify straight 10 JQ");
        hand = buildHand(
                new Card(Suit.hearts, Rank.Ace),
                new Card(Suit.spades, Rank.King),
                new Card(Suit.clubs, Rank.Queen)
        );
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Should identify straight AKQ");
        hand = buildHand(
                new Card(Suit.hearts, Rank.Ace),
                new Card(Suit.spades, Rank.two),
                new Card(Suit.clubs, Rank.three)
        );
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Should identify straight A23");
    }

    @Test
    void testEvalHandFlush(){
        ArrayList<Card> hand = buildHand(
                new Card(Suit.hearts, Rank.nine),
                new Card(Suit.hearts, Rank.seven),
                new Card(Suit.hearts, Rank.Jack)
        );
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Should identify hearts Flush");
        hand = buildHand(
                new Card(Suit.spades, Rank.Queen),
                new Card(Suit.spades, Rank.three),
                new Card(Suit.spades, Rank.Ace)
        );
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Should identify spades Flush");
        hand = buildHand(
                new Card(Suit.clubs, Rank.Ace),
                new Card(Suit.clubs, Rank.eight),
                new Card(Suit.clubs, Rank.three)
        );
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Should identify clubs Flush");
        hand = buildHand(
                new Card(Suit.diamonds, Rank.Ace),
                new Card(Suit.diamonds, Rank.eight),
                new Card(Suit.diamonds, Rank.three)
        );
        assertEquals(2, ThreeCardLogic.evalHand(hand), "Should identify diamond Flush");
    }

    @Test
    void testEvalHandPair(){
        ArrayList<Card> hand = buildHand(
                new Card(Suit.hearts, Rank.nine),
                new Card(Suit.spades, Rank.seven),
                new Card(Suit.hearts, Rank.nine)
        );
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Pair at 0 1");
        hand = buildHand(
                new Card(Suit.spades, Rank.Queen),
                new Card(Suit.diamonds, Rank.three),
                new Card(Suit.spades, Rank.Queen)
        );
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Pair at 0 2");
        hand = buildHand(
                new Card(Suit.clubs, Rank.Ace),
                new Card(Suit.hearts, Rank.eight),
                new Card(Suit.clubs, Rank.eight)
        );
        assertEquals(1, ThreeCardLogic.evalHand(hand), "Pair at 1 2");
    }

    @Test
    void testEvalHandHighCard(){
        ArrayList<Card> hand = buildHand(
                new Card(Suit.hearts, Rank.nine),
                new Card(Suit.spades, Rank.seven),
                new Card(Suit.clubs, Rank.Ace)
        );
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Should identify High Card");
    }
}