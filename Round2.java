import java.util.ArrayList;
import java.awt.*;

public class Round2 {
   Player[] players;
   Deck deck;
   Card[] mid = new Card[3];
   int turn = 0;
   int lastTurn = -1;
   boolean done = false;

   public Round2() {
      players = new Player[4];
      int[] numList = {1, 7, 8, 9, 10, 11, 12, 13};
      int[] suitList = {0, 1, 2, 3};
      deck = new Deck(numList, suitList);
      dealCards();
   }

   private void dealCards() {
      Card[] hand = new Card[3];
      for (int i=0; i<3; i++)
         hand[i] = deck.pullCard();
      players[0] = new Player(false, hand, "Brooke");
      for (int i=1; i<4; i++) {
         hand = new Card[3];
         for (int j=0; j<3; j++) {
            hand[j] = deck.pullCard();
         }
         players[i] = new Player(true, hand, "Player " + i);
      }
      for (int i=0; i<3; i++)
         mid[i] = deck.pullCard();
   }
   
   public Card[] getMid() {
      return mid;
   }
   
   public Player getPlayer(int n) {
      return players[n];
   }
   
   public int getTurn() {
      return turn;
   }
      
   public void start(boolean swap) {
      if (swap) {
         mid = players[turn].swapThree(mid);
         turn++;
         nextTurn();
      } else {
         turn++;
         nextTurn();
      }
   }
   
   public void nextTurn() {
      // Point to current player?
      // Print "Last Turn" if last turn
      Card[] move = players[turn].aiSwap(mid);
      if (move == null) {
         if (lastTurn == -1)
            lastTurn = turn;
      } else if (move[2] == null) {
         for (int i=0; i<3; i++) {
            if (mid[i].getNum() == move[0].getNum() && mid[i].getSuit() == move[0].getSuit()) {
               mid[i] = move[1];
            }
         }
      } else {
         mid = move;
         if (lastTurn == -1)
            lastTurn = turn;
      }
      turn = (turn+1) % players.length;
      if (turn == lastTurn)
         done = true;
   }

   
   public boolean end() {
      return done;
   }
   
   public void swapCard(int midCard, int playerCard) {
      mid[midCard] = players[turn].userSwap2(mid[midCard], playerCard);
   }
   
   public void swapThree() {
      mid = players[turn].swapThree(mid);
      if (lastTurn == -1)
         lastTurn = turn;
   }
}
