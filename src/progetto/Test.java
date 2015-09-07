package progetto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test {

	@org.junit.Test
	//per testare il movimento valido del pedone utilizzo 2 movimenti validi uno settato per i pedoni bianchi ed uno settato per i pedoni neri
	//inoltre il pedone non può mai spostarsi di due caselle 
	public void testMovimentoValidoPedone() {
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		assertTrue(mv.movimentoValido(1, 1, 2, 1));
		assertTrue(mv.movimentoValido(6, 1, 5, 1));
		assertFalse(mv.movimentoValido(1, 1, 3, 1));
		assertFalse(mv.movimentoValido(6, 1, 4, 1));
	}
	@org.junit.Test
	//è la somma dei movimenti presi da alfiere e torre
	public void testMovimentoValidoRegina() {
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		assertTrue(mv.movimentoValidoRegina(5, 5, 5, 7));
		assertTrue(mv.movimentoValidoRegina(5, 5, 5, 3));
		assertTrue(mv.movimentoValidoRegina(4, 5, 6, 5));
		assertTrue(mv.movimentoValidoRegina(4, 4, 6, 6));
		assertTrue(mv.movimentoValidoRegina(4, 4, 2, 2));
	}
	@org.junit.Test
	//il cavallo resta settato nella sua posizione iniziale in quanto può saltare i pezzi in mezzo al percorso
	public void testMovimentoCavallo(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		assertTrue(mv.movimentoValidoCavallo(1, 0, 2, 2));
		assertTrue(mv.movimentoValidoCavallo(1, 0, 0, 2));
		assertTrue(mv.movimentoValidoCavallo(1, 7, 0, 5));
		assertTrue(mv.movimentoValidoCavallo(1, 7, 2, 5));
	}
	@org.junit.Test
	//il primo è a false perchè il pedone bianco gli impedisce il movimento
	public void testMovimentoTorre(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		assertFalse(mv.movimentoValidoTorre(0, 0, 0, 4));
		assertTrue(mv.movimentoValidoTorre(4, 4, 4, 7));
		assertTrue(mv.movimentoValidoTorre(4, 4, 6, 4));
	}
	@org.junit.Test
	//viene spostato al centro in modo da avere tutti i movimenti liberi
	public void testMovimentoReLibero(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		assertTrue(mv.movimentoValidoRe(4, 3, 4, 4));
		assertTrue(mv.movimentoValidoRe(4, 3, 4, 2));
		assertTrue(mv.movimentoValidoRe(4, 3, 5, 3));
		assertTrue(mv.movimentoValidoRe(4, 3, 5, 4));
		assertTrue(mv.movimentoValidoRe(4, 3, 5, 3));
		assertTrue(mv.movimentoValidoRe(4, 3, 5, 2));
		assertTrue(mv.movimentoValidoRe(4, 3, 3, 2));
		assertTrue(mv.movimentoValidoRe(4, 3, 3, 4));
	}
	@org.junit.Test
	//verifico che il re non possa muoversi sotto scacco ma se il pezzo viene mangiato quel movimento gli viene concesso
	public void testVietatoMovimentoReSottoScacco(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		Pezzo reB = gs.getPezzoNonCatturato(0, 4);
		reB.setRiga(4);
		reB.setColonna(4);
		if(mv.movimentoValidoRe(reB.getRiga(),reB.getColonna(), 5, 5)){
			reB.setRiga(5);
			reB.setColonna(5);
		}
		assertTrue(gs.rischioScaccoAlRe(reB));
	}
	@org.junit.Test
	//verifica lo scacco al re e la revoca di questo quando il pezzo attaccante viene mangiato dal re se ciò non comporta un ulteriore scacco
	public void testScaccoAlReConMangiata(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		Pezzo reginaN = gs.getPezzoNonCatturato(7, 3);
		Pezzo reB = gs.getPezzoNonCatturato(0, 4);
		reginaN.setRiga(1);
		reginaN.setColonna(4);
		assertTrue(gs.verificaScaccoAlRe(reginaN));
		if(mv.movimentoValidoRe(reB.getRiga(), reB.getColonna(), reginaN.getRiga(), reginaN.getColonna())){
		reB.setRiga(reginaN.getRiga());
		reB.setColonna(reginaN.getColonna());
		}
		assertFalse(gs.verificaScaccoAlRe(reginaN));
	}
	@org.junit.Test
	//verifica lo scacco al re e la revoca di questo quando il pezzo attaccante viene mangiato da un pezzo che può salvare il re
	public void testScaccoAlReConCopertura(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		Pezzo reginaN = gs.getPezzoNonCatturato(7, 3);
		Pezzo reginaB = gs.getPezzoNonCatturato(0, 3);
		reginaN.setRiga(1);
		reginaN.setColonna(4);
		assertTrue(gs.verificaScaccoAlRe(reginaN));
		if(mv.movimentoValidoRegina(reginaB.getRiga(), reginaB.getColonna(), reginaN.getRiga(), reginaN.getColonna())){
		reginaB.setRiga(reginaN.getRiga());
		reginaB.setColonna(reginaN.getColonna());
		reginaN.setCaptured(true);
		}
		assertFalse(gs.verificaScaccoAlRe(reginaN));
	}
	@org.junit.Test
	//un unico pezzo non catturabile impedisce tutti i movimenti al re avversario creando scacco matto
	public void testScaccoMattoPezzoUnico(){
		GiocoScacchi gs = new GiocoScacchi();
		MovimentoValido mv = new MovimentoValido(gs);
		Pezzo reginaN = gs.getPezzoNonCatturato(7, 3);
		Pezzo pedone1B = gs.getPezzoNonCatturato(1, 5);
		Pezzo pedone2B = gs.getPezzoNonCatturato(1, 6);
		pedone1B.setCaptured(true);
		pedone2B.setCaptured(true);
		reginaN.setRiga(3);
		reginaN.setColonna(7);
		if(gs.verificaScaccoAlRe(reginaN)){
			assertTrue(gs.verificaScaccoMatto());
		}	
	}
	@org.junit.Test
	//il pezzo attaccante non può essere mangiato dal re sotto attacco perché coperto da un suo altro pezzo alleato
	//e il re non può muoversi in altre posizioni perché rimane lo scacco al re 
	public void testScaccoMattoPezzoCoperto(){
		GiocoScacchi gs = new GiocoScacchi();
		Pezzo reginaN = gs.getPezzoNonCatturato(7, 3);
		Pezzo torreN = gs.getPezzoNonCatturato(7, 7);
		Pezzo reB = gs.getPezzoNonCatturato(1, 6);
		reginaN.setRiga(4);
		reginaN.setColonna(4);
		torreN.setRiga(4);
		torreN.setColonna(0);
		reB.setRiga(4);
		reB.setColonna(5);
		if(gs.verificaScaccoAlRe(reginaN)){
			assertTrue(gs.verificaScaccoMatto());
		}	
	}
}