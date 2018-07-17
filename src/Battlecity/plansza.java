package Battlecity;

import java.awt.*;
import java.io.IOException;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Battlecity.mapa;
import Battlecity.gracz;

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class plansza extends JFrame implements KeyListener, ActionListener{

	
	public JPanel level = new mapa();
	Timer timer=new Timer(20, this);
	Timer resp = new Timer(2000, this);
	
	
	
	public plansza() {
		super("Battle City");
		((mapa) level).wczytaj_level();
		
		
		add(level);
		
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(255 , 233);
		pack();
		setVisible(true);
		timer.start();
		resp.start();
		this.setResizable(false);
		
	}
	

	
	
	
	public void keyReleased(KeyEvent evt) {
	
	}
	
	public void keyPressed(KeyEvent evt) {

		
		int c=evt.getKeyCode();
		int x = ((mapa) level).jeden.pozX;
		int y = ((mapa) level).jeden.pozY;
		switch(c){
			case KeyEvent.VK_RIGHT:
				((mapa) level).jeden.strona=3;
				((mapa) level).jeden.klatka=((mapa) level).jeden.klatka ^1;
				if(x <192 &&((mapa) level).przejazd_prawo(x,y)) {
					((mapa) level).jeden.pozX+=2;
					((mapa) level).poz_gracza.x+=2;

				}
				break;
			case KeyEvent.VK_UP:
				((mapa) level).jeden.strona=0;
				((mapa) level).jeden.klatka=((mapa) level).jeden.klatka ^1;
				if(y >0 && ((mapa) level).przejazd_gora(x,y)) {
					((mapa) level).jeden.pozY-=2;
					((mapa) level).poz_gracza.y-=2;
					
				}
				break;
			case KeyEvent.VK_LEFT:
				((mapa) level).jeden.strona=1;
				((mapa) level).jeden.klatka=((mapa) level).jeden.klatka ^1;
				if(x >0 &&((mapa) level).przejazd_lewo(x,y)) {
					((mapa) level).jeden.pozX-=2;
					((mapa) level).poz_gracza.x-=2;
					
				}
				break;
			case KeyEvent.VK_DOWN:
				((mapa) level).jeden.strona=2;
				((mapa) level).jeden.klatka=((mapa) level).jeden.klatka ^1;
				if(y <192 &&((mapa) level).przejazd_dol(x,y)) {
					((mapa) level).jeden.pozY+=2;
					((mapa) level).poz_gracza.y+=2;
					
				}
				break;
			case KeyEvent.VK_SPACE:
				if(((mapa) level).jeden.at ==null) {
				((mapa) level).jeden.at = new strzal(((mapa) level).jeden.pozX ,((mapa) level).jeden.pozY ,((mapa) level).jeden.strona ,true);
				((mapa) level).ogien.add(((mapa) level).jeden.at);
				}	
				
				break;
				
			case KeyEvent.VK_Q:
				((mapa) level).poziom++;
				((mapa) level).victory=true;
				
				break;
		}
	
		//level.repaint();
	
	}
	
	public void keyTyped(KeyEvent evt) {
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getSource()==timer){
		      repaint();
		      
		    }
		if(ev.getSource()==resp) {
			((mapa) level).spawn();
		}
		if(((mapa) level).victory) {
			int temp = ((mapa)level).zniszczenia;
			int temp2 = ((mapa)level).poziom;
			this.remove(level);
			level = null;
			level = new mapa();
			((mapa) level).zniszczenia =temp;
			((mapa) level).poziom=temp2;
			((mapa) level).wczytaj_level();
			this.add(level);
			this.setVisible(true);

		}
	}
	
}
