package progetto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JPanel {
	
	/*
	 * questa classe imposta tutte le immagini e le stampa nella finestra di gioco
	 */
	
	private Image background;							//immagine dello sfondo
	private Image scacchiera;							//immagine della scacchiera
	private Image giocatoreBiancoC;						//immagine della scritta "Giocatore Bianco"
	private Image giocatoreNeroC;						//immagine della scritta "Giocatore Nero"
	private Image giocatoreBiancoI;						//immagine della zona giocatori bianca
	private Image giocatoreNeroI;						//immagine della zona giocatori nera
	private Image scacco;								//immagine scritta "Scacco Al Re"
	private Image BiancoVince;							//immagine "BiancoVince"
	private Image NeroVince;							//immagine "NeroVince"
	private Image scaccoMatto;							//immagine scritta "Scacco Matto"
	private int nPezziBCatturati;						//numero di pezzi bianchi catturati
	private int nPezziNCatturati;						// numero di pezzi neri catturati
	private int rettangoloX;							//coordinata x del rettangolo che segna i possibili movimenti dei pezzi
	private int rettangoloY;							// coordinata y del rettangolo che segna i possibili movimenti dei pezzi
	private GiocoScacchi giocoScacchi;					// contiene tutte le regole del gioco
	private ArrayList<DisegnaPezzo> pezzi_disegnati;	// ArrayList di pezzi con le informazioni grafiche
	private SpostamentoPezzi spostamento;				// spostamento dei pezzi grazie al mouse
	
	/*
	 * variabili statiche per inserire i pezzi in maniera corretta nella scacchiera anche quando
	 * vengono sposati e variabili statiche che contengono i possibili colori, i possibili tipi e
	 * le possibili righe e colonne dei pezzi
	 */
	private static final int inizio_scacchiera = 20;
	private static final int gr_casella = 75;
	private static final int gr_pezzo = 75;
	
	private static final int inizio_pezzo = inizio_scacchiera + (int)(gr_casella/2.0 - gr_pezzo/2.0);
	private static final int inizio_pezzo_spostato = inizio_scacchiera - (int)(gr_pezzo/2.0);
	
	private static final int bianco = 0;
	private static final int nero = 1;
	
	private static final int torre = 1;
	private static final int cavallo = 2;
	private static final int alfiere = 3;
	private static final int regina = 4;
	private static final int re = 5;
	private static final int pedina = 6;
	
	private static final int[] righe = {0, 1, 2, 3, 4, 5, 6, 7};
	private static final int[] colonne = {0, 1, 2, 3, 4, 5, 6, 7};
	
	public Gui (){
		
		this.nPezziBCatturati = 0;
		this.nPezziNCatturati = 0;
		this.pezzi_disegnati = new ArrayList<DisegnaPezzo>();
		
		this.background = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/background.png").getImage();
		this.scacchiera = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/scacchieraDef.png").getImage();
		this.giocatoreBiancoI = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/giocatore Bianco.png").getImage();
		this.giocatoreNeroI = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/giocatore Nero.png").getImage();
		this.giocatoreBiancoC = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/ContornoScacchiera.png").getImage();
		this.giocatoreNeroC = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/ContornoScacchiera.png").getImage();
		this.scacco = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/scacco.png").getImage();
		this.scaccoMatto = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/scaccoMatto.png").getImage();
		this.BiancoVince = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/BiancoVince.png").getImage();
		this.NeroVince = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/NeroVince.png").getImage();
		assegnaPezziDisegnati();		
	}
	
	//aggiunge i vari "pezzi disegnati" all'ArrayList e imposta le variabili alla situazione di inizio gioco
	public void assegnaPezziDisegnati(){
		this.nPezziBCatturati = 0;
		this.nPezziNCatturati = 0;
		this.giocoScacchi = new GiocoScacchi();
		this.pezzi_disegnati = new ArrayList<DisegnaPezzo>();
		for(Pezzo pezzo : this.giocoScacchi.getPezzi()){
			aggiungiPezzoDisegnato(pezzo);
		}
		spostamento = new SpostamentoPezzi(this, this.pezzi_disegnati);
		addMouseListener(spostamento);
		addMouseMotionListener(spostamento);		
	}
	
	//aggiunge quel pezzo specifico all'ArrayList
	public void aggiungiPezzoDisegnato(Pezzo pezzo){
		Image immagine = getImmaginePezzo(pezzo.getColore(), pezzo.getTipo());
		DisegnaPezzo pezzo_disegnato = new DisegnaPezzo(immagine, pezzo);
		this.pezzi_disegnati.add(pezzo_disegnato);
	}
	
	//in base al colore e al tipo prende il percorso giusto dell'immagine
	public Image getImmaginePezzo(int colore, int tipo){
		String nomefile = "";

		switch(tipo){
			case torre:
				nomefile += "Torre";
				break;
			case cavallo:
				nomefile += "Cavallo";
				break;
			case alfiere:
				nomefile += "Alfiere";
				break;
			case regina:
				nomefile += "Regina";
				break;
			case re:
				nomefile += "Re";
				break;
			case pedina:
				nomefile += "Pedina";
				break;
		}
		
		if(colore == bianco)
			nomefile += "B";
		else
			nomefile += "N";
		
		nomefile += ".png";
		
		return new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/" + nomefile).getImage();
		
		
	}
	
	public GiocoScacchi getGiocoScacchi(){
		return giocoScacchi;
	}
	
	public int getTurno(){
		return giocoScacchi.getTurno();
	}
	
	//converte la colonna nella corrispondente coordinata x
	public static int convertiColonnaInX(int colonna){
		return inizio_pezzo + gr_casella * colonna; 	
	}
	
	//converte la riga nella corrispondente coordinata y
	public static int convertiRigaInY(int riga){
		return inizio_pezzo + gr_casella * (righe[7] - riga);
	}
	
	//converte la coordinata x nella corrispondente colonna
	public static int convertiXInColonna(int x){
		return (x - inizio_pezzo_spostato) / gr_casella;
	}
	
	//converte la coordinata y nella corrispondente riga
	public static int convertiYInRiga(int y){
		return righe[7] - (y - inizio_pezzo_spostato) / gr_casella;
	}
	
	//imposta una nuova posizione al "pezzo disegnato"
	public void setNuovaPosizione(DisegnaPezzo pezzo_spostato, int x, int y){
		int nuovaRiga = Gui.convertiYInRiga(y);
		int nuovaColonna = Gui.convertiXInColonna(x);
		
		if(nuovaRiga < righe[0] || nuovaRiga > righe[7] || nuovaColonna < colonne[0] || nuovaColonna > colonne[7] ){
			pezzo_spostato.nuovaPosizione();
		}else{
			giocoScacchi.muoviPezzo(pezzo_spostato.getPezzo().getRiga(), pezzo_spostato.getPezzo().getColonna(), nuovaRiga, nuovaColonna);
			
			// nel caso in cui una pedina può essere promossa allora crea e visualizza la finestra per la promozione
			if((pezzo_spostato.getPezzo().getTipo() == pedina && pezzo_spostato.getPezzo().getColore() == bianco && pezzo_spostato.getPezzo().getRiga() == righe[7]) || (pezzo_spostato.getPezzo().getTipo() == pedina && pezzo_spostato.getPezzo().getColore() == nero && pezzo_spostato.getPezzo().getRiga() == righe[0])){
				JFrame promozione = new PromozionePedina(pezzo_spostato, pezzi_disegnati);
				promozione.setVisible(true);
				promozione.pack();
			}
			pezzo_spostato.nuovaPosizione();
		}
	}
	
	public Dimension getPrefferedSize(){
		return new Dimension(636, 636);
	}
	
	protected void paintComponent(Graphics g){
		g.drawImage(this.background, 0, 0, null);
		g.drawImage(scacchiera, 0, 0, null);
		if(giocoScacchi.getTurno() == bianco){
			g.drawImage(giocatoreBiancoI, 725, 425, null);
		}
		g.drawImage(giocatoreBiancoC,650,450, null);
		if(giocoScacchi.getTurno() == nero){
			g.drawImage(giocatoreNeroI, 725, 25, null);
		}
		g.drawImage(giocatoreNeroC,650,50, null);
		for(DisegnaPezzo pezzo_disegnato : this.pezzi_disegnati){
			
			//se avviene lo scacco al re e non avviene lo scacco matto allora stampa la scritta "Scacco Al Re" sotto la scacchiera
			if(giocoScacchi.isScaccoAlRe() && giocoScacchi.isScaccoMatto() == false){
				g.drawImage(scacco,245,650,null);
			}
			
			//se avviene lo scacco matto allora stampa lo scacco matto sotto la scacchiera
			if(giocoScacchi.isScaccoMatto()){
				if(spostamento.getChiVince() == bianco)
					g.drawImage(NeroVince,200,200,null);
				else
					g.drawImage(BiancoVince,200,200,null);
				
				g.drawImage(scaccoMatto,245,650,null);
			}
			
			//se il pezzo non è stato catturato continua a stamparlo sulla scacchiera
			if(pezzo_disegnato.isCaptured() == false){
				g.drawImage(pezzo_disegnato.getImmagine(), pezzo_disegnato.getX(), pezzo_disegnato.getY(), null);
				
			//altrimenti si prende la posizione di cattura del pezzo e in base alla posizione lo stampa nella zona giocatore di chi l'ha catturato	
			}else{
				if(pezzo_disegnato.getColore() == bianco){
					nPezziBCatturati = (pezzo_disegnato.getPosizioneDiCattura()-1)*37;
					if(nPezziBCatturati/37<8){
						g.drawImage(pezzo_disegnato.getImmagine(), 655+nPezziBCatturati, 50, 37, 37, null);
						}
						else{
							g.drawImage(pezzo_disegnato.getImmagine(), 655+(nPezziBCatturati-296), 87, 37, 37, null);
						}
				}
				else{
					nPezziNCatturati = (pezzo_disegnato.getPosizioneDiCattura()-1)*37;
					if(nPezziNCatturati/37<8){
					g.drawImage(pezzo_disegnato.getImmagine(), 655+nPezziNCatturati, 450, 37, 37, null);
					}
					else{	
						g.drawImage(pezzo_disegnato.getImmagine(), 655+(nPezziNCatturati-296), 487, 37, 37, null);
					}
				}	
			}
		}
		
		//stampa dei rettangoli verdi intorno alle possibili caselle nella quale il pezzo può spostarsi (solo se il pezzo viene selezionato con il mouse)
		MovimentoValido movimentoValido = this.giocoScacchi.getMv();
		if(spostamento.getPezzoSpostato()!=null){
			for(int riga = righe[0]; riga <= righe[7]; riga++){
				for(int colonna = colonne[0]; colonna <= colonne[7]; colonna++){
					int vecchiaRiga = spostamento.getPezzoSpostato().getPezzo().getRiga();				
					int vecchiaColonna = spostamento.getPezzoSpostato().getPezzo().getColonna();
					if(movimentoValido.movimentoValido(vecchiaRiga, vecchiaColonna, riga, colonna)){
						rettangoloX = convertiColonnaInX(colonna);
						rettangoloY = convertiRigaInY(riga);
						g.setColor(Color.BLACK);
						g.drawRect( rettangoloX+1, rettangoloY+1, gr_casella-1, gr_casella-1);
						g.drawRect( rettangoloX+2, rettangoloY+2, gr_casella-1, gr_casella-1);
						g.setColor(Color.GREEN);
						g.drawRect( rettangoloX, rettangoloY, gr_casella-1, gr_casella-1);
						g.drawRect( rettangoloX-1, rettangoloY-1, gr_casella-1, gr_casella-1);
					}
				}
			}
		}
	}
}

