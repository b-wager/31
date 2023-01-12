import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.applet.Applet;
import java.awt.image.*;
import java.util.*;
import java.net.*;

public class TestGame extends Applet implements KeyListener, FocusListener, MouseListener
{

   ArrayList<String> grid = new ArrayList<String>();
   Graphics gBuffer;
	Image virtualMem;
   int mainX;
   int mainY;
   int movesX;
   int movesY;
   boolean jumping;
   boolean ceiling;
   int jumpDelta;
   int jumpTotal;
   int maxJump;
   int radius;
   Image picture;

   
   
	public void init()
	{
		virtualMem = createImage(800,600);
		gBuffer = virtualMem.getGraphics();
		addKeyListener(this);
		addFocusListener(this);
      addMouseListener(this);
      
      mainX = 100;
      mainY = 100;
      movesX = 200;
      movesY = 200;
      radius = 60;
      jumping = false;
      ceiling = false;
      jumpDelta = 20;
      jumpTotal = 0;
      maxJump = 240;
      
      picture = getImage(getDocumentBase(), "home1.png");
	}
   
	public void update(Graphics g)
	{
		virtualMem = createImage(800,800);
		gBuffer = virtualMem.getGraphics();
		paint(gBuffer);
		g.drawImage(virtualMem,0,0,this);
	}
   
	public void paint(Graphics g)
	{
      g.drawImage(picture,300,300,this);
		g.setColor(Color.black);
		g.drawString("Some Text",300,200);
		drawSomething(g);
      drawSomethingElse(g);
      drawSomethingMoves(g);
      animate();
		delay(30);
		repaint();
	}

	public void drawSomething(Graphics g)
	{
		gBuffer.setColor(Color.RED);
		gBuffer.fillRect(0,0,100,100);
      g.drawImage(virtualMem,0,0, this);
      
	}
   
   public void drawSomethingElse(Graphics g)
   {
   	g.setColor(Color.GREEN);
      gBuffer.fillRect(mainX, mainY, 100, 100);
      g.drawImage(virtualMem,0,0, this);
   }
   
   public void drawSomethingMoves(Graphics g)
   {
      g.setColor(Color.BLUE);
      gBuffer.fillOval(movesX, movesY, 20, 20);
      g.drawImage(virtualMem,0, 0, this);  
   }
   
   
   public void animate()
   {
   
   
      if(movesX >800)
         movesX = 0;
      movesX += 10;
      
      if(radius == 0)
         radius = 60;
      radius -=2;
      
      if(jumping && !ceiling)
      {
         mainY -= jumpDelta;
         jumpTotal += jumpDelta;
         if(jumpTotal == maxJump)
            ceiling = true;
      }
      else if(jumping && ceiling)
      {
         mainY += jumpDelta;
         jumpTotal -= jumpDelta;
         if(jumpTotal == 0)
            ceiling = false;
      }
      if(jumpTotal == 0)
         jumping = false;
      //System.out.println(jumpTotal);
      //System.out.println(ceiling);
      
   }
   
	public void delay(int n)
	{
		long startDelay = System.currentTimeMillis();
		long endDelay = 0;
		while (endDelay - startDelay < n)
			endDelay = System.currentTimeMillis();
	}
   
   public void jump()
   {
      if(!jumping)
      {
         jumping = true;
      }
   }
   
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {mainX -= 25;}
		if (key == KeyEvent.VK_DOWN) {mainY += 25;}
		if (key == KeyEvent.VK_RIGHT) {mainX += 25;}
		if (key == KeyEvent.VK_UP) {jump();}
	}
   
	public void focusGained(FocusEvent evt) {}
	public void focusLost(FocusEvent evt) {}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e) {}
   
   public void mousePressed(MouseEvent e)
   {
      System.out.println("CLICKED");
      mainX = e.getX();
      mainY = e.getY();

   }
   
   public void mouseReleased(MouseEvent e) {}   
   public void mouseEntered(MouseEvent e) {}
   public void mouseExited(MouseEvent e) {}
   public void mouseClicked(MouseEvent e) {}
}