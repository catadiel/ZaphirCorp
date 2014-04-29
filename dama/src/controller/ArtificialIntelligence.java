package controller;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import model.*;


/**
 * Rappresenta l'intelligenza artificiale
 * 
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 */

public class ArtificialIntelligence{
	private SortedSet<Step> blackSteps_Move;		//MOSSE SENZA MANGIATA
	private SortedSet<Step> blackSteps_Eat;			//MOSSE CON MANGIATA
	private Board board;
	private Executor executor;
	private Random random;
	private Step stepToDo;							//MOSSA EFFETTIVA DA FARE
	private boolean status; 						//STATO DELLA PARTITA: TRUE SE PC BLOCCATO, FALSE ALTRIMENTI
	
	/**
	 * Costruttore che inizializza l'intelligenza
	 * @param board damiera su cui si sta giocando
	 * @see Board
	 */
	
	public ArtificialIntelligence ( Board board ) {
		this.board = board;
		executor = new Executor();
		status = false;
		random = new Random();
	}
	
	/**
	 * Metodo per il calcolo delle mosse migliori.
	 */
	
//	public void playPC(){											//CALCOLO MOSSA MIGLIORE
//		
//		blackSteps_Move = new TreeSet<Step>();		
//		blackSteps_Eat = new TreeSet<Step>(); 				
//		
//		for(int i = 0; i < 8; i++)
//			for(int j = 0; j < 8; j++){
//				if (!board.getCell(i, j).getColor() && !board.getCell(i, j).isEmpty() && !board.getCell(i, j).getColorPawn()){
//					goBlack(i, j);								//CREO ELENCO MOSSE MIGLIORI
//					}
//				}
//		
//		if(blackSteps_Eat.isEmpty() && blackSteps_Move.isEmpty())		//SE LE LISTE SONO VUOTE IL PC E' BLOCCATO
//			status = true;
//		else if (!blackSteps_Eat.isEmpty())	{							//MANGIATA CASUALE TRA QUELLE DI EGUAL PUNTEGGIO
//			stepToDo = randomEatChoice();
//			executor.execute(board, stepToDo);
//		}
//		else {
//			stepToDo = randomMoveChoice();								//MOVIMENTO CASUALE TRA QUELLE DI EGUAL PUNTEGGIO
//			executor.execute(board, stepToDo);
//		}
//		
//	}
	
	public Step playPC(){											//CALCOLO MOSSA MIGLIORE
	
	blackSteps_Move = new TreeSet<Step>();		
	blackSteps_Eat = new TreeSet<Step>(); 				
	
	for(int i = 0; i < 8; i++)
		for(int j = 0; j < 8; j++){
			if (!board.getCell(i, j).getColor() && !board.getCell(i, j).isEmpty() && !board.getCell(i, j).getColorPawn()){
				goBlack(i, j);								//CREO ELENCO MOSSE MIGLIORI
				}
			}
	
	if(blackSteps_Eat.isEmpty() && blackSteps_Move.isEmpty())		//SE LE LISTE SONO VUOTE IL PC E' BLOCCATO
		status = true;
	else if (!blackSteps_Eat.isEmpty())	{							//MANGIATA CASUALE TRA QUELLE DI EGUAL PUNTEGGIO
		stepToDo = randomEatChoice();
		return stepToDo;
	}
	else {
		stepToDo = randomMoveChoice();								//MOVIMENTO CASUALE TRA QUELLE DI EGUAL PUNTEGGIO
		return stepToDo;
	}
	
	return null;
}

		
	private Step randomMoveChoice() {								//CALCOLO MOSSA CASUALE PER MOVIMENTO
		
		Step[] possibilities = new Step[blackSteps_Move.size()];
		int maxEat = blackSteps_Move.last().getEatablePawn();
		int pointer = 0;
		
		for(Step possible : blackSteps_Move){
			if (possible.getEatablePawn() == maxEat)
				possibilities[pointer++] = possible;
		}
		
		return possibilities[random.nextInt(pointer)];
	}

	
	private Step randomEatChoice() {								//CALCOLO MOSSA CASUALE PER MANGIATA
		
		Step[] possibilities = new Step[blackSteps_Eat.size()];
		int maxEat = blackSteps_Eat.last().getEatablePawn();
		int pointer = 0;
		
		for(Step possible : blackSteps_Eat){
			if (possible.getEatablePawn() == maxEat)
				possibilities[pointer++] = possible;
		}
		
		return possibilities[ random.nextInt(pointer) ];
	}

	
	
	private void goBlack (int x, int y ){							//CREO ELENCO MOSSE MIGLIORI
		
		Step chosen;
		SortedSet<Step> whiteSteps = new TreeSet<Step>();
		Board copyboard = new Board(board);											//BOARD COPIA PER CALCOLARE LE MOSSE E CONTROMOSSE MIGLIORI DEL PC
		ValidStepsList tempBlack = new ValidStepsList( copyboard, x, y, true );
		
		if ( !tempBlack.isEmpty() ){
			for( Step thisStep:tempBlack ){
				
				copyboard = new Board(board);
				chosen = thisStep;
				
				executor.execute(copyboard,chosen );
				for(int i=0;i<8;i++)
					for(int j=0;j<8;j++){
						if(!copyboard.getCell(i,j).getColor() && !copyboard.getCell(i,j).isEmpty() && copyboard.getCell(i,j).getColorPawn()){//cella nera pedina bianca
							ValidStepsList tempWhite=new ValidStepsList(copyboard,i,j,true);
							if(!tempWhite.isEmpty())
								whiteSteps.add(tempWhite.bestStep());
			
						}
					}
				if(chosen.getRoute() == null){
					if(!whiteSteps.isEmpty())
						chosen.setEatablePawn(chosen.compareTo(whiteSteps.last()));
					blackSteps_Move.add(chosen);
				}
				else{
					if(!whiteSteps.isEmpty())
						chosen.setEatablePawn(chosen.compareTo(whiteSteps.last()));
					blackSteps_Eat.add(chosen);
					
				}
			}

		}
		
	}

	/**
	 * Controllo dello stato dell'intelligenza artificiale
	 * @return <code>TRUE</code> se il computer non puo' piu' muovere<br>
	 * 		   <code>FALSE</code> altrimenti
	 */
	
	public boolean blocked() {			//RITORNA TRUE SE IL PC NON HA PIU' MOSSE DA FARE
		return status;
	}

	

}




