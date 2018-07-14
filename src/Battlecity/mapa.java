package Battlecity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Battlecity.gracz;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class mapa  extends JPanel {
	
	public gracz jeden;
	public Rectangle[] sektor;
	public int[] sektor_id;
	public int licznik;
	public int[] respy;
	public Rectangle poz_gracza;
	public boolean victory;
	public int zniszczenia;
	public Font font;
	
	BufferedImage[] sciany;
	BufferedImage[] wybuchy;
	BufferedImage[] eksplozje;
	BufferedImage ikony;
	public BufferedImage[] sciana_sektora;
	BufferedImage ikona_wroga;
	//podsumowanie finito;
	//BufferedImage[] pociski;
	//Rectangle pocisk;
	//ArrayList<Rectangle> wyburzony = new ArrayList<Rectangle>();
	ArrayList<wybuch> trafienia;
	ArrayList<zniszczenie> rip;
	ArrayList<Rectangle> sektor_wroga;
	ArrayList<wrog> przeciwnicy;
	ArrayList<strzal> ogien; 
	
	
	public mapa() {
		jeden = new gracz();
		poz_gracza = new Rectangle(jeden.pozX , jeden.pozY, 16 ,16);
		setPreferredSize(new Dimension( 230, 230 )); //240 , 208 |||-10 bo resize=false
		setBackground(Color.LIGHT_GRAY);
		//pociski= new BufferedImage[4];
		sciany= new BufferedImage[10];
		wybuchy = new BufferedImage[3];
		eksplozje = new BufferedImage[2];
		trafienia = new ArrayList<wybuch>();
		rip = new ArrayList<zniszczenie>();
		sektor_wroga = new ArrayList<Rectangle>();
		przeciwnicy = new ArrayList<wrog>();
		ogien = new ArrayList<strzal>();
		licznik=20;
		zniszczenia=0;
		respy = new int[] {0 ,96 ,192};
		//przegrana = false;
		wczytaj_tekstury();
		wczytaj_level();
		
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		//this.setBackground(Color.BLACK);
		g.setColor(Color.BLACK);
		g.fillRect(0 , 0  ,208 ,208);
		g.setColor(Color.LIGHT_GRAY);
		//g.fillRect(208 , 0  ,32 ,208);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("PUNKTY:", 5, 227);
		g.drawString(String.valueOf(zniszczenia), 100, 227);
		
		int k=0;
		for(int i=0 ;i<licznik ;i++) {
			g.drawImage(ikona_wroga, 216+(i%2)*8,16+(k*8), null);
			k+= i%2;
		}
		if(jeden!=null) {
			BufferedImage temp = ((gracz) jeden).rysuj();
			g.drawImage(temp,((gracz) jeden).pozX ,((gracz) jeden).pozY , null);	
		}

		
		for(wrog w: przeciwnicy) {
			int cos =przeciwnicy.indexOf(w);
			sterowanie_ai(cos);
			BufferedImage temp2 = ((wrog)w).rysuj();
			g.drawImage(temp2, w.poz_X,w.poz_Y, null);
		}
		
		for(int i=0 ; i<sektor.length ; i++) {
			
			g.drawImage(sciana_sektora[i], sektor[i].x, sektor[i].y,
					sektor[i].width ,sektor[i].height,  null);
		}
		
		
		
		for(strzal at :ogien) {
			
			g.setColor(Color.GRAY);
			g.fillRect(at.poz_X, at.poz_Y, 2,2);
			at.lot();
			trafienie(at);
			
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
		
		 
			    
			    
			    try {
			    	InputStream is = new BufferedInputStream(new FileInputStream("font/prstartk.ttf"));
					font = Font.createFont(Font.TRUETYPE_FONT, is);
					font =font.deriveFont(12f);
				} catch (FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			  
		
		ikona_wroga = ikony.getSubimage(20*16, 16*12, 8, 8);
		
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
	
	public boolean przejazd_lewo(int x , int y) {
		
		for(int i=0 ; i<sektor.length ;i++) {
			for(int j=0 ; j<16 ; j++) {
				if(sektor[i].contains(x-1, y+j)&& sektor_id[i]!=9) return false;
				else if(poz_gracza.contains(x-1 ,y+j))return false;
				
				for(Rectangle r: sektor_wroga) {
					if(r.contains(x-1,y+j)) return false;
				}
			}
		}

		
		return true;
	}
	
	public boolean przejazd_gora(int x , int y) {
		
		for(int i=0 ; i<sektor.length ;i++) {
			for(int j=0 ; j<16 ; j++) {
				if(sektor[i].contains(x+j, y-1) && sektor_id[i]!=9) return false;
				else if(poz_gracza.contains(x+j ,y-1))return false;
				
				for(Rectangle r: sektor_wroga) {
					if(r.contains(x+j,y-1)) return false;
				}
				
			}
		}

		
		return true;
	}
		
		public boolean przejazd_prawo(int x, int y) {
			
			for(int i=0 ; i<sektor.length ;i++) {
				for(int j=0 ; j<16 ; j++) {
					if(sektor[i].contains(x+1+16, y+j)&& sektor_id[i]!=9) return false;
					else if(poz_gracza.contains(x+1+16 ,y+j))return false;
					
					for(Rectangle r: sektor_wroga) {
						if(r.contains(x+1+16,y+j)) return false;
					}
				}
			}
	
			
			return true;
		}
		
		public boolean przejazd_dol(int x , int y) {
			
			for(int i=0 ; i<sektor.length ;i++) {
				for(int j=0 ; j<16 ; j++) {
					if(sektor[i].contains(x+j, y+1+16)&& sektor_id[i]!=9) return false;
					else if(poz_gracza.contains(x+j ,y+1+16))return false;
					
					for(Rectangle r: sektor_wroga) {
						if(r.contains(x+j,y+1+16)) return false;
					}
				}
			}

		

		
		return true;
	}
		
		
		public void trafienie(strzal at) {
			int x= at.poz_X;
			int y= at.poz_Y;
			if(x >= 208 || x<=0) {
				czysc_strzal(at);
				wybuch temp= new wybuch(x ,y);
				trafienia.add(temp);
				return;
			}
			else if (y>=208 || y<=0) {
				czysc_strzal(at);
				wybuch temp= new wybuch(x ,y);
				trafienia.add(temp);
				return;
			}
			
			if(at.gracza) {
				int index=0;
				for(Rectangle r: sektor_wroga){
					
					if(r.contains(x, y)) {
						wybuch temp = new wybuch(x,y);
						trafienia.add(temp);
						zniszczenie temp2 = new zniszczenie(r.x , r.y);
						rip.add(temp2);
						czysc_strzal(at);
						if(przeciwnicy.get(index).at!=null)czysc_strzal(przeciwnicy.get(index).at);
						sektor_wroga.remove(index);
						przeciwnicy.remove(index);
						zniszczenia++;
						zwyciestwo();
					}
				
					index++;
				}
				
			}
			else if(jeden!=null) {
				if(poz_gracza.contains(x ,y)) {
					wybuch temp = new wybuch(x,y);
					trafienia.add(temp);
					zniszczenie temp2 = new zniszczenie(poz_gracza.x , poz_gracza.y);
					rip.add(temp2);
					jeden=null;
					czysc_strzal(at);
					przegrana();
				}
			}

			
				for(int i=0 ; i<sektor.length ;i++) {
					if(sektor[i].contains(x ,y) && sektor_id[i]<8) {
						wybuch temp= new wybuch(x ,y);
						trafienia.add(temp);
						if(sektor_id[i]<3)wyburz(i, at);
						else if(sektor_id[i]==6) {
							czysc_strzal(at);
							sektor_id[i]=7;
							sciana_sektora[i] = sciany[7];
							zniszczenie temp2 = new zniszczenie(sektor[i].x , sektor[i].y);
							rip.add(temp2);
							przegrana();
						}
						else czysc_strzal(at);
							
						
					}
									
								
				}
					
				
			}

		
		
		public void wyburz(int x , strzal at) {
			int z=0 , y=0 ;
			switch(at.kierunek) {
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
			czysc_strzal(at);
			wytnij(x ,z ,y);
			
			
		}
		
		public void spawn() {
			if(przeciwnicy.size() <4 &&licznik>0)
			{
				Random rand= new Random();
				int los = rand.nextInt(3);
				wrog temp = new wrog(respy[los]);
				Rectangle temp2 = new Rectangle(respy[los] ,0 , 16 ,16);
				sektor_wroga.add(temp2);
				przeciwnicy.add(temp);
				licznik--;
			}
		}
		
		public void sterowanie_ai(int inde) {
			wrog l= przeciwnicy.get(inde);
			Rectangle r= sektor_wroga.get(inde);
			int x=l.poz_X;
			int y=l.poz_Y;
			l.przeladowanie++;
			Random rand = new Random();
			if(l.at==null && l.przeladowanie==100) {
				l.at= new strzal(x, y, l.strona , false);
				ogien.add(l.at);
				l.przeladowanie=0;
			}
			
			
			switch(l.strona){
			case 3:
				
				l.klatka=l.klatka^1;
				if(l.poz_X <192 &&przejazd_prawo(x,y)) {
					l.poz_X+=1;
					r.x+=1;
					return;
				}
				break;
			case 0:
				l.klatka=l.klatka^1;
				if(l.poz_Y >0 && przejazd_gora(x,y)) {
					l.poz_Y-=1;
					r.y-=1;
					return;
					
				}
				break;
			case 1:
				l.klatka=l.klatka^1;
				if(l.poz_X >0 && przejazd_lewo(x,y)) {
					l.poz_X-=1;
					r.x-=1;
					return;
					
				}
				break;
			case 2:
				l.klatka=l.klatka^1;
				if(l.poz_Y <192 && przejazd_dol(x,y)) {
					l.poz_Y+=1;
					r.y+=1;
					return;
					
				}
				break;

				}
			
			int los = rand.nextInt(4);
			l.strona =los;
		}
		public void czysc_strzal(strzal at) {
			
			if(jeden != null && at==jeden.at) {
				jeden.at=null;
				ogien.remove(at);
				return;
			}
			for(wrog w:przeciwnicy) {
				if(at==w.at) {
					w.at=null;
					ogien.remove(at);
					return;
				}
			}
		}
		
		public void przegrana() {
			
			
			repaint();
			
			
			int response = JOptionPane.showConfirmDialog(null, "Udalo Ci sie zniszczyc "+zniszczenia+" wrogow. Zaczac od poczatku ?", "Koniec gry",
	                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		            System.exit(0);
		    } 
		        else if (response == JOptionPane.YES_OPTION) {
		        	zniszczenia=0;
		        	victory=true;
		        	

		    } 
		        else if (response == JOptionPane.CLOSED_OPTION) {
		            System.exit(0);
		    }
			

		}
		
		public void zwyciestwo() {
			if(licznik==0 && przeciwnicy.size()==0) {
				victory=true;
			}
		}
		
		public void wytnij(int i ,int x ,int y ) {
			BufferedImage temp =sciana_sektora[i].getSubimage(x , y ,sektor[i].width,sektor[i].height);
			sciana_sektora[i]= temp;
		}
		
}
