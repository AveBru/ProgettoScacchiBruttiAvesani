package progetto;

import javax.swing.JFrame;

public class FinestraGioco extends JFrame {
	
	/*
	 * La finestra con la scacchiera, le zone dei rispettivi giocatori
	 * e i pezzi disegnati
	 */
	
	public FinestraGioco(){

		add(new Gui());
	}
}
