import java.util.ArrayList;
import java.util.Scanner;

public class Round {
    Player[] players;
    Deck deck;
    Card[] mid = new Card[3];
    int turn = 0;
    int lastTurn = -1;
    Scanner input = new Scanner(System.in);

    public Round(int n) {
        int[] numList = {1, 7, 8, 9, 10, 11, 12, 13};
        int[] suitList = {0, 1, 2, 3};
        deck = new Deck(numList, suitList);
        if (n > 9)
            n = 9;
        players = new Player[n];
        dealCards(n);
        start();
    }

    public Round() {
        int[] numList = {1, 7, 8, 9, 10, 11, 12, 13};
        int[] suitList = {0, 1, 2, 3};
        deck = new Deck(numList, suitList);
        players = new Player[4];
        dealCards(4);
        start();
    }

    private void dealCards(int n) {
        Card[] hand = new Card[3];
        for (int i=0; i<3; i++)
            hand[i] = deck.pullCard();
        players[0] = new Player(false, hand, "Brooke");
        for (int i=1; i<n; i++) {
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

    public String toString() {
        String temp = "";
        temp += "Number of Players: " + players.length + "\n";
        for (int i=0; i<3; i++) {
            temp += mid[i].toString();
            if (i<2)
                temp += ",";
            temp += " ";
        }
        return temp;
    }

    public Player getPlayer(int n) {
        return players[n];
    }

    public void start() {
        System.out.println(players[turn]);
        System.out.print("Type 1 to keep your hand or 2 to swap with the middle ");
        int move = input.nextInt();
        if (move == 2) {
            mid = players[turn].swapThree(mid);
            turn++;
            nextTurn();
        }
        else {
            turn++;
            nextTurn();
        }
    }

    public void nextTurn() {
        System.out.println("\n" + players[turn].getName() + "'s turn");
        System.out.print("Middle Cards: ");
        for (int i=0; i<2; i++)
            System.out.print(getMid()[i] + " | ");
        System.out.println(getMid()[2]);
        if (lastTurn >= 0)
            System.out.println("Last turn");
        if (players[turn].isRobot()) {
            Card[] move = players[turn].aiSwap(mid);
            if (move == null) {
                if (lastTurn == -1)
                    lastTurn = turn;
            }
            else if (move[2] == null) {
                for (int i=0; i<3; i++) {
                    if (mid[i].getNum() == move[0].getNum() && mid[i].getSuit() == move[0].getSuit()) {
                        mid[i] = move[1];
                    }
                }
            }
            else {
                mid = move;
                if (lastTurn == -1)
                    lastTurn = turn;
            }
        }
        else {
            System.out.println(players[turn]);
            System.out.print("Type 1 to continue, 2 to pass, or 3 to swap three and pass ");
            int move = input.nextInt();
            System.out.println();
            if (move==3)
                swapThree();
            else if (move==1)
                swapCard();
            else if (move==2)
                if (lastTurn == -1)
                    lastTurn = turn;
        }
        turn = (turn+1) % players.length;
        if (turn == lastTurn)
            end();
        else
            nextTurn();
    }

    public void swapCard() {
        boolean done = false;
        while (!done) {
            System.out.println("Choose a card to swap:");
            for (int i=0; i<2; i++)
                System.out.print(mid[i] + " | ");
            System.out.println(mid[2]);
            System.out.print("Input card number (Ace=1, Jack=11, Queen=12, King=13): ");
            int num = input.nextInt();
            System.out.print("Input card suit (Hearts=0, Diamonds=1, Clubs=2, Spades=3): ");
            int suit = input.nextInt();
            System.out.println();
            for (int i=0; i<3; i++) {
                if (mid[i].getNum() == num && mid[i].getSuit() == suit) {
                    mid[i] = players[turn].userSwap(mid[i]);
                    done = true;
                }
            }
            if (!done)
                System.out.println("Not a valid card. Try again");
        }
    }

    public void swapThree() {
        mid = players[turn].swapThree(mid);
        if (lastTurn == -1)
            lastTurn = turn;
    }

    public void end() {
        double max = 0;
        ArrayList<Player> winners = new ArrayList<Player>();
        System.out.println();
        for (Player player:players) {
            System.out.println(player.getName() + "'s score is " + player.calculateScore());
            if (player.calculateScore() > max)
                max = player.calculateScore();
        }
        for (Player player:players)
            if (player.calculateScore() == max)
                winners.add(player);
        if (winners.size() > 1) {
            for (int i=0; i<winners.size()-1; i++)
                System.out.print(winners.get(i).getName() + " and ");
            System.out.println(winners.get(winners.size()-1).getName() + " win!");
        }
        else
            System.out.println("\n" + winners.get(0).getName() + " wins!");
        input.close();
    }
}