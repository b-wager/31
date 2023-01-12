import java.awt.*;

public class Card {
    private int num;
    private int suit; // 0 = hearts, 1 = diamonds, 2 = clubs, 3 = spades

    public Card(int n, int s) {
        num = n;
        suit = s;
    }

    public String toString() {
        String temp = "";
        if (num == 1)
            temp += "Ace of ";
        else if (num == 11)
            temp += "Jack of ";
        else if (num == 12)
            temp += "Queen of ";
        else if (num == 13)
            temp += "King of ";
        else
            temp += num + " of ";
        
        if (suit == 0)
            temp += "Hearts";
        if (suit == 1)
            temp += "Diamonds";
        if (suit == 2)
            temp += "Clubs";
        if (suit == 3)
            temp += "Spades";
    
        return temp;
    }

    public int getNum() {
        return num;
    }

    public int getSuit() {
        return suit;
    }
}