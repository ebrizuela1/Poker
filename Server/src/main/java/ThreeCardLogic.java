import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ThreeCardLogic {
    // Returns int based on hand type
    /* -1 -> something is wrong
    *   0 -> high card?
    *   1 -> pair
    *   2 -> flush
    *   3 -> straight
    *   4 -> three of kind
    *   5 -> straight flush
    * */
    public static int evalHand(ArrayList<PlayingCards.Card> hand){
        // catch improper hand size for three card poker
        if(hand.size() != 3){return -1;}
        Collections.sort(hand);
        if(hasStraightFlush(hand)) return 5;
        if(hasThreeOfKind(hand)) return 4;
        if(hasStraight(hand)) return 3;
        if(hasFlush(hand)) return 2;
        if(hasPair(hand)) return 1;

        return 0;
    }
    // Returns the amount won based on the hand and the bet for pair plus
    public static int evalPPlWinnings(ArrayList<PlayingCards.Card> hand, int bet){
        int handRank = evalHand(hand);
        switch(handRank){
            case 5: return bet * 40 + bet;
            case 4: return bet * 30 + bet;
            case 3: return bet * 6 + bet;
            case 2: return bet * 3 + bet;
            case 1: return bet * 2;
            default: return 0;
        }
    }
    /// Return val: 0 for neither, 1 if dealer wins, 2 if player wins
    public static int compareHands(ArrayList<PlayingCards.Card> dealer, ArrayList<PlayingCards.Card> player) {
        int dRank = evalHand(dealer);
        int pRank = evalHand(player);

        if(dRank > pRank) return 1;
        if(dRank < pRank) return 2;

        switch(dRank){
            case 5:
            case 3:
                int dSum = 0;
                int pSum = 0;
                for(PlayingCards.Card card : dealer)
                    dSum += card.getRank().getPower();
                for(PlayingCards.Card card : player)
                    pSum += card.getRank().getPower();

                if (dSum == pSum) return 0;
                return dSum >  pSum ? 1 : 2;
            case 4:

            case 2:
            case 1:
            default:
                return 0;
        }
    }

    /// private static method finding if hand contains pair
    private static boolean hasPair(ArrayList<PlayingCards.Card> hand){
//        Collections.sort(hand);
        return (hand.get(0).getRank() == hand.get(1).getRank()) ||
                (hand.get(1).getRank() == hand.get(2).getRank());
    }

    /// check for flush
    private static boolean hasFlush(ArrayList<PlayingCards.Card> hand){
        return (Objects.equals(hand.get(0).getSuit().getPower(), hand.get(1).getSuit().getPower()) &&
                Objects.equals(hand.get(1).getSuit().getPower(), hand.get(2).getSuit().getPower()));
    }

    /// check if hand contains a straight and account for straights containing ace since
    /// it can be A23 or QKA
    private static boolean hasStraight(ArrayList<PlayingCards.Card> hand){
//        Collections.sort(hand);
        return  (hand.get(0).getRank().getPower() == 2 && hand.get(1).getRank().getPower() == 3
                && hand.get(2).getRank().getPower() == 14) ||
                (hand.get(0).getRank().getPower() + 1 == hand.get(1).getRank().getPower() &&
                hand.get(1).getRank().getPower() + 1 == hand.get(2).getRank().getPower());
    }

    /// straight flush means hand would return true for both straight and flush
    private static boolean hasStraightFlush(ArrayList<PlayingCards.Card> hand){
        return hasStraight(hand) && hasFlush(hand);
    }

    /// all cards have the same rank
    private static boolean hasThreeOfKind(ArrayList<PlayingCards.Card> hand) {
//        Collections.sort(hand);
        return hand.get(0).getRank() == hand.get(1).getRank() && hand.get(1).getRank() == hand.get(2).getRank();
    }
}
