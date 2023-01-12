import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game31Applet extends Applet implements MouseListener {
   private Round2 play;
   private Image virtualMem;
   private Graphics gBuffer;
   private boolean start = false;
   private boolean end = false;
   private int mouseX;
   private int mouseY;
   private boolean justPressed = false;
   private int selectedMid;
   private int selectedHand;

   public void init() {
      addMouseListener(this);
      virtualMem = createImage(1900, 1000);
      gBuffer = virtualMem.getGraphics();
      play = new Round2();
   }
    
   public void update(Graphics g) {
      virtualMem = createImage(1900,1000);
      gBuffer = virtualMem.getGraphics();
      paint(gBuffer);
      g.drawImage(virtualMem,0,0,this);
   }
   
   public void paint(Graphics g) {
      table(g);
      if (!start)
         firstMove(g);
      repaint();
   }
   
   public void delay(int n) {
      long startDelay = System.currentTimeMillis();
      long endDelay = 0;
      while (endDelay - startDelay < n)
         endDelay = System.currentTimeMillis();
   }
   
   public void takeTurn(Graphics g) {
      if (play.getTurn() == 0) {
         g.setFont(new Font("Times New Roman",Font.PLAIN,40));
         g.setColor(Color.BLACK);
         g.drawString("Pass", 825, 714);
         g.drawString("Swap 3 and Pass", 775, 794);
         
         if (justPressed) {
            if (mouseX > 825 && mouseX < 1000 && mouseY > 687 && mouseY < 714) {}
               
            if (mouseX > 775 && mouseX < 1066 && mouseY > 768 && mouseY < 794) {}

            justPressed = false;
         }
      } else {
         play.nextTurn();
      }
      if (!play.end()) {
         takeTurn(g);
      }
   }
   
   public void firstMove(Graphics g) {
      g.setFont(new Font("Times New Roman",Font.PLAIN,40));
      g.setColor(Color.BLACK);
      g.drawString("Keep Hand", 825, 714);
      g.setFont(new Font("Times New Roman",Font.PLAIN,25));
      g.drawString("or", 900, 749);
      g.setFont(new Font("Times New Roman",Font.PLAIN,40));
      g.drawString("Swap with Middle", 775, 794);
      
      if (justPressed) {
         if (mouseX > 825 && mouseX < 1000 && mouseY > 687 && mouseY < 714) {
            play.start(false);
            start = true;
         }
         if (mouseX > 775 && mouseX < 1066 && mouseY > 768 && mouseY < 794) {
            play.start(true);
            start = true;
         }
         justPressed = false;
      }
   }
   
   public int selectMid() {
      return selectedMid;
   }
   
   private void table(Graphics g) {
      Image cardBackRotate = getImage(getDocumentBase(), "cardBackRotate.jpg");
      Image cardBack = getImage(getDocumentBase(), "cardBack.jpg");
      //Your hand
      drawCard(g, play.getPlayer(0).getHand()[0], 400, 674, true);
      drawCard(g, play.getPlayer(0).getHand()[1], 510, 674, true);
      drawCard(g, play.getPlayer(0).getHand()[2], 620, 674, true);
      //Mid
      drawCard(g, play.getMid()[0], 400, 387, start);
      drawCard(g, play.getMid()[1], 510, 387, start);
      drawCard(g, play.getMid()[2], 620, 387, start);
      //Player 1
      g.drawImage(cardBackRotate, 100, 300, this);
      g.drawImage(cardBackRotate, 100, 410, this);
      g.drawImage(cardBackRotate, 100, 520, this);
      //Player 2
      g.drawImage(cardBack, 400, 100, this);
      g.drawImage(cardBack, 510, 100, this);
      g.drawImage(cardBack, 620, 100, this);
      //Player 3
      g.drawImage(cardBackRotate, 874, 300, this);
      g.drawImage(cardBackRotate, 874, 410, this);
      g.drawImage(cardBackRotate, 874, 520, this);
   }
   
   private void drawCard(Graphics g, Card c, int x, int y, boolean faceUp) {
      Image cardBack = getImage(getDocumentBase(), "cardBack.jpg");
      g.setFont(new Font("Times New Roman",Font.PLAIN,40));
      g.setColor(Color.WHITE);
      g.fillRect(x, y, 100, 146);
      g.setColor(Color.BLACK);
      g.drawRect(x, y, 100, 146);
      Image suit = getImage(getDocumentBase(), "heart.png");
      if (c.getNum() == 1)
         g.drawString("A", x+35, y+83);
      else if (c.getNum() >= 7 && c.getNum() <= 9)
         g.drawString(""+c.getNum(), x+40, y+83);
      else if (c.getNum() == 10)
         g.drawString("10", x+30, y+83);
      else if (c.getNum() == 11)
         g.drawString("J", x+40, y+83);
      else if (c.getNum() == 12)
         g.drawString("Q", x+35, y+83);
      else
         g.drawString("K", x+35, y+83);
      
      if (c.getSuit() == 1)
         suit = getImage(getDocumentBase(), "diamond.png");
      if (c.getSuit() == 2)
         suit = getImage(getDocumentBase(), "club.png");
      if (c.getSuit() == 3)
         suit = getImage(getDocumentBase(), "spade.png");
      g.drawImage(suit, x+5, y+5, this);
      g.drawImage(suit, x+70, y+116, this);
      
      if (!faceUp)
         g.drawImage(cardBack, x, y, this);
   }
   
   public void mousePressed(MouseEvent e) {
      mouseX = e.getX();
      mouseY = e.getY();
      justPressed = true;
      System.out.println(mouseX + ", " + mouseY);
   }
   
   public void mouseReleased(MouseEvent e) {}   
   public void mouseEntered(MouseEvent e) {}
   public void mouseExited(MouseEvent e) {}
   public void mouseClicked(MouseEvent e) {}
}