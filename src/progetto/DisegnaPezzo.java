package progetto;

import java.awt.Image;

public class DisegnaPezzo {
	
	/*
	 * La classe rappresenta la parte grafica di un pezzo
	 * che verrà disegnata sulla scacchiera
	 */
	
	private Image immagine; // l'immagine del pezzo
	private int x; 			// la sua coordinata x
	private int y;			// la sua coordinata y
	private Pezzo pezzo;	// la parte logica del pezzo
	
	public DisegnaPezzo(Image immagine, Pezzo pezzo){
		this.immagine = immagine;
		this.pezzo = pezzo;
		this.nuovaPosizione();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getLarghezza(){
		return immagine.getWidth(null);
	}
	
	public int getAltezza(){
		return immagine.getHeight(null);
	}
	
	public Image getImmagine(){
		return immagine;
	}
	
	public void setImmagine(Image immagine){
		this.immagine = immagine;
	}
	
	public int getColore(){
		return pezzo.getColore();
	}
	
	public void nuovaPosizione(){
		x = Gui.convertiColonnaInX(pezzo.getColonna());
		y = Gui.convertiRigaInY(pezzo.getRiga());
		
	}
	
	public Pezzo getPezzo(){
		return pezzo;
	}
	
	public boolean isCaptured(){
		return pezzo.isCaptured();
	}
	
	public int getPosizioneDiCattura(){
		return pezzo.getPosizioneDiCattura();
	}
	
	public void setPosizioneDiCattura(int nPezziCatturati){
		pezzo.setPosizioneDiCattura(nPezziCatturati);
	}

}
