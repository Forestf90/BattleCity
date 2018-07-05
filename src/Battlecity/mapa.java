package Battlecity;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Battlecity.gracz;

import java.util.Scanner;

public class mapa  extends JPanel {
	
	public gracz jeden;
	public Rectangle[] sektor;
	public int[] sektor_id;
	BufferedImage[] sciany;
	BufferedImage ikony;
	//BufferedImage[] pociski;
	//Rectangle pocisk;
	
	
	public mapa() {
		jeden = new gracz();
		setPreferredSize(new Dimension( 240, 208 ));
		//pociski= new BufferedImage[4];
		sciany= new BufferedImage[4];
		wczytaj_tekstury();
		wczytaj_level();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		//this.setBackground(Color.BLACK);
		g.setColor(Color.BLACK);
		g.fillRect(0 , 0  ,208 ,208);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(208 , 0  ,32 ,208);


		for(int i=0 ; i<sektor.length ; i++) {
			
			g.drawImage(sciany[sektor_id[i]], sektor[i].x, sektor[i].y,
					sektor[i].width ,sektor[i].height,  null);
		}
		
		BufferedImage temp = ((gracz) jeden).rysuj();
		g.drawImage(temp,((gracz) jeden).pozX ,((gracz) jeden).pozY , null);
		
		if(jeden.at !=null) {
			g.fillRect(jeden.at.poz_X, jeden.at.poz_Y, 2,2);
			jeden.at.lot();
			trafienie(jeden.at.poz_X , jeden.at.poz_Y);
		}
		
	}
	public void wczytaj_tekstury() {
		
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
		sciany[0]= ikony.getSubimage((16*19), 32, 16, 16); //orzel
		sciany[1]=ikony.getSubimage((16*16), 0, 16, 16); //cegla
		sciany[2]=ikony.getSubimage((16*17)+8, 0, 8, 16); //cegla_pion
		sciany[3]=ikony.getSubimage((16*18), 8, 16, 8); //cegla_poziom
		
		
		
		//pociski[0]=ikony.getSubimage((16*19), 32, 16, 16);
		
	}
	
	public void wczytaj_level() {
	       try {
	            File f = new File("level1.txt");
	            
				Scanner sc = new Scanner(f);
	            
	            int count = 0;
	            while (sc.hasNextLine()) {
	                count++;
	                sc.nextLine();
	            }
	            sc.close();
	            sc=null;
	            sektor = new Rectangle[count];
	            sektor_id= new int[count];
	            

	        } catch (FileNotFoundException e) {         
	            e.printStackTrace();
	        }
	       
	       try {
	            File f = new File("level1.txt");
	            
				Scanner sc = new Scanner(f);

	            int i=0;
	            while(sc.hasNextLine()){
	                String line = sc.nextLine();
	                String[] dane = line.split(",");
	                 //= details[0];
	                sektor_id[i]= Integer.valueOf(dane[0]);
	                sektor[i] = new Rectangle(Integer.valueOf(dane[1]),Integer.valueOf(dane[2]),Integer.valueOf(dane[3]), Integer.valueOf(dane[4]));
	                		i++;

	            }

	        } catch (FileNotFoundException e) {         
	            e.printStackTrace();
	        }
		
	       
	}
	
	public boolean przejazd_lewo() {
		
		for(int i=0 ; i<sektor.length ;i++) {
			for(int j=0 ; j<=16 ; j++) {
				if(sektor[i].contains((jeden.pozX)-2, (jeden.pozY+j))) return false;
				
			}
		}
		return true;
	}
	
	public boolean przejazd_gora() {
		
		for(int i=0 ; i<sektor.length ;i++) {
			for(int j=0 ; j<=16 ; j++) {
				if(sektor[i].contains((jeden.pozX+j), (jeden.pozY)-2)) return false;
				
			}
		}
		return true;
	}
		
		public boolean przejazd_prawo() {
			
			for(int i=0 ; i<sektor.length ;i++) {
				for(int j=0 ; j<=16 ; j++) {
					if(sektor[i].contains((jeden.pozX)+2+16, (jeden.pozY+j))) return false;
					
				}
			}
			return true;
		}
		
		public boolean przejazd_dol() {
			
			for(int i=0 ; i<sektor.length ;i++) {
				for(int j=0 ; j<=16 ; j++) {
					if(sektor[i].contains((jeden.pozX+j), (jeden.pozY)+2+16)) return false;
					
				}
			}
		
		
		return true;
	}
		
		
		public void trafienie(int x , int y) {
			if(x >= 208 || x<=0) {
				jeden.at=null;
				return;
			}
			else if (y>=208 || y<=0) {
				jeden.at=null;
				return;
			}
			
			for(int i=0 ; i<sektor.length ;i++) {
					if(sektor[i].contains(x ,y)) {
						jeden.at =null;
						sektor[i].width -=4;
					}
					
				
			}

		}

}
