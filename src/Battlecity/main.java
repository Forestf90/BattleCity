package Battlecity;

import java.awt.EventQueue;

import Battlecity.plansza;

public class main {

	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		@Override
		public void run() {
			new plansza();
		}
	});
	} 
}
