package Battlecity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class gracz {

	public int pozX=66;
	public int pozY=192;
	BufferedImage[][] kierunki ;
	BufferedImage ikony;
	public int strona;
	public int klatka;
	strzal at;
	
	public gracz(){
		
	File sciezka = new File("images/icon.png");
		try {
		ikony  = ImageIO.read(sciezka);
		}
		catch(IOException e)
		{
			//nie wiem
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
			
		}
		
		wytnij(ikony);
	}
	
	public void wytnij(BufferedImage x) {
		
		strona =0;
		klatka=0;
		kierunki = new BufferedImage[4][2];
		for(int i=0 ; i<kierunki.length ; i++){
			
			kierunki[i][0]=x.getSubimage(i*16*2, 0, 16, 16);
			kierunki[i][1]=x.getSubimage((i*16*2)+16, 0, 16, 16);
			
			
		}

			
	}
	
	public BufferedImage rysuj() {
		 
		return kierunki[this.strona][this.klatka];
	
		}
		

	
			
		
	
}
