package controller;

import model.Board;
import model.Step;

/**
 * Esecutore delle mosse
 * 
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class Executor {
	
	
	/**
	 * Esecutore della mossa ricevuta nella board designata
	 * 
	 * @param board Damiera su cui eseguire la mossa
	 * @param step Mossa da eseguire
	 * 
	 * @see Board
	 * @see Step
	 */
	
	public void execute(Board board, Step step) {
		
		if (step.getRoute() == null){						//Mossa di movimento
			board.move(step.getStart(), step.getEnd());
		}
		else{												//Mossa con presa
			int i = 0;
			if(step.getRoute() != null){
				for(; i<(step.getRoute()).length-2; ){
					board.eat(step.getRoute()[i], step.getRoute()[i+1], step.getRoute()[i+2]);
					i = i+2;
				}
			board.eat(step.getRoute()[i], step.getRoute()[i+1], step.getEnd());
			}		
		}
			
	}

	
	
}
