package progetto;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PromozionePedina extends JFrame {
	
	/*
	 * questa classe stampa una nuova finestra con i bottoni dei possibili pezzi
	 * con la quale la pedina può essere promossa
	 */
	
	private String colore;
	private static final int bianco = 0;
	private static final int torre = 1;
	private static final int cavallo = 2;
	private static final int alfiere = 3;
	private static final int regina = 4;
	public PromozionePedina(DisegnaPezzo pezzo, ArrayList<DisegnaPezzo> pezzi) {
		JPanel p = new JPanel();
		p.setSize(320, 80);
		JButton bt = new JButton();
		bt.setSize(75, 75);
		bt.setVisible(true);
		bt.setBorder(null);
		JButton bc = new JButton();
		bc.setSize(75, 75);
		bc.setVisible(true);
		bc.setBorder(null);
		JButton ba = new JButton();
		ba.setSize(75, 75);
		ba.setVisible(true);
		ba.setBorder(null);
		JButton br = new JButton();
		br.setSize(75, 75);
		br.setVisible(true);
		br.setBorder(null);
		if(pezzo.getColore() == bianco)
			colore = "B";
		else
			colore = "N";
		ImageIcon imageBT = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Torre"+colore+".png");
		ImageIcon imageBC = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Cavallo"+colore+".png");
		ImageIcon imageBA = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Alfiere"+colore+".png");
		ImageIcon imageBR = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Regina"+colore+".png");
		Image imageT = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Torre"+colore+".png").getImage();
		Image imageC = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Cavallo"+colore+".png").getImage();
		Image imageA = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Alfiere"+colore+".png").getImage();
		Image imageR = new ImageIcon("../ProgettoScacchiBruttiAvesani/immagini/Regina"+colore+".png").getImage();
		bt.setIcon(imageBT);
		bc.setIcon(imageBC);
		ba.setIcon(imageBA);
		br.setIcon(imageBR);
		/* aggiunge i bottoni e quindi la possibilità di essere promossa in cavallo alfiere o torre solo nel caso in cui almeno uno dei due pezzi
		 * sia stato mangiato
	     */
		for(DisegnaPezzo pezzoTemp : pezzi){
			if(pezzoTemp.getPezzo().getTipo() == torre && pezzoTemp.isCaptured() && pezzo.getColore() == pezzoTemp.getColore()){
				bt.addActionListener(new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent event){
						pezzo.getPezzo().setTipo(torre);
						pezzo.setImmagine(imageT);
						p.setVisible(false);
					}
				});
				p.add(bt);
			}
			if(pezzoTemp.getPezzo().getTipo() == cavallo && pezzoTemp.isCaptured() && pezzo.getColore() == pezzoTemp.getColore()){
				bc.addActionListener(new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent event){
						pezzo.getPezzo().setTipo(cavallo);
						pezzo.setImmagine(imageC);
						p.setVisible(false);
					}
				});
				p.add(bc);
			}
			if(pezzoTemp.getPezzo().getTipo() == alfiere && pezzoTemp.isCaptured() && pezzo.getColore() == pezzoTemp.getColore()){
				ba.addActionListener(new java.awt.event.ActionListener(){
					public void actionPerformed(ActionEvent event){
						pezzo.getPezzo().setTipo(alfiere);
						pezzo.setImmagine(imageA);
					}
				});
				p.add(ba);
			}
		}
		//nel caso della regina invece potendo giocare con più di una regina in campo viene sempre concessa la promozione
		br.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent event){
				pezzo.getPezzo().setTipo(regina);
				pezzo.setImmagine(imageR);
				p.setVisible(false);
			}
		});
		p.add(br);
		add(p);
		
	}
}

