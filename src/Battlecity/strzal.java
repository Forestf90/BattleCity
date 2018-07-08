package Battlecity;


public class strzal {

	//BufferedImage[] kierunek;
	public int kierunek;
	public int poz_X;
	public int poz_Y;
	public int zmiana_X;
	public int zmiana_Y;
	
	public strzal(int x ,int  y ,int k) {
		kierunek =k;
		switch (kierunek) {
		case 0: //gora
			poz_X=x+7;
			poz_Y=y;
			zmiana_X=0;
			zmiana_Y=-3;
			break;
		case 1: //lewo
			poz_X=x;
			poz_Y=y+7;
			zmiana_X=-3;
			zmiana_Y=0;
			break;
		case 2: //dol
			poz_X=x+7;
			poz_Y=y+16;
			zmiana_X=0;
			zmiana_Y=3;
			break;
		case 3: //prawo
			poz_X=x+16;
			poz_Y=y+7;
			zmiana_X=3;
			zmiana_Y=0;
			break;
		}
	}
	
	public void lot() {
		poz_X+= zmiana_X;
		poz_Y += zmiana_Y;
	}
	



}
