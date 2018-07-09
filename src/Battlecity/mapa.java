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
import java.util.ArrayList;

public class mapa  extends JPanel {
	
	public gracz jeden;
	public Rectangle[] sektor;
	public int[] sektor_id;
	
	BufferedImage[] sciany;
	BufferedImage[] wybuchy;
	BufferedImage[] eksplozje;
	BufferedImage ikony;
	public BufferedImage[] sciana_sektora;
	//BufferedImage[] pociski;
	//Rectangle pocisk;
	//ArrayList<Rectangle> wyburzony = new ArrayList<Rectangle>();
	ArrayList<wybuch> trafienia;
	ArrayList<zniszczenie> rip;
	
	
	public mapa() {
		jeden = new gracz();
		setPreferredSize(new Dimension( 240, 208 ));
		//pociski= new BufferedImage[4];
		sciany= new BufferedImage[10];
		wybuchy = new BufferedImage[3];
		eksplozje = new BufferedImage[2];
		trafienia = new ArrayList<wybuch>();
		rip = new ArrayList<zniszczenie>();
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


		BufferedImage temp = ((gracz) jeden).rysuj();
		g.drawImage(temp,((gracz) jeden).pozX ,((gracz) jeden).pozY , null);
		
		for(int i=0 ; i<sektor.length ; i++) {
			
			g.drawImage(sciana_sektora[i], sektor[i].x, sektor[i].y,
					sektor[i].width ,sektor[i].height,  null);
		}
		
		
		
		
		if(jeden.at !=null) {
			g.setColor(Color.GRAY);
			g.fillRect(jeden.at.poz_X, jeden.at.poz_Y, 2,2);
			jeden.at.lot();
			trafienie(jeden.at.poz_X , jeden.at.poz_Y);
		}
		
		for(wybuch w : trafienia) {
			if(w.klatka==3) {
				trafienia.remove(w);
				w=null;
				continue;
			}
			g.drawImage(wybuchy[w.klatka], w.poz_X, w.poz_Y, null);
			w.klatka++;
		}
			
			for(zniszczenie z : rip) {
				if(z.klatka==2) {
					rip.remove(z);
					z=null;
					continue;
				}
				g.drawImage(eksplozje[z.klatka], z.poz_X, z.poz_Y, null);
				z.klatka++;
		}
		
	}
	public void wczytaj_tekstury() {
		
		File sciezka = new File("images/icon2.png");
		try {
		ikony  = ImageIO.read(sciezka);
		}
		catch(IOException e)
		{
			//nie wiem
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
			
		}
		
		sciany[0]=ikony.getSubimage((16*16), 0, 16, 16); //cegla
		sciany[1]=ikony.getSubimage((16*17)+8, 0, 8, 16); //cegla_pion
		sciany[2]=ikony.getSubimage((16*18), 8, 16, 8); //cegla_poziom
		
		sciany[3]=ikony.getSubimage((16*16), 16, 16, 16); //stal
		sciany[4]=ikony.getSubimage((16*17)+8, 16, 8, 16); //stal_pion
		sciany[5]=ikony.getSubimage((16*18), 24, 16, 8); //stal_poziom
		

		sciany[6]= ikony.getSubimage((16*19), 32, 16, 16); //orzel
		sciany[7]= ikony.getSubimage((16*20), 32, 16, 16); //orzel_zniszczony
		
		sciany[8]= ikony.getSubimage((16*16), 32, 16, 16); //woda
		sciany[9]= ikony.getSubimage((16*17), 32, 16, 16); //las
		
		
		wybuchy[0] = ikony.getSubimage(16*16, 16*8, 16, 16); //wybuch1
		wybuchy[1] = ikony.getSubimage(16*17, 16*8, 16, 16); //wybuch2
		wybuchy[2] = ikony.getSubimage(16*17, 16*8, 16, 16); //wybuch3
		
		eksplozje[0] = ikony.getSubimage(16*19, 8*16, 32, 32);
		eksplozje[1] = ikony.getSubimage(16*21, 8*16, 32, 32);
		
		
		//wybuchy[2].getTransparency();
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
	            sciana_sektora = new BufferedImage[count];

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
	                sciana_sektora[i] = sciany[Integer.valueOf(dane[0])];
	                sektor[i] = new Rectangle(Integer.valueOf(dane[1]),Integer.valueOf(dane[2]),Integer.valueOf(dane[3]), Integer.valueOf(dane[4]));
	                		i++;

	            }

	        } catch (FileNotFoundException e) {         
	            e.printStackTrace();
	        }
		
	       
	}
	
	public boolean przejazd_lewo() {
		
		for(int i=0 ; i<sektor.length ;i++) {
			for(int j=0 ; j<16 ; j++) {
				if(sektor[i].contains((jeden.pozX)-1, (jeden.pozY+j))&& sektor_id[i]!=9) return false;
				
			}
		}
		return true;
	}
	
	public boolean przejazd_gora() {
		
		for(int i=0 ; i<sektor.length ;i++) {
			for(int j=0 ; j<16 ; j++) {
				if(sektor[i].contains((jeden.pozX+j), (jeden.pozY)-1) && sektor_id[i]!=9) return false;
				
			}
		}
		return true;
	}
		
		public boolean przejazd_prawo() {
			
			for(int i=0 ; i<sektor.length ;i++) {
				for(int j=0 ; j<16 ; j++) {
					if(sektor[i].contains((jeden.pozX)+1+16, (jeden.pozY+j))&& sektor_id[i]!=9) return false;
					
				}
			}
			return true;
		}
		
		public boolean przejazd_dol() {
			
			for(int i=0 ; i<sektor.length ;i++) {
				for(int j=0 ; j<16 ; j++) {
					if(sektor[i].contains((jeden.pozX+j), (jeden.pozY)+1+16)&& sektor_id[i]!=9) return false;
					
				}
		}

		
		return true;
	}
		
		
		public void trafienie(int x , int y) {
			if(x >= 208 || x<=0) {
				jeden.at=null;
				wybuch temp= new wybuch(x ,y);
				trafienia.add(temp);
				return;
			}
			else if (y>=208 || y<=0) {
				jeden.at=null;
				wybuch temp= new wybuch(x ,y);
				trafienia.add(temp);
				return;
			}
			
				for(int i=0 ; i<sektor.length ;i++) {
					if(sektor[i].contains(x ,y) && sektor_id[i]<8) {
						wybuch temp= new wybuch(x ,y);
						trafienia.add(temp);
						if(sektor_id[i]<3)wyburz(i);
						else if(sektor_id[i]==6)przegrana(i);
						else jeden.at=null;
					}
									
								
				}
					
				
			}

		
		
		public void wyburz(int x) {
			int z=0 , y=0 ;
			switch(jeden.at.kierunek) {
			case 0: //gora
				//sektor[x].y +=4;
				sektor[x].height -=4;
				break;
			case 1://lewo
				//sektor[x].x+=4;
				sektor[x].width -=4;
				break;
			case 2://dol
				sektor[x].y +=4;
				sektor[x].height -=4;
				y=4;
				break;
			case 3://prawo
				sektor[x].x+=4;
				sektor[x].width -=4;
				z=4;
				break;
			}
			jeden.at =null;
			wytnij(x ,z ,y);
			
			
		}
		
		public void przegrana( int k) {
			jeden.at=null;
			sektor_id[k]=7;
			sciana_sektora[k] = sciany[7];
			zniszczenie temp = new zniszczenie(sektor[k].x , sektor[k].y);
			rip.add(temp);
		}
		
		public void wytnij(int i ,int x ,int y ) {
			BufferedImage temp =sciana_sektora[i].getSubimage(x , y ,sektor[i].width,sektor[i].height);
			sciana_sektora[i]= temp;
		}
		
}
