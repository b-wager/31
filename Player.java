import java.util.Scanner;
import java.awt.*;

public class Player {
   private boolean robot;
   private Card[] hand;
   private String name;
   private Scanner input = new Scanner(System.in);

   public Player (boolean r, Card[] h, String n) {
      robot = r;
      hand = h;
      name = n;
   }
   
   public void dealHand (Card[] h) {
      hand = h;
   }

   public Card[] getHand() {
      return hand;
   }

   public String getName() {
      return name;
   }

   public String toString() {
      String h = "";
      for (int i=0; i<2; i++)
         h += (hand[i].toString() + " | ");
      h += hand[2].toString();
      return (name + " has a hand of " + h);
   }

   public boolean isRobot() {
      return robot;
   }

   public Card userSwap(Card mid) {
      while (true) {
         System.out.println("Choose a card to swap:");
         for (int i=0; i<2; i++)
            System.out.print(hand[i] + " | ");
         System.out.println(hand[2]);
         System.out.print("Input card number (Ace=1, Jack=11, Queen=12, King=13): ");
         int num = input.nextInt();
         System.out.print("Input card suit (Hearts=0, Diamonds=1, Clubs=2, Spades=3): ");
         int suit = input.nextInt();
         System.out.println();
         for (int i=0; i<3; i++) {
            if (hand[i].getNum() == num && hand[i].getSuit() == suit) {
               Card old = hand[i];
               hand[i] = mid;
               return old;
            }
         }
      }
   
   }
   
   public Card userSwap2(Card mid, int swap) {
      Card old = hand[swap];
      hand[swap] = mid;
      return old;
   }

   public Card[] swapThree(Card[] m) {
      Card[] old = new Card[3];
      for (int i=0; i<3; i++) {
         old[i] = hand[i];
         hand[i] = m[i];
      }
      return old;
   }

   public double calculateScore() {
      double max = 0;
      double score;
      if (hand[0].getNum() == hand[1].getNum() && hand[0].getNum() == hand[2].getNum()) {
         if (hand[0].getNum() == 1)
            return 32;
         return 30.5;
      }
      for (int i=0; i<4; i++) {
         score = 0;
         for (Card card:hand) {
            if (card.getSuit() == i) {
               if (card.getNum() >= 10)
                  score += 10;
               else if (card.getNum() == 1)
                  score += 11;
               else
                  score += card.getNum();
            }
         }
         if (score > max)
            max = score;
      }
      return max;
   }

   public Card[] aiSwap(Card[] m) {
      double maxScore = calculateScore();
      Card[] holder = new Card[3];
      for (Card mCard:m) {
         for (int i=0; i<3; i++) {
            Card temp = hand[i];
            hand[i] = mCard;
            if (calculateScore() > maxScore) {
               maxScore = calculateScore();
               holder[0] = mCard;
               holder[1] = temp;
            }
            hand[i] = temp;
         }
      }
      Card[] temp = swapThree(m);
      if (calculateScore() > maxScore) {
         maxScore = calculateScore();
         holder = temp;
      }
      swapThree(temp);
      if (holder[2] != null) {
         hand = m;
         System.out.println("Swap three and pass");
         return holder;
      }
      else if (holder[0] != null) {
         for (int i=0; i<3; i++) {
            if (hand[i].getNum() == holder[1].getNum() && hand[i].getSuit() == holder[1].getSuit()) {
               hand[i] = holder[0];
            }
         }
         System.out.println("Replace " + holder[0] + " with " + holder[1]);
         return holder;
      }
      System.out.println("Pass");
      return null;
   }
}