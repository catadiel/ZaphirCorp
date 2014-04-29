package model;

/**
 * Definisce una mossa
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class Step implements Comparable<Step>{
	Cell start,end;
	int eatablePawn = 0;		//numero di pedine che possono essere mangiate durante la mossa
	private Cell[] route;		//sequenza di celle interessate dalla mossa
	
	/**
	 * Crea una mossa
	 * @param start Cella di partenza
	 * @param end	Cella di arrivo
	 * @param route Array di celle interessate dalla mossa
	 * @param etablePawn Pedine che posso mangiare
	 */
	public Step(Cell start, Cell end, Cell[] route, int etablePawn){ //TODO costuttore senza route ed etablepawn
		this.start=start;
		this.end = end;
		this.eatablePawn = etablePawn;
		this.route=route;
	}

	/**
	 * Confronta la mossa corrente co quella passata per argomento
	 * @return <code>1</code> se la differenza tra i valori di etablepawn e' 0 <br>
	 * La <code>differenza tra i valori di etablepawn</code> negli altri casi 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Step other) {
		
		if( eatablePawn-other.eatablePawn == 0)
			return 1;
		else
			return (eatablePawn-other.eatablePawn);
	}
	
	/**
	 * Modifica il valore della variabile etablePawn con il valore intero passato in oggetto
	 * @param other nuovo valore di etablePawn
	 */
	public void setEatablePawn(int other){
		eatablePawn=other;
	}

	/**
	 * Ritorna il numero di pedine che possono essere mangiate durante la mossa
	 * @return numero di pedine mangiabili
	 */
	public int getEatablePawn() {
		return eatablePawn;
	}

	/**
	 * Ritorna la cella finale della mossa
	 * @return cella finale
	 * @see Cell
	 */
	public Cell getEnd() {
		return end;
	}
	
	/**
	 * Ritorna la cella iniziale della mossa
	 * @return cella iniziale
	 * @see Cell
	 */
	public Cell getStart() {
		return start;
	}	

	/**
	 * Ritorna sequenza di celle che compongono la mossa
	 * @return sequenza delle celle componenti la mossa
	 * @see Cell
	 */
	public Cell[] getRoute(){
		return route;
	}
	
	/**
	 * La mossa corrente e quella psaata come argomento sono uguali (uguale lungehezza e composte dalle stesse celle)
	 * @param other mossa da confrontare
	 * @return <code>TRUE</code> se uguali<br>
	 * <code>FALSE</code> se diverse
	 */
	public boolean equals(Step other){
		if(route.length != other.getRoute().length || eatablePawn-other.eatablePawn != 0) // stessa lunghezza > 0 
			return false;
		for(int i = 0; i < route.length; i++){ //coppie di celle allo stesso indice uguali
			if(route[i].getX() != other.getRoute()[i].getX() || route[i].getY() != other.getRoute()[i].getY() )
				return false;
		}
		return true;
	}
	}

