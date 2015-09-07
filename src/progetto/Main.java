package progetto;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
	
	/*
	 * il main del progetto
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				JFrame gioco = new FinestraGioco();
				gioco.setSize(1000, 720);
				gioco.setVisible(true);
				gioco.setTitle("Scacchi");
				gioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});	
	}
} 
