package progetto;

public class Pezzo {
	
	/*
	 * questa classe contiene tutte le informazioni dei pezzi
	 * per regolare gli spostamenti e le regole del gioco
	 */
	
	private int colore;				//il colore del pezzo
	private int tipo;				//il tipo di pezzo
	private int riga;				//la riga attuale
	private int colonna;			//la colonna attuale
	private boolean isCaptured;		//true se il pezzo è stato catturato
	private boolean sottoAttacco;	//true se il re alleato è sotto scacco al re
	private int posizioneDiCattura; //il momento nella quale è stato catturato 
									//e serve per disegnare il pezzo nella zona giocatore in posizione giusta

	public Pezzo(int colore, int tipo, int riga, int colonna){
		this.colore = colore;
		this.tipo = tipo;
		this.riga = riga;
		this.colonna = colonna;
		this.isCaptured = false;
		this.sottoAttacco = false;
		this.posizioneDiCattura = 0;
	}

	public int getColore() {
		return colore;
	}

	public void setColore(int colore) {
		this.colore = colore;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getRiga() {
		return riga;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public int getColonna() {
		return colonna;
	}

	public void setColonna(int colonna) {
		this.colonna = colonna;
	}

	public boolean isCaptured() {
		return isCaptured;
	}

	public void setCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public boolean isSottoAttacco() {
		return sottoAttacco;
	}

	public void setSottoAttacco(boolean sottoAttacco) {
		this.sottoAttacco = sottoAttacco;
	}

	public int getPosizioneDiCattura() {
		return posizioneDiCattura;
	}

	public void setPosizioneDiCattura(int posizioneDiCattura) {
		this.posizioneDiCattura = posizioneDiCattura;
	}
}
