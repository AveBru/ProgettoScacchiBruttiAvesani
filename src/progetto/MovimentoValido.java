package progetto;

public class MovimentoValido {
	
	/*
	 * questa classe regola i vari movimenti dei singoli pezzi nella scacchiera
	 */

	private static final int bianco = 0;
	
	private static final int torre = 1;
	private static final int cavallo = 2;
	private static final int alfiere = 3;
	private static final int regina = 4;
	private static final int re = 5;
	private static final int pedina = 6;
	
	private static final int[] righe = {0, 1, 2, 3, 4, 5, 6, 7};
	private static final int[] colonne = {0, 1, 2, 3, 4, 5, 6, 7};
	
	private GiocoScacchi giocoScacchi;
	private Pezzo vecchioPezzo;
	private Pezzo nuovoPezzo;
	
	public MovimentoValido(GiocoScacchi giocoScacchi){
		this.giocoScacchi = giocoScacchi;
	}
	//controlla che solo i pezzi adeguati possano fare i loro rispettivi movimenti
	public boolean movimentoValido(int vecchiaRiga,int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		
		vecchioPezzo = giocoScacchi.getPezzoNonCatturato(vecchiaRiga, vecchiaColonna);
		nuovoPezzo = giocoScacchi.getPezzoNonCatturato(nuovaRiga, nuovaColonna);
			
		if( vecchioPezzo == null ){
			return false;
		}
		if( nuovaRiga < righe[0] || nuovaRiga > righe[7]
				|| nuovaColonna < colonne[0] || nuovaColonna > colonne[7]){
			return false;
		}
		
		boolean movimentoPezzoValido = false;
		switch (vecchioPezzo.getTipo()) {
			case alfiere:
				movimentoPezzoValido = movimentoValidoAlfiere(vecchiaRiga,vecchiaColonna,nuovaRiga,nuovaColonna);
				break;
			case re:
				movimentoPezzoValido = movimentoValidoRe(vecchiaRiga,vecchiaColonna,nuovaRiga,nuovaColonna);
				break;
			case cavallo:
				movimentoPezzoValido = movimentoValidoCavallo(vecchiaRiga,vecchiaColonna,nuovaRiga,nuovaColonna);
				break;
			case pedina:
				movimentoPezzoValido = movimentoValidoPedina(vecchiaRiga,vecchiaColonna,nuovaRiga,nuovaColonna);
				break;
			case regina:
				movimentoPezzoValido = movimentoValidoRegina(vecchiaRiga,vecchiaColonna,nuovaRiga,nuovaColonna);
				break;
			case torre:
				movimentoPezzoValido = movimentoValidoTorre(vecchiaRiga,vecchiaColonna,nuovaRiga,nuovaColonna);
				break;
			default: 
				break;
		}
		if(!movimentoPezzoValido){
			return false;
		}
		return true;		
	}
	//controlla che nella casella ci sia un pezzo avversario e quindi catturabile
	public boolean pezzoCatturabile() {
		if( nuovoPezzo == null ){
			return false;
		}
		else if( nuovoPezzo.getColore() != vecchioPezzo.getColore()){
			return true;
		}
		else{
			return false;
		}
	}
	//controlla che la casella non sia occupata da nessun pezzo
	public boolean casellaLibera() {
		return nuovoPezzo == null;
	}
	//può muoversi in tutte le direzioni ma solo diagonalmente
	public boolean movimentoValidoAlfiere(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		
		if(casellaLibera() || pezzoCatturabile()){
			
		}else{
			return false;
		}
		
		boolean valido = false;
		int diffRiga = nuovaRiga - vecchiaRiga;
		int diffColonna = nuovaColonna - vecchiaColonna;
		
		if(diffRiga == diffColonna && diffColonna > 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, +1, +1);
		}
		else if(diffRiga == -diffColonna && diffColonna > 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, -1, +1);	
		}
		else if(diffRiga == diffColonna && diffColonna < 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, -1, -1);
		}
		else if(diffRiga == -diffColonna && diffColonna < 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, +1, -1);
			
		}else{
			valido = false;
		}
		return valido;
	}
	//vengono concessi i movimenti dell'alfiere e della torre
	public boolean movimentoValidoRegina(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		boolean valido = movimentoValidoAlfiere(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna);
		valido |= movimentoValidoTorre(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna);
		return valido;
	}
	/* 
	 * al pedone viene concesso di spostarsi in avanti di una casella e viene distinto il movimento verso l'alto nel caso il pedone sia bianco 
	 * mentre nel caso il pedone sia nero viene mosso verso il basso.
	 * nel caso in cui ci sia un pezzo nemico nelle caselle anteriori diagonali viene concesso il movimento e quindi la mangiata.
	 */
	public boolean movimentoValidoPedina(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		if(casellaLibera()){
			if(vecchiaColonna == nuovaColonna){
				if(vecchioPezzo.getColore() == bianco){
					if(vecchiaRiga+1 == nuovaRiga){
						return true;
					}
					else{
						return false;
					}
				}
				else{
					if(vecchiaRiga-1 == nuovaRiga){
						return true;
					}else{
						return false;
					}
				}
			}
			return false;		
		}else if(pezzoCatturabile()){	
			if(vecchiaColonna+1 == nuovaColonna || vecchiaColonna-1 == nuovaColonna){
				if(vecchioPezzo.getColore() == bianco){
					if(vecchiaRiga+1 == nuovaRiga){
						return true;
					}else{
						return false;
					}
				}else{
					if(vecchiaRiga-1 == nuovaRiga){
						return true;
					}else{
						return false;
					}
				}
			}
			return false;	
		}
		return false;
	}
	// consente il movimento in tutte le direzioni ma solo di una casella 
	public boolean movimentoValidoRe(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		
		if(casellaLibera() || pezzoCatturabile()){
			
		}else{
			return false;
		}
		
		boolean valido = true;
		if(vecchiaRiga+1 == nuovaRiga && vecchiaColonna == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga+1 == nuovaRiga && vecchiaColonna+1 == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga == nuovaRiga && vecchiaColonna+1 == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga-1 == nuovaRiga && vecchiaColonna+1 == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga-1 == nuovaRiga && vecchiaColonna == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga-1 == nuovaRiga && vecchiaColonna-1 == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga == nuovaRiga && vecchiaColonna-1 == nuovaColonna){
			valido = true;
		}
		else if(vecchiaRiga+1 == nuovaRiga && vecchiaColonna-1 == nuovaColonna){
			valido = true;
		}
		else{
			valido = false;
		}
		return valido;
	}
	//consente di muoversi solo in verticale o orizzontale rispetto al pezzo
	public boolean movimentoValidoTorre(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		
		if(casellaLibera() || pezzoCatturabile()){
			
		}else{
			return false;
		}
		boolean valido = false;
		int diffRiga = nuovaRiga - vecchiaRiga;
		int diffColonna = nuovaColonna - vecchiaColonna;
		
		if(diffRiga == 0 && diffColonna > 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, 0, +1);
		}
		else if(diffRiga == 0 && diffColonna < 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, 0, -1);
		}
		else if(diffRiga > 0 && diffColonna == 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, +1, 0);
		}
		else if(diffRiga < 0 && diffColonna == 0){
			valido = !pezziNelPercorso(vecchiaRiga, vecchiaColonna, nuovaRiga, nuovaColonna, -1, 0);	
		}
		else{
			valido = false;
		}
		return valido;
	}
	//concede solo i movimenti ad L tipici del cavallo 
	public boolean movimentoValidoCavallo(int vecchiaRiga, int vecchiaColonna, int nuovaRiga, int nuovaColonna) {
		if(casellaLibera() || pezzoCatturabile()){
			
		}else{
			return false;
		}
		if(vecchiaRiga+2 == nuovaRiga && vecchiaColonna+1 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga+1 == nuovaRiga && vecchiaColonna+2 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga-1 == nuovaRiga && vecchiaColonna+2 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga-2 == nuovaRiga && vecchiaColonna+1 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga-2 == nuovaRiga && vecchiaColonna-1 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga-1 == nuovaRiga && vecchiaColonna-2 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga+1 == nuovaRiga && vecchiaColonna-2 == nuovaColonna){
			return true;
		}
		else if(vecchiaRiga+2 == nuovaRiga && vecchiaColonna-1 == nuovaColonna){
			return true;
		}
		else{
			return false;
		}
	}
	//questo metodo controlla se ci sono alleati nel percorso del pezzo che si vuole muovere
	public boolean pezziNelPercorso(int vecchiaRiga, int vecchiaColonna,int nuovaRiga, int nuovaColonna, int incremento1Riga, int incremento1Colonna) {
		
		int rigaCorrente = vecchiaRiga + incremento1Riga;
		int colonnaCorrente = vecchiaColonna + incremento1Colonna;
		while(true){
			if(rigaCorrente == nuovaRiga && colonnaCorrente == nuovaColonna){
				break;
			}
			if( rigaCorrente < righe[0] || rigaCorrente > righe[7]
				|| colonnaCorrente < righe[0] || colonnaCorrente > colonne[7]){
				break;
			}

			if( giocoScacchi.controllaPezzo(rigaCorrente, colonnaCorrente)){
				return true;
			}

			rigaCorrente += incremento1Riga;
			colonnaCorrente += incremento1Colonna;
		}
		return false;
	}
}
