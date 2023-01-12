public class Deck {
    private Card[] cards;
    private int size;
    // 0 = hearts, 1 = diamonds, 2 = clubs, 3 = spades
    public Deck (int[] numList, int[] suitList) {
        size = numList.length * suitList.length;
        cards = new Card[size];
        int i = 0;
        for (int n=0; n<numList.length; n++)
            for (int s=0; s<suitList.length; s++) {
                cards[i] = new Card(numList[n], suitList[s]);
                i++;
            }
    }

    public Deck (int[] suitList) {
        size = 13 * suitList.length;
        cards = new Card[size];
        int i = 0;
        for (int n=1; n<=13; n++)
            for (int s=0; s<suitList.length; s++) {
                cards[i] = new Card(n, suitList[s]);
                i++;
            }
    }

    public Deck () {
        size = 52;
        cards = new Card[52];
        int i = 0;
        for (int n=1; n<=13; n++)
            for (int s=0; s<4; s++) {
                cards[i] = new Card(n, s);
                i++;
            }
    }

    public Card pullCard() {
        int rand = (int)(Math.random() * size);
        Card ans = cards[rand];
        for (int i=rand; i<size-1; i++)
            cards[i] = cards[i+1];
        size --;
        return ans;
    }

    public void addCard(Card card) {
        boolean notInDeck = true;
        for (int i=0; i<size; i++)
            if (cards[i].getNum() == card.getNum() && cards[i].getSuit() == card.getSuit())
                notInDeck = false;
        if (notInDeck && size != cards.length) {
            cards[size] = card;
            size++;
        }
        
    }

    public String toString() {
        String temp = "";
        for (int i=0; i<size; i++)
            temp += cards[i] + "\n";
        return temp;
    }
}