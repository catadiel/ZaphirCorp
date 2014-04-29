package model;

/**
 * Rappresenta una damiera composta da una matrice di <tt>Cell</tt> <br>
 * 
 *  @see Cell
 *  @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 */
public class Board {
	private final int BLACK = 0;
	private final int WHITE = 1;
	private Cell board [][]=new Cell[8][8];	
	private int howMany[] = {12,12}; 		//Pedine (e dame) in gioco per colore 
	private int damas[] = {0, 0};			//Dame in gioco
	private int counter;					//Contatore di mosse senza mangiate consecutive
	
	/**
	 * Costruisce una nuova damiera di celle e la popola con le pedine
	 * 
	 */
	public Board(){
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if((i+j)%2==0)								//se cella nera
					board[i][j]=new Cell(i, j, false);	//nuova cella nera
				else
					board[i][j]=new Cell(i, j, true);		//nuova cella bianca
		populate();
		counter = 0;
	}
	/**
	 * Crea una copia della damiera passata in oggetto
	 * @param other Board da copiare
	 */
	public Board(Board other){
		this.howMany[BLACK] = other.howMany[BLACK];
		this.howMany[WHITE] = other.howMany[WHITE];
		this.damas[BLACK] = other.damas[BLACK];
		this.damas[WHITE] = other.damas[WHITE];
		this.counter = other.counter;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				this.board[i][j] = new Cell(other.board[i][j]);
		
	}
	/**
	 * Ritorna una cella rispetto le sue coordinate (x,y)
	 * @param x Riga
	 * @param y	Colonna
	 * @return <tt>Cell(x,y)</tt> La cella desiderata
	 * @throws OutOfBorderException
	 */
	public Cell getCell(int x, int y) throws OutOfBorderException{
		if (x<0 || x>7 || y<0 || y>7)			//se non fa parte della damiera lancia eccezione
			throw new OutOfBorderException();
		return board[x][y];
	}
	
	/**
	 * Esegue una mossa di movimento date una cella di partenza e di arrivo
	 * @param start Cella di partenza
	 * @param end	Cella di arrivo
	 */
	public void move(Cell start, Cell end){
		moveInBoard(getCell(start.getX(), start.getY()) , getCell(end.getX(), end.getY()));  //estrae le coordinate delle celle e chiama il metodo movimento della board
	}
	
	
	private void moveInBoard(Cell start, Cell end){
		end.addPawn(start.getPawn()); 				//sposta la pedina
		start.removePawn();							
		growUp(end);								
		counter++;									//incrementa il contatore delle mosse senza presa
		
	}
	/**
	 * Esegue una presa date una cella di inzio, di fine e la cella contenete la pedina da mangiare
	 * @param start Cella di partenza
	 * @param toEat	Cella con la pedina da mangiare
	 * @param end	Cella di arrivo
	 */
	public void eat(Cell start, Cell toEat, Cell end){
		eatInBoard(getCell(start.getX(), start.getY()) ,getCell(toEat.getX() , toEat.getY()) , getCell(end.getX(), end.getY())); //estrae le coordinate delle celle e chiama il metodo presa della board
	}
	
	private void eatInBoard(Cell start, Cell toEat, Cell end){
		moveInBoard(start,end);				// sposta la pedina
		if(toEat.getColorPawn()){			// se pedina (o dama) bianca (valore true)
			howMany[WHITE]--;			
			if( toEat.getPawn() instanceof Dama)	
				damas[WHITE]--;
		}
		else{								// la pedina e' nera
			howMany[BLACK]--;
			if( toEat.getPawn() instanceof Dama)
				damas[BLACK]--;
		}
		toEat.removePawn();					//mangia
		counter = 0;						//reset del contatore di mosse consecutive senza presa
		growUp(end);
	}
	/**
	 * Determina se la partita e' "patta"
	 * @return <code>TRUE</code se sono sate eseguite piu' di 40 mosse consecutive <br>
	 * <code>FALSE</code> altrimenti
	 */
	public boolean isTie(){
		return (counter >= 80);
	}
	
	private void populate(){			// Posiziona le pedine all'inizio della partita
		for(int i=0;i<3;i++)			//Posiziona le nere
			for(int j=0;j<8;j++)
				if(!board[i][j].getColor())				// Se cella nera
					board[i][j].addPawn(new Pawn(false)); //Crea pedina nera
		
		for(int i=5;i<8;i++)			//Posiziona le bianche
			for(int j=0;j<8;j++)			
				if(!board[i][j].getColor())				//Se cella nera
					board[i][j].addPawn(new Pawn(true));//Crea pedina bianca

	}
	/**
	 * Ritorna il numero di pedine dei bianchi e dei neri
	 * @return Array di interi, [0] Neri [1] Bianchi
	 */
	public int[] howMany(){
		return howMany;
	}
	/**
	 * Ritorna il numero di dame dei bianchi e dei neri
	 * @return Array di interi, [0] Neri [1] Bianchi
	 */
	public int[] howManyDama(){
		return damas;
	}
	
	
	
	private void growUp(Cell cell){				//Promuove una pedina a dama
		if(!(cell.getPawn() instanceof Dama) && (cell.getX() == 0 || cell.getX() == 7)){  //se pedina ai limiti scacchiera
			cell.addPawn(new Dama(cell.getColorPawn()));
			if(!cell.getColorPawn())
				damas[BLACK]++;
			else
				damas[WHITE]++;
		}
	}
	/**
	 * Resetta la damiera alle condizioni iniziali
	 */
	public void reset() {
		howMany[BLACK]=12;
		howMany[WHITE]=12;
		damas[BLACK] = 0;
		damas[WHITE] = 0;
		counter = 0;
		remove();		//rimuovo pedine
		populate();		//ripristino in posizione iniziale
		
	}
	
	private void remove() {			//rimuove le pedine
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				if(!board[i][j].isEmpty())
					board[i][j].removePawn();		
	}


}
