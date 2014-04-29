package model;
/**
 * Definisce una cella formata dal proprio colore, una pedina e la propria posizione
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class Cell {
	private boolean color; //bianco = true
	private Pawn pawn;
	private int x, y;
	
	/**
	 * Crea una nuova cella vuota
	 * @param x Riga
	 * @param y Colonna
	 * @param color Colore della cella, TRUE = bianco
	 */
	public Cell( int x, int y, boolean color){
		this.color=color;
		this.x=x;
		this.y=y;
		pawn=null;
	}
	
	
	/**
	 * Crea una copia della cella passata come argomento
	 * @param other Cella da copiare
	 */
	public Cell (Cell other){
		this.color = other.color;
		if (other.pawn != null)
			if(other.pawn instanceof Dama)
			this.pawn = new Dama(other.pawn);
			else
				this.pawn = new Pawn(other.pawn);
			else this.pawn = null;
		
		this.x = other.x;
		this.y = other.y;
	}
	
	/**
	 * Restituisce la riga di una cella
	 * @return int riga
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Restituisce colonna di una cella
	 * @return int colonna
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Ritorna se una cella e' vuota
	 * @return TRUE se vuota
	 */
	 public boolean isEmpty(){
		return pawn==null;
	}
	
	 /**
	  * Aggiunge la pedina passata come argomento alla cella
	  * @param pawn Pedina da aggiungere
	  */
	public void addPawn(Pawn pawn) {
		this.pawn=pawn;
	}
	
	/**
	 * Rimuove la pedina contenuta nella cella
	 */
	public void removePawn(){
		pawn=null;
	}
	
	/**
	 * Restituisce la pedina contenuta nella cella
	 * @return la pedina in oggetto
	 */
	public Pawn getPawn(){
		return pawn;
	}
	
	/**
	 * Ritorna il colore della pedina contenuta nella cella
	 * @return <code>TRUE</code> se bianca <br>
	 * <code>FALSE</code> se nera
	 * @see Pawn
	 */
	public boolean getColorPawn(){ //TODO refactor
		return pawn.getColor();
	}
	
	/**
	 * Ritorna il colore della cella
	 * @return <code>TRUE</code> se bianca <br>
	 * <code>FALSE</code> se nera
	 */
	public boolean getColor(){
		return color;
	}
	
	/**
	 * Controlla se la cella passata come argomento e' uguale alla corrente (identica posizione)
	 * @param other Cella da confrontare
	 * @return <code>TRUE</code> se uguali <br>
	 * <code>FALSE</code> se diverse
	 */
	public boolean equals(Cell other){
		return ( x == other.x && y == other.y );
	}

}
//VARIABILI PRIVATE!!!!