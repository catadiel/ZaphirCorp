package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.Iterator;

import controller.*;
import model.*;

/**
 * Pannello che visualizza la partita in corso e permette all'utente di eseguire una mossa
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel implements Runnable{
	
	private Tile[][] boardButton;					// tile della damiera
	private Board board;
	private Tile[] humanStep;						// selzione dell'utente
	private ValidStepsList validatedSteps;
	private Executor executor;
	private ArtificialIntelligence ai;
	private Checker checker;
	private int eatenByHuman;
	private boolean forceNextStep;					// forza la presa multipla dell'utente
 	
	/**
	 * Costruisce il pannello
	 */
	public BoardPanel(){
		eatenByHuman = 0;
		forceNextStep = false;
		boardButton = new Tile[8][8];
		humanStep = new Tile[2];
		executor = new Executor();
		board = new Board();
		ai = new ArtificialIntelligence(board);
		checker = new Checker (board, ai);
		
		
		setLayout(new GridLayout(8,8));
		buildTiles(true);
	}
	
	
	/**
	 * Reimposta i pezzi e i componenti grafici per iniziare una nuova partita
	 */
	public void newGame() {
		board.reset();
		UserPanel.resetIcon();
		revalidate();
		refresh(true);
		
		
	}
 	

	/**
	 * Esegue una mossa del PC e richiama il checker per stabilire lo stato della partita
	 * @see Checker
	 */
	@Override
	public void run(){
		Step pcStep;
		pcStep = ai.playPC();
		if(pcStep != null){
				
			boardButton[pcStep.getStart().getX()][pcStep.getStart().getY()].setBorder(BorderFactory.createLineBorder(Color.blue, 3));
			boardButton[pcStep.getEnd().getX()][pcStep.getEnd().getY()].setBorder(BorderFactory.createLineBorder(Color.blue, 3));
					
			try {
				if(pcStep.getRoute() == null)
					Thread.sleep(600);
				else
					Thread.sleep(1000);
			} catch (InterruptedException e) { }
			
			executor.execute(board, pcStep);
		}
		refresh(true);
		int status = checker.gameStatus();
		if (!(status == 2)){
			endGame(status);
		}
	
		
	}
	
	
//	private void lightPcStep(Step pcStep) {
//			
//		
//	}


	private void addListener(Tile tile, int i, int j){
		final int x = i;
		final int y = j;
		boardButton[x][y].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//se c'e' pedina bianca e tile non premuto
				if(!board.getCell(x, y).getColor() && !board.getCell(x, y).isEmpty() && board.getCell(x, y).getColorPawn() && !boardButton[x][y].isPressed() && !forceNextStep) { 
					boardButton[x][y].setPressed(true);
					
					if( humanStep[0] == null ){ //primo tile premuto 
						humanStep[0] = boardButton [x][y];
						humanStep[1] = null;
						validatedSteps = new ValidStepsList( board, x, y, false ); 
						lightValidStep(validatedSteps,true);
					}
					
					else{ //secondo tile premuto
						
						humanStep[1] = boardButton [x][y]; 
						rightChoice();
					}
				}
				
				//disabilita selezione precendente
				else if(!board.getCell(x, y).getColor() && !board.getCell(x, y).isEmpty() && board.getCell(x, y).getColorPawn() && boardButton[x][y].isPressed() && !boardButton[x][y].isLocked() ){
					//
					humanStep[0] = null;
					humanStep[1] = null; 
  					lightValidStep(validatedSteps, false);
					boardButton[x][y].setPressed(false);
					validatedSteps = null;
				}
				
				else if(!board.getCell(x, y).getColor() && board.getCell(x, y).isEmpty() && !(humanStep[0]==null)){
					// secondo tile selezionato vuoto
					boardButton[x][y].setPressed(true);
					humanStep[1] = boardButton [x][y];
					rightChoice();
					}
	
			}

			});
			
	}
	

	private void buildTiles(boolean listener){ // crea e aggiunge i Tile al pannello 

		for( int i = 0; i <8; i++)
			for(int j = 0; j <8; j++){
				boardButton[i][j] = new Tile(i, j, board);
				add(boardButton[i][j]);
				if(listener)				 // se listener == TURE aggiungo il listener ai Tile
					addListener(boardButton[i][j], i, j);
			}
	}
 
 	
	private void endGame(int winner){ // esito della partita: 0 pareggio 1 vittoria utente -1 sconfitta utente
		String title = "La partita e' terminata";
		String message;
		
		if(winner == 0)
			message = "Parita'!\nOra puoi iniziare una nuova partita oppure uscire.";
		else if(winner == 1 )
			message = "Bravo\nOra puoi iniziare una nuova partita oppure uscire.";
		else
			message = "Hai perso\nOra puoi iniziare una nuova partita oppure uscire.";
		
		Object[] optionButton = { "Nuova Partita", "Esci" };
		
		int choice = JOptionPane.showOptionDialog(null, message, title, JOptionPane.ERROR_MESSAGE, JOptionPane.INFORMATION_MESSAGE,null, optionButton, null);
		
		switch(choice){

			case 0: newGame();
					revalidate();
					break;//resetgame

			default: System.exit(0);;//exit
		}
		
	}
	
	
	private boolean expectedCell(){ //contolla se la cella cliccata su cui muovere appartiene ad una mossa legale
		for(Step temp:validatedSteps){
			if(temp.getEnd().equals(board.getCell(humanStep[1].getx(), humanStep[1].gety()))){
				return true;
			}
		}
		return false;
	}
	
	
	private void lightValidStep(ValidStepsList steps, boolean lightOnOFF){ //illumina o spegne le mosse legali per il giocatore umano
		Iterator<Step> tempo = steps.iterator();
		while(tempo.hasNext()){
			Step temp = tempo.next();
			if(lightOnOFF){
				boardButton[temp.getEnd().getX()] [temp.getEnd().getY()].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			}
			
			else{
				boardButton[temp.getEnd().getX()] [temp.getEnd().getY()].setBorder(null);
			}
		}
	}

	
	private void refresh(boolean listener) { //aggiorna la grafica
		
		for(int a=0; a <8; a++)
			for(int b=0; b <8; b++)
				remove(boardButton[a][b]);
		
		buildTiles(listener);
		UserPanel.refreshIcons(board.howMany()[1], board.howManyDama()[1], board.howMany()[0], board.howManyDama()[0]);
		revalidate();
	}
	
	
	private void rightChoice() { 		// Controlla se i Tile selezionati rappresentano una mossa valida
		int X0 = humanStep[0].getx();
		int Y0 = humanStep[0].gety();
		int X1 = humanStep[1].getx();
		int Y1 = humanStep[1].gety();
		
		
		if(expectedCell()){				//se la mossa e' corretta la eseguo 
			Step temp=null;
			for(Step temporary:validatedSteps)
				if(temporary.getEnd().getX() == X1 && temporary.getEnd().getY() == Y1)
					temp=temporary;
			executor.execute(board, temp);
			
			
			boardButton[X0][Y0].setPressed(false);
			lightValidStep(validatedSteps, false);
			boardButton[X1][Y1].setPressed(false);
			refresh(false);
			
			if(temp.getEatablePawn() > 0 && eatenByHuman<2 ){
				refresh(true);
				eatenByHuman++;
				humanStep[0]=humanStep[1]; //reset della mossa utente per proseguire con la mangiata consecutiva
				X0=X1;
				Y0=Y1;
				humanStep[1]=null;
				boardButton[X0][Y0].setPressed(true);	//non devo poter spegere questo bottone
				boardButton[X0][Y0].lock();
				forceNextStep=true;
				
				
				validatedSteps= new ValidStepsList(board, X0, Y0);  //creo una validStepList vuota
				Cell thisCell= board.getCell(X0, Y0);
				validatedSteps.uiValidator(X0, Y0, 0, null, thisCell);	//aggiungo le mosse valide alla lista
			
				if(!validatedSteps.isEmpty()){				 // se posso ancora mangiare
					lightValidStep(validatedSteps, true);
				}
				else{
					StartThread();
				}
			}
			else{
				StartThread();
			}
		}
		else if( board.getCell(X1, Y1).getPawn()==null){ //se non c'e' una pedina spegne il Tile
			boardButton[X1][Y1].setPressed(false);
			boardButton[X1][Y1].setBorder(null);
			humanStep[1]=null;
			
		}
		
		// se le pedine sono entrambe bianche spegne la prima ed evidenzia la seconda
		else if ( board.getCell(X1, Y1).getColorPawn() == board.getCell(X0, Y0).getColorPawn() ) {
			lightValidStep(validatedSteps, false);
			boardButton[X0][Y0].setPressed(false);
			
			humanStep[0] = humanStep[1];
			humanStep[1] = null;
			X0=X1;
			Y0=Y1;
			validatedSteps=new ValidStepsList( board, X0, Y0, false);
			lightValidStep(validatedSteps , true);
		}	
															
	}
	
	
	private void StartThread(){
		int X0 = humanStep[0].getx();
		int Y0 = humanStep[0].gety();
		
		eatenByHuman=0;
		forceNextStep=false;
		humanStep[0].unlock();
		boardButton[X0][Y0].setBorder(null);
		humanStep[0]=null;
		new Thread(this).start();
	}
		
	}

	


