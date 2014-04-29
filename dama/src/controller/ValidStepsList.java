package controller;


import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import model.*;


/**
 * Lista ordinata di mosse valide per una pedina
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 * @see Step
 *
 */

public class ValidStepsList implements Iterable<Step> {
	Board board;
	int x, y;
	private SortedSet<Step> validStepsList = new TreeSet<Step>();
	boolean turn;
	
	
	/**
	 * Costruttore della lista
	 * @param board damiera da cui generare la lista
	 * @param x riga della pedina su cui calcolare la lista delle mosse
	 * @param y colonna della pedina su cui calcolare la lista delle mosse
	 * @param turn esplicita se la lista viene costruita per il pc o per l'utente
	 */
	
	public ValidStepsList(Board board,int x, int y, boolean turn){
		this.x = x;
		this.y = y;
		this.board = board;
		this.turn = turn;
		validStep();
	}
	
	
	/**
	 * Costruttore che non inizializza la lista
	 * @param board damiera da cui generare la lista
	 * @param x riga della pedina su cui calcolare la lista delle mosse
	 * @param y colonna della pedina su cui calcolare la lista delle mosse
	 */
	public ValidStepsList(Board board, int x, int y) {
		this.x = x;
		this.y = y;
		this.board = board;
		this.turn = false;
	}
	
	
	/**
	 * Controllo se la lista e' vuota
	 * @return <code>TRUE</code> se e' vuota <br>
	 * <code>FALSE</code> se non lo e'
	 */
	public boolean isEmpty(){
		return validStepsList.isEmpty();
	}
	
	/**
	 * Costruisce l'insieme ordinato crescente rispetto all'efficacia delle mosse possibili
	 * @see Step
	 */
	
	//Costruisce l'insieme ordinato crescente rispetto all'efficacia (numero e tipo di pedine mangiate) delle mosse possibili
	public void validStep(){
		
		// Se dama
		if(board.getCell(x, y).getPawn() instanceof Dama){
			moveDama(turn);
		}
		else
			//Se bianca
			if(board.getCell(x, y).getPawn().getColor()){
				// calcola le mosse possibili se la pedina bianca
				moveWhite(turn);
				
			}
			//Se nera
			else{
				// calcola le mosse possibili se la pedina nera
				moveBlack(turn);
			}
		
		
	
	}

	
	/**
	 * Ritorna la lista delle mosse valide
	 * @return lista delle mosse valide
	 */
	//Ritorna l'insieme delle mosse valide
	public SortedSet<Step> getValidSteps(){
	
		return validStepsList;
	}
	
	
	
	/**
	 * Costruisce la lista di mosse con presa per una pedina dell'utente
	 */
	
	public void uiValidator(int x, int y, int eaten, Cell[] route, Cell start){
		if (start.getPawn() instanceof Dama)
			moveBlackIfFull(x, y, eaten, route, false);
		moveWhiteIfFull( x, y, eaten, route, false);
	}
	
	
	// Calcola le mosse possibili se Dama.
	// La Dama puo' essere vista, per i movimenti, come una pediana sia bianca che nera( muove verso la propria base oltre che verso l'avversario )
	private void moveDama(boolean turn) {
		moveBlack(turn);
		moveWhite(turn);
		
			
	}
	
																		// Calcola le mosse possibili se la pedina bianca
	private void moveWhite(boolean turn) {
	
		// adiacenze vuote?
		boolean full = false;
		try{
																		//Se la casella successiva esiste vuota, valida la mossa
			if(board.getCell(x-1, y+1).isEmpty()) {
				validStepsList.add(new Step(board.getCell(x, y),board.getCell(x-1, y+1),null,0));
				}
			else 
																		//Indica presenza di possibile presa
				full=true;
		}
		catch(Exception e){}
		
		try{
		
			if(board.getCell(x-1, y-1).isEmpty()){    //Se la casella successiva esiste vuota, valida la mossa
				validStepsList.add(new Step(board.getCell(x, y),board.getCell(x-1, y-1),null,0));
			}
			else 
				
				full=true; //Indica presenza di possibile presa
		}
		catch(Exception e){}
		// controllo prese
		if(full){
			moveWhiteIfFull(x, y, 0,null, turn);
		}
	}
	
	
	/**
	 * Ritorna la migliore mossa della lista
	 * @return mossa migliore della lista
	 */
	//Ritorna la mossa migliore tra quelle possibili
	public Step bestStep(){
			
			return validStepsList.last();
			
			
		}
		
	// Calcola le mosse possibili se la pedina nera
	private void moveBlack(boolean turn){
		//adiacenze vuote?
		boolean full = false;
		try{
			if(board.getCell(x+1, y+1).isEmpty())
				validStepsList.add(new Step(board.getCell(x, y),board.getCell(x+1, y+1),null,0));
			else 
				full = true;
		}
		catch(Exception e){}
		
		try{
			if(board.getCell(x+1, y-1).isEmpty())
				validStepsList.add(new Step(board.getCell(x, y),board.getCell(x+1, y-1),null,0));
			else 
				full = true;
		}
		catch(Exception e){}
		//se una piena
		if(full){
			
			moveBlackIfFull(x, y, 0, null, turn);
			
		}
		
	}
	
	// Metodo ricorsivo che calcola tutte le mosse possibili in caso di mangiata se la pedina o dama che muove bianca
	private void moveWhiteIfFull(int x, int y, int eaten, Cell[] route, boolean turn){
		int nextEaten;
		Cell[] newRoute;
		try{
			if(route == null){	
				newRoute = new Cell[2];
				newRoute[0] = board.getCell(x,y);
				}
			else{
				newRoute = new Cell[route.length+2];
				copyArray(route, newRoute);
				newRoute[newRoute.length-2] = board.getCell(x,y);
				}
			if(!board.getCell(x-1,y+1).isEmpty())
				if(!(board.getCell(x-1,y+1).getColorPawn() == newRoute[0].getColorPawn() )){
					if(board.getCell(x-2,y+2).isEmpty()){
						newRoute[newRoute.length-1] = board.getCell(x-1,y+1);
						nextEaten = board.getCell(x-1,y+1).getPawn() instanceof Dama ? eaten+10 : eaten + 1;
						validStepsList.add(new Step(board.getCell(x, y),board.getCell(x-2, y+2), newRoute, nextEaten ));
						
						//se gioca pc si richiama
					if(turn && newRoute.length < 6){	// blocca la ricorsione se gioca utente o pc ha raggiunto 3 mangiate consecutive
						if(board.getCell(x,y).getPawn() instanceof Dama)
							moveBlackIfFull(x+2, y+2, nextEaten, newRoute, turn);
						moveWhiteIfFull(x-2, y+2, nextEaten,newRoute, turn);}
					}
				}
		}
		catch(Exception e){}
		
		try{
			if(route == null){				
				newRoute = new Cell[2];
				newRoute[0] = board.getCell(x, y);
				}
			else{
				newRoute = new Cell[route.length+2];
				copyArray(route, newRoute);
				newRoute[newRoute.length-2] = board.getCell(x, y);
				}
			
			if(!board.getCell(x-1, y-1).isEmpty())
				if(!(board.getCell(x-1,y-1).getColorPawn() == newRoute[0].getColorPawn() )){
					if(board.getCell(x-2,y-2).isEmpty()){
						newRoute[newRoute.length-1] = board.getCell(x-1,y-1);
						nextEaten = board.getCell(x-1,y-1).getPawn() instanceof Dama ? eaten+10 : eaten + 1;
						validStepsList.add(new Step(board.getCell(x, y),board.getCell(x-2, y-2), newRoute, nextEaten ));
						//se gioca pc si richiama
						if(turn && newRoute.length<6){
							if(board.getCell(x,y).getPawn() instanceof Dama)
								moveBlackIfFull(x+2, y+2, nextEaten, newRoute, turn);
							moveWhiteIfFull(x-2, y-2, nextEaten,newRoute, turn);}
						}
				}
			}
		catch(Exception e){}
	}

	// Metodo ricorsivo che calcola tutte le mosse possibili in caso di mangiata se la pedina o dama che muove nera
	private void moveBlackIfFull(int x, int y, int eaten, Cell[] route, boolean turn){
		int nextEaten;
		Cell[] newRoute;
		try{
			if(route == null){				
				newRoute = new Cell[2];
				newRoute[0] = board.getCell(x,y);
				}
			else{
				newRoute = new Cell[route.length+2];
				copyArray(route, newRoute);
				newRoute[newRoute.length-2] = board.getCell(x,y);
				}
			
			if(!board.getCell(x+1,y+1).isEmpty())
				if(!(board.getCell(x+1,y+1).getColorPawn() == newRoute[0].getColorPawn() )){
					
						if(board.getCell(x+2,y+2).isEmpty()){
							newRoute[newRoute.length-1] = board.getCell(x+1,y+1);
							nextEaten = board.getCell(x+1,y+1).getPawn() instanceof Dama ? eaten+10 : eaten + 1;
							validStepsList.add(new Step(board.getCell(x, y),board.getCell(x+2, y+2), newRoute, nextEaten));
							//se gioca pc si richiama
							if(turn && newRoute.length < 6){
								if(board.getCell(x,y).getPawn() instanceof Dama)
									moveWhiteIfFull(x+2, y+2, nextEaten, newRoute, turn);
								moveBlackIfFull(x+2, y+2, nextEaten, newRoute, turn);}
						}
				}
		}
		catch(Exception e){}
		try{
			if(route == null){				
				newRoute = new Cell[2];
				newRoute[0] = board.getCell(x,y);
				}
			else{
				newRoute = new Cell[route.length+2];
				copyArray(route, newRoute);
				newRoute[newRoute.length-2] = board.getCell(x,y);
				}
			
			if(!board.getCell(x+1,y-1).isEmpty())
				if(!(board.getCell(x+1,y-1).getColorPawn()==newRoute[0].getColorPawn() )){
						if(board.getCell(x+2,y-2).isEmpty()){
							newRoute[newRoute.length-1]=board.getCell(x+1,y-1);
							nextEaten = board.getCell(x+1,y-1).getPawn() instanceof Dama ? eaten+10 : eaten + 1;
							validStepsList.add(new Step(board.getCell(x, y),board.getCell(x+2, y-2), newRoute, nextEaten));
							//se gioca pc si richiama
							if(turn && newRoute.length<6){
								if(board.getCell(x,y).getPawn() instanceof Dama)
									moveWhiteIfFull(x+2, y+2, nextEaten, newRoute, turn);
								moveBlackIfFull(x+2, y-2, nextEaten,newRoute, turn);}
						}
				}
			}
		catch(Exception e){}
	}
	// TODO copyarray non mi piace;
	private void copyArray(Cell[] one, Cell[] two) {
		for(int i = 0; i < one.length ; i++ ){
			two[i] = new Cell(one[i]); 
		}
	}
	
	
	
	/**
	 * Ritorna un iteratore sulla lista
	 */
	public Iterator<Step> iterator() {
		return validStepsList.iterator();
	}
	
	






}
