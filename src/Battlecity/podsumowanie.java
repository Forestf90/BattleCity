package Battlecity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class podsumowanie extends JPanel implements KeyListener {

	public boolean zwyciestwo;
	BufferedImage wznacznik;
	public int co;
	Rectangle[] pozycje;
	public podsumowanie( BufferedImage w) {
		//zwyciestwo = t;
		setPreferredSize(new Dimension( 240, 208 ));
		addKeyListener(this);
		 pozycje = new Rectangle[2];
		pozycje[0] = new Rectangle(122,122,16,16); 
		pozycje[1] = new Rectangle(122, 154,16,16);
		wznacznik=w;
		this.setBackground(Color.BLACK);
	}
	
	public void paint(Graphics g) {
		g.drawImage(wznacznik , pozycje[co].x , pozycje[co].x , null);
	}
	@Override
	public void keyPressed(KeyEvent evt) {
		int c=evt.getKeyCode();
		
		switch(c){
			case KeyEvent.VK_RIGHT:
				co ^=1;
				break;
			case KeyEvent.VK_UP:
				co ^=1;
				break;
			case KeyEvent.VK_ENTER:
				if(c==0) {
					
				}
			}
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
