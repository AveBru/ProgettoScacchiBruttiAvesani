package progetto;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SpostamentoPezzi implements MouseListener, MouseMotionListener {
	
	/*
	 * questa classe implementa lo spostamento dei vari pezzi nella scacchiera
	 * attraverso il premi e rilascia del mouse 
	 */
	
	/*
	 * variabili statiche che contengono rispettivamente i possibili turni
	 * (fine indica che è avvenuto lo scacco matto) e i possibili colori dei pezzi
	 */
	private static final int turno_bianco = 0;
	private static final int turno_nero = 1;
	private static final int fine = 2;
	
	private static final int bianco = 0;
	private static final int nero = 1;
	
	private Gui chessGui;							//le informazioni grafiche del
	private ArrayList<DisegnaPezzo> pezzi_disegnati;//ArrayList con i pezzi disegnati
	private DisegnaPezzo pezzoSpostato;				//il pezzo che viene spostato con il mouse
	private int offsetX;							//offset della coordinata x
	private int offsetY;							// offset della coordinata y
	private int chiVince;							// per assegnare vittoria bianca o nera
	
	public SpostamentoPezzi(Gui chessGui, ArrayList<DisegnaPezzo> pezzi_disegnati){
		this.chessGui = chessGui;
		this.pezzi_disegnati = pezzi_disegnati;
		this.chiVince = 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0){
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0){
		
	}

	@Override
	public void mouseExited(MouseEvent arg0){
		
	}

	/*quando il mouse viene premuto su un pezzo se non è stato ancora catturato e se il turno è bianco
	* o nero mi permette di spostare quel pezzo
	*/ 
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		DisegnaPezzo temp = null;
		for(int i = pezzi_disegnati.size() - 1; i >= 0; i--){
			temp = pezzi_disegnati.get(i);
			if(temp.isCaptured() == false){
				if(temp.getX() <= x && temp.getY() <= y && temp.getX() + temp.getLarghezza() >= x && temp.getY() + temp.getAltezza() >= y){
					if((chessGui.getTurno() == turno_bianco && temp.getColore() == bianco) || (chessGui.getTurno() == turno_nero && temp.getColore() == nero)){
						offsetX = x - temp.getX();
						offsetY = y - temp.getY();
						pezzoSpostato = temp;
						break;
					}
				}
			}
		}
		
		if(pezzoSpostato != null){
			pezzi_disegnati.remove(pezzoSpostato);
			pezzi_disegnati.add(pezzoSpostato);
		}
		chessGui.repaint();
	}

	/*
	 * quando il mouse viene rilasciato vengono aggiornate tutte le nuove informazioni
	 * sul pezzo (ad esempio riga e colonna), inoltre viene controllato se è avvenuto scacco al re
	 * e nel caso ci sia allora viene anche verificato scacco matto, se è presente allora viene impostato il turno a fine
	 * e viene creato un bottone tra le due zone giocatori per iniziare una nuova partita
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		GiocoScacchi gioco = chessGui.getGiocoScacchi();
		if(pezzoSpostato != null){
			int x = e.getX() - offsetX;
			int y = e.getY() - offsetY;
			chessGui.setNuovaPosizione(pezzoSpostato, x, y);	
			if(gioco.verificaScaccoAlRe(pezzoSpostato.getPezzo()))
				if(gioco.verificaScaccoMatto()){
					if(gioco.getTurno()==bianco)
						chiVince = bianco;
					else 
						chiVince = nero;
					gioco.setTurno(fine);
					JButton nuovaPartita = new JButton();
					nuovaPartita.setSize(138,100);
					nuovaPartita.setLocation(750, 225);
					ImageIcon backgroundB = new ImageIcon("../ProgettoScacchi/immagini/backgroundB.png");
					nuovaPartita.setIcon(backgroundB);
					nuovaPartita.setVisible(true);
					chessGui.add(nuovaPartita);
					nuovaPartita.addActionListener(new java.awt.event.ActionListener(){
						public void actionPerformed(ActionEvent event){
							chessGui.assegnaPezziDisegnati();
							nuovaPartita.setVisible(false);
						}
					});
				}
			chessGui.repaint();
			pezzoSpostato = null;
		}	
	}
	
	public int getChiVince() {
		return chiVince;
	}

	public DisegnaPezzo getPezzoSpostato() {
		return pezzoSpostato;
	}

	//visualizza il movimento del pezzo quando viene spostato con il mouse
	@Override
	public void mouseDragged(MouseEvent e) {
		if(pezzoSpostato != null){
			int x = e.getX() - offsetX;
			int y = e.getY() - offsetY;
			this.pezzoSpostato.setX(x);
			this.pezzoSpostato.setY(y);
			this.chessGui.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		chessGui.repaint();	
	}
}