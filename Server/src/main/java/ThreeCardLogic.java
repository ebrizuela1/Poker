import javax.smartcardio.Card;
import java.util.ArrayList;

public class ThreeCardLogic {
    // Returns int based on hand type
    /* -1 -> something is wrong
    *   0 -> high card?
    *   1 ->
    * */
    public static int evalHand(ArrayList<Card> hand){
        if(hand.isEmpty()){return -1;}

        if () {

        }
        return 0;
    }
    // Returns the amount won based on the hand and the bet
    public static int evalPPlWinnings(ArrayList<Card> hand, int bet){
        int handRank = evalHand(hand);
        switch(handRank){
            case 1: return bet * 40 + bet;
            case 2: return bet * 30 + bet;
            case 3: return bet * 6 + bet;
            case 4: return bet * 3 + bet;
            case 5: return bet * 2;
            default: return 0;
        }
    }
    // Return val: 0 for neither, 1 if dealer wins, 2 if player wins
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        return 0;
    }
}
