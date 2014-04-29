package controller;

import model.Board;

/**
 * Classe che controlla lo stato del gioco
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */

public class Checker {
	
	private Board board;
	private ArtificialIntelligence ai;

/**
 * Costruttore che inizializza il Checker
 * @param board damiera da controllare
 * @param ai intelligenza artificiale che sta giocando, a cui chiedere se e' bloccata
 * 
 * @see Board
 * @see ArtificialIntelligence
 */
	
	
	public Checker(Board board, ArtificialIntelligence ai) {
		this.board = board;
		this.ai = ai;
}

/**
 * Controlla lo stato totale del gioco
 * @return <code>0</code> Partita patta<br>
 * <code>1</code> Intelligenza Artificiale bloccata o utente vincitore<br>
 * <code>-1</code> Utente bloccato o Intelligenza Artificiale bloccata<br>
 * <code>2</code> Altrimenti
 * @see ArtificialIntelligence
 */
	public int gameStatus(){
		if (board.isTie())
			return 0;
		else if(userBlocked() || board.howMany()[1]==0)			//Utente bloccato o pc vincitore
			return -1;
		else if(ai.blocked() || board.howMany()[0]==0)			//Pc bloccato o user vincitore
			return 1;
	
		return 2;
}

	
	private boolean userBlocked() {				//Per ogni pedina dell'utente controlla che ci sia almeno una mossa
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				if(!board.getCell(i, j).isEmpty() && board.getCell(i, j).getColorPawn()){
					if(!new ValidStepsList(board, i, j, false).isEmpty()){
						return false;
				}
			}
	return true;					//TRUE se bloccato
	}
}
