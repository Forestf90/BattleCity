package Battlecity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class wrog {
 public int poz_X;
 public int poz_Y=0;
 BufferedImage[][] kierunki ;
 BufferedImage ikony;
 public int strona;
 public int klatka;
 public int przeladowanie;
 strzal at;
	
	public wrog(int x){
		
	File sciezka = new File("./resource/images/icon2.png");
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
		poz_X=x;
	}
	
	public void wytnij(BufferedImage x) {
		
		strona =2;
		klatka=0;
		przeladowanie=0;
		kierunki = new BufferedImage[4][2];
		for(int i=0 ; i<kierunki.length ; i++){
			
			kierunki[i][0]=x.getSubimage(128+(i*16*2), 0, 16, 16);
			kierunki[i][1]=x.getSubimage((i*16*2)+144, 0, 16, 16);
			
			
			
		}

			
	}
	
	public BufferedImage rysuj() {
		 
		return kierunki[this.strona][this.klatka];
	
		}
}
