import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
        assertEquals(-1, ThreeCardLogic.evalHand(hand));
        hand = buildHand(new Card(Suit.clubs,Rank.Jack),new Card(Suit.clubs,Rank.Jack),new Card(Suit.clubs,Rank.Jack));
        assertEquals(4, ThreeCardLogic.evalHand(hand));
        hand.add(new Card(Suit.clubs,Rank.Jack));
        assertEquals(-1, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void testEvalHandStraightFlush(){

    }

    @Test
    void testEvalHandThreeOfKind(){}

    @Test
    void testEvalHandStraight(){}

    @Test
    void testEvalHandFlush(){}

    @Test
    void testEvalHandPair(){}

    @Test
    void testEvalHandHighCard(){}
}