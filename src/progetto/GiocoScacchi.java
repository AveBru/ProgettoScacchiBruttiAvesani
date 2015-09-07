package progetto;

import java.util.ArrayList;

public class GiocoScacchi {
	
	/*
	 * questa classe imposta le regole del gioco
	 * e verifica lo scacco al re e lo scacco matto
	 */
	
	private int turno;					//il turno attuale
	private int nPezziBCatturati;		//il numero di pezzi bianchi catturati
	private int nPezziNCatturati;		//il numero di pezzi neri catturati
	private boolean scaccoAlRe;         //impostata a true quando esiste lo scacco al re
	private boolean scaccoMatto;		//impostata a true quando esiste lo scacco matto
	private ArrayList<Pezzo> pezzi;		//ArrayList che contiene tutti i pezzi 
	private MovimentoValido mv;			//variabile che serve per controllare i movimenti validi dei pezzi
	private Pezzo pezzo_attaccante;		//contiene il pezzo che attualmente sta facendo scacco al re
	
	/*
	 * variabili statiche che contengono rispettivamente
	 * i possibili turni, i possibili colori,
	 * le possibili righe e colonne e i possibili tipi dei pezzi
	 */
	private static final int turno_bianco = 0;
	private static final int turno_nero = 1;
	
	private static final int bianco = 0;
	private static final int nero = 1;
	
	private static final int[] righe = {0, 1, 2, 3, 4, 5, 6, 7};
	private static final int[] colonne = {0, 1, 2, 3, 4, 5, 6, 7};
	
	private static final int torre = 1;
	private static final int cavallo = 2;
	private static final int alfiere = 3;
	private static final int regina = 4;
	private static final int re = 5;
	private static final int pedina = 6;
	
	public GiocoScacchi(){

		this.turno = turno_bianco;
		this.nPezziBCatturati = 0;
		this.nPezziNCatturati = 0;
		this.scaccoAlRe = false;
		this.scaccoMatto = false;
		this.pezzi = new ArrayList<Pezzo>();
		this.mv = new MovimentoValido(this);
		this.pezzo_attaccante = null;
		assegnaPezzi();
		
	}
	
	// aggiunge i vari pezzi all'ArrayList e li imposta alla riga e colonna di inizio gioco
	public void assegnaPezzi(){
		aggiungiPezzo(nero, torre, righe[7], colonne[0]);
		aggiungiPezzo(nero, cavallo, righe[7], colonne[1]);
		aggiungiPezzo(nero, alfiere, righe[7], colonne[2]);
		aggiungiPezzo(nero, regina, righe[7], colonne[3]);
		aggiungiPezzo(nero, re, righe[7], colonne[4]);
		aggiungiPezzo(nero, alfiere, righe[7], colonne[5]);
		aggiungiPezzo(nero, cavallo, righe[7], colonne[6]);
		aggiungiPezzo(nero, torre, righe[7], colonne[7]);
		int k = colonne[0];
		for(int i = 0; i < 8; i++){
			aggiungiPezzo(nero, pedina, righe[6], k);
			k++;
		}
		aggiungiPezzo(bianco, torre, righe[0], colonne[0]);
		aggiungiPezzo(bianco, cavallo, righe[0], colonne[1]);
		aggiungiPezzo(bianco, alfiere, righe[0], colonne[2]);
		aggiungiPezzo(bianco, regina, righe[0], colonne[3]);
		aggiungiPezzo(bianco, re, righe[0], colonne[4]);
		aggiungiPezzo(bianco, alfiere, righe[0], colonne[5]);
		aggiungiPezzo(bianco, cavallo, righe[0], colonne[6]);
		aggiungiPezzo(bianco, torre, righe[0], colonne[7]);
		k = colonne[0];
		for (int i=0;i<8;i++){
			aggiungiPezzo(bianco, pedina, righe[1], k);
			k++;
		}
	}
	
	//aggiunge quel determinato pezzo all'ArrayList
	public void aggiungiPezzo(int colore, int tipo, int riga, int colonna){
		Pezzo pezzo = new Pezzo(colore, tipo, riga, colonna);
		pezzi.add(pezzo);
	}
	
	//restituisce true se in quella determinata riga e colonna esiste un pezzo di quel colore (se non è ancora stato catturato)
	public boolean controllaPezzoColore(int colore, int riga, int colonna){
		for(Pezzo pezzo : pezzi){
			if(pezzo.getRiga() == riga && pezzo.getColonna() == colonna && pezzo.isCaptured() == false && pezzo.getColore() == colore){
				return true;
			}
		}
		return false;
	}
	
	//restituisce true se in quella determinata riga e colonna esiste un pezzo di un qualsiasi colore (se non è ancora stato catturato)
	public boolean controllaPezzo(int riga, int colonna){
		for(Pezzo pezzo : pezzi){
			if(pezzo.getRiga() == riga && pezzo.getColonna() == colonna && pezzo.isCaptured() == false){
				return true;
			}
		}
		return false;
	}
	
	//restituisce il pezzo corrispondente ad una determinata riga e colonna se non è stato ancora catturato e se esiste 
	public Pezzo getPezzoNonCatturato(int riga, int colonna){
		for(Pezzo pezzo : pezzi){
			if(pezzo.getRiga() == riga && pezzo.getColonna() == colonna && pezzo.isCaptured() == false){
				return pezzo;
			}
		}
		return null;
	}
	
	//restituisce true se il pezzo dato in input potrebbe catturare il re avversario (quindi avviene lo scacco al re)
	public boolean verificaScaccoAlRe(Pezzo pezzo){
		
		int coloreAvversario;
		Pezzo reAvversario = null;
		boolean scacco = false;
		
		if(pezzo.getColore() == bianco)
			coloreAvversario = nero;
		else
			coloreAvversario = bianco;
		
		/*
		 * prende ogni pezzo presente nella scacchiera del colore opposto di quello attaccante,
		 * dopodichè se quel pezzo è il re e il pezzo_attaccante può muoversi nella posizione
		 * del re (quindi potrebbe catturarlo) allora il metodo restituisce true, setta la variabile pezzo_attaccante,
		 * imposta la variabile sottoAttacco a true per ogni pezzo dello stesso colore del re (compreso lui) e
		 * imposta la variabile scaccoAlRe a true
		 */
		
		for(int riga = righe[0]; riga <= righe[7]; riga++){
			for(int colonna = colonne[0]; colonna <= colonne[7]; colonna++){
				if(controllaPezzoColore(coloreAvversario, riga, colonna)){
					reAvversario = getPezzoNonCatturato(riga, colonna);
					if(reAvversario.getTipo() == re && mv.movimentoValido(pezzo.getRiga(), pezzo.getColonna(), riga, colonna)){
						pezzo_attaccante = pezzo;
						for(Pezzo pezzoAvversario : pezzi){
							if(pezzoAvversario.getColore() == reAvversario.getColore())
								pezzoAvversario.setSottoAttacco(true);
						}
						scaccoAlRe = true;
						scacco = true;
					}
				}
			}
		}
		return scacco;
	}
	
	//restituisce true se il re del pezzo inserito in input è sotto scacco al re da parte di un pezzo avversario
	public boolean rischioScaccoAlRe(Pezzo pezzoARischio){
		Pezzo reAlleato = null;
		int coloreAvversario;
		boolean rischioScacco = false;
		
		for(Pezzo pezzo : pezzi)
			if(pezzo.getColore() == pezzoARischio.getColore() && pezzo.getTipo() == re)
				reAlleato = pezzo;
		
		if(pezzoARischio.getColore() == bianco)
			coloreAvversario = nero;
		else
			coloreAvversario = bianco;
			
		/*
		 * se uno qualsiasi dei pezzi avversari può fare scacco al re, allora la variabile scacco al
		 * re viene settata a true
		 */
		
		for(int riga = righe[0]; riga <= righe[7]; riga++){
			for(int colonna = colonne[0]; colonna <= colonne[7]; colonna++){
				if(controllaPezzoColore(coloreAvversario, riga, colonna)){
					if(mv.movimentoValido(riga, colonna, reAlleato.getRiga(), reAlleato.getColonna())){
						rischioScacco = true;
					}
				}
			}
		}
		return rischioScacco;
				
	}
	
	//il metodo si occupa di aggiornare le nuove posizioni di riga e colonna quando un pezzo viene spostato dalla scacchiera
	public void muoviPezzo(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna){
		
		//se non è valido il movimento verso la nuova riga e la nuova colonna il pezzo non può essere spostato
		if(!mv.movimentoValido(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna)){
			return;
		}
		
		Pezzo pezzo = getPezzoNonCatturato(vecchiaRiga, vecchiaColonna);
		
		int coloreOpposto;
		if(pezzo.getColore() == nero)
			coloreOpposto = bianco;
		else
			coloreOpposto = nero;
		
		//se esiste un pezzo nella nuova riga e colonna del colore opposto allora quel pezzo è catturabile 
		if(controllaPezzoColore(coloreOpposto, nuovaRiga, nuovaColonna)){
			Pezzo pezzoOpposto = getPezzoNonCatturato(nuovaRiga, nuovaColonna);
			
			//se il pezzo che vuole muoversi è sotto attacco (scacco al re = true) e il pezzo opposto non
			//è il pezzo che sta attaccando il re allora il pezzo non può muoversi
			if(pezzo.isSottoAttacco() && pezzoOpposto != pezzo_attaccante)
				return;
			
			/*
			 * se è il re il pezzo vuole muoversi ed è sotto attacco e il pezzo che potrebbe catturare
			 * è il pezzo attaccante allora viene controllato se spostando il re nella nuova posizione
			 * (catturando il pezzo attaccante) lo scacco al re scompare allora lo spostamento è permesso,
			 * altrimenti se lo scacco al re rimane a causa di un altro pezzo avversario allora lo spostamento viene permesso  
			 */
			if(pezzoOpposto == pezzo_attaccante && pezzo.isSottoAttacco() && pezzo.getTipo() == re){
				pezzo.setRiga(nuovaRiga);
				pezzo.setColonna(nuovaColonna);
				pezzoOpposto.setCaptured(true);
				for(int riga = righe[0]; riga <= righe[7]; riga++){
					for(int colonna = colonne[0]; colonna <= colonne[7]; colonna++){
						if(controllaPezzoColore(coloreOpposto, riga, colonna)){
							Pezzo temp = getPezzoNonCatturato(riga, colonna);
							if(verificaScaccoAlRe(temp)){
								pezzo.setRiga(vecchiaRiga);
								pezzo.setColonna(vecchiaColonna);
								pezzoOpposto.setCaptured(false);
								pezzo_attaccante = pezzoOpposto;
								return;
							}
						}
					}
				} 
			}
			
			
			/*
			 * se spostando quel pezzo per catturare un pezzo avversario avviene lo scacco
			 * al re del proprio re allora non viene concessa quella mossa e il pezzo ritorna alla
			 * posizione precedente senza aver catturato nessuno
			 */
			pezzo.setRiga(nuovaRiga);
			pezzo.setColonna(nuovaColonna);
			pezzoOpposto.setCaptured(true);
			if(rischioScaccoAlRe(pezzo)){
				pezzoOpposto.setCaptured(false);
				pezzo.setRiga(vecchiaRiga);
				pezzo.setColonna(vecchiaColonna);
				return;
			}
			
			// se il pezzo è stato catturato allora in base al colore vengono incrementate le due variabili, ciò servirà per
			// come coordinate per disegnare i pezzi catturati nelle rispettive zone dei giocatori
			if(pezzo.getColore() == nero){
				nPezziBCatturati++;
				pezzoOpposto.setPosizioneDiCattura(nPezziBCatturati);
			}else{
				nPezziNCatturati++;
				pezzoOpposto.setPosizioneDiCattura(nPezziNCatturati);
			} 
		
		//se la casella è libera nella nuova riga e colonna
		}else{
			/*
			 * se è valido lo scacco al re e il pezzo che vuole muoversi è il re sotto attacco
			 * allora il metodo controlla che se spostando il re nella nuova posizione lo scacco al re
			 * rimane allora non viene permesso lo spostamento, altrimenti viene permesso
			 */
			if(pezzo.isSottoAttacco() && pezzo.getTipo() == re){
				Pezzo app = pezzo_attaccante;
				pezzo.setRiga(nuovaRiga);
				pezzo.setColonna(nuovaColonna);
				for(int riga = righe[0]; riga <= righe[7]; riga++){
					for(int colonna = colonne[0]; colonna <= colonne[7]; colonna++){
						if(controllaPezzoColore(coloreOpposto, riga, colonna)){
							Pezzo temp = getPezzoNonCatturato(riga, colonna);
							if(verificaScaccoAlRe(temp)){
								pezzo.setRiga(vecchiaRiga);
								pezzo.setColonna(vecchiaColonna);
								return;
							}
						}
					}
				}
				pezzo_attaccante = app;
			}
			
		}
		
		pezzo.setRiga(nuovaRiga);
		pezzo.setColonna(nuovaColonna);
		
		/*
		 * se è valido lo scacco al re e spostando il pezzo lo scacco al re resta valido
		 * allora lo spostamento non viene permesso altrimenti viene permesso
		 */
		
		if(pezzo.isSottoAttacco()){
			if(verificaScaccoAlRe(pezzo_attaccante)){		
				pezzo.setRiga(vecchiaRiga);
				pezzo.setColonna(vecchiaColonna);
			}else{
				for(Pezzo pezzoSottoAttacco : pezzi)
					pezzoSottoAttacco.setSottoAttacco(false);

				scaccoAlRe = false;
				cambiaTurno();
			}
			
		}else{
			/*
			 * se non è presente lo scacco al re ma spostando il pezzo il proprio re rischia lo scacco
			 * allora lo spostamento non viene permesso
			 */
			 
			if(rischioScaccoAlRe(pezzo)){
				pezzo.setRiga(vecchiaRiga);
				pezzo.setColonna(vecchiaColonna);	
			}else
				cambiaTurno();
		}
		
	}
	
	//restituisce true se è valido lo scacco matto
	public boolean verificaScaccoMatto(){
		
		int qu_mosse = 0;
		boolean scacco = false;
		
		if(pezzo_attaccante == null)
			return scacco;
		
		int proteggiPezzoAttaccante = 0;
		int vecchiaRiga;
		int vecchiaColonna;
			
		for(Pezzo pezzo : pezzi){
			/*
			 * se il pezzo è sotto attacco allora vengono simulate tutte le possibili mosse del pezzo,
			 * se spostando quel pezzo lo scacco al re si annulla oppure il pezzo può catturare il pezzo attaccante
			 * allora incrementa il numero di mosse possibili (scacco matto = false)
			 */
			if(pezzo.isSottoAttacco()){
				vecchiaRiga = pezzo.getRiga();
				vecchiaColonna = pezzo.getColonna(); 
				for(int riga = righe[0]; riga <= righe[7]; riga++){
					for(int colonna = colonne[0]; colonna <= colonne[7]; colonna++){
						if(mv.movimentoValido(vecchiaRiga, vecchiaColonna, riga, colonna)){
							pezzo.setRiga(riga);
							pezzo.setColonna(colonna);
							// il pezzo si può posizionare in modo da annullare lo scacco al re
							if(riga != pezzo_attaccante.getRiga() || colonna != pezzo_attaccante.getColonna()){
								if(pezzo.getTipo()!=re){
									if(!verificaScaccoAlRe(pezzo_attaccante)){
										qu_mosse++;
									}
								}
							}
							//controllo se il pezzo attaccante è protetto da un altro suo pezzo alleato, se lo è allora il re non può catturarlo
							for(Pezzo pezzoAvversario : pezzi){
								if(pezzoAvversario.getColore() == pezzo_attaccante.getColore() && !pezzoAvversario.equals(pezzo_attaccante)){
									if(pezzo_attaccante.getColore() == nero)
										pezzo_attaccante.setColore(bianco);
									else
										pezzo_attaccante.setColore(nero);
									if(mv.movimentoValido(pezzoAvversario.getRiga(), pezzoAvversario.getColonna(), pezzo_attaccante.getRiga(), pezzo_attaccante.getColonna())){
										proteggiPezzoAttaccante++;
									}
									if(pezzo_attaccante.getColore() == nero)
										pezzo_attaccante.setColore(bianco);
									else
										pezzo_attaccante.setColore(nero);
								}
							}
							//il pezzo attaccante non è protetto quindi se posso catturarlo lo scacco al re si annulla
							if(proteggiPezzoAttaccante == 0){
								if(riga == pezzo_attaccante.getRiga() && colonna == pezzo_attaccante.getColonna()){
									qu_mosse++;
								}
							}
							//il pezzo attaccante è protetto quindi solo il re non può catturarlo
							else{
								if(riga == pezzo_attaccante.getRiga() && colonna == pezzo_attaccante.getColonna() && pezzo.getTipo() != re){
									qu_mosse++;
								}
							}
							pezzo.setRiga(vecchiaRiga);
							pezzo.setColonna(vecchiaColonna);
						}
					}
				}
			}
		}
	 
		if(qu_mosse == 0)
			scacco = true;
		else
			scacco = false;
				
		if(scacco == true)
			scaccoMatto = true;
		else
			scaccoMatto = false;

		return scacco;	
}

	public int getnPezziBCatturati() {
		return nPezziBCatturati;
	}

	public int getnPezziNCatturati() {
		return nPezziNCatturati;
	}
	
	public int getTurno() {
		return turno;
	}
	
	public void setTurno(int turno){
		this.turno = turno;
	}

	public ArrayList<Pezzo> getPezzi() {
		return pezzi;
	}
	
	public boolean isScaccoAlRe() {
		return scaccoAlRe;
	}
	
	public boolean isScaccoMatto() {
		return scaccoMatto;
	}
	
	public MovimentoValido getMv() {
		return mv;
	}
	
	public void cambiaTurno(){
		if(turno == turno_bianco){
			turno = turno_nero;
		}else if(turno == turno_nero){
			turno = turno_bianco;
		}
	}
}
