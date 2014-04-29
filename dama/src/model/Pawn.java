package model;
/**
 * Definisce una pedina
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class Pawn {
	boolean color; //bianco=true
	
	/**
	 * Crea una pedina del colore passato come parametro
	 * @param color Colore della pedina
	 */
	public Pawn(boolean color){
		this.color=color;
	}
	
	/**
	 * Crea una copia della pedina passata come argomento
	 * @param other Pedina da copiare
	 */
	public Pawn(Pawn other){
		this.color = other.color;
	}
	
	/**
	 * Restituisce il colore della pedina
	 * @return <code>TRUE</code> se bianca <br>
	 * <code>FALSE</code> se nera
	 */
	public boolean getColor(){
		return color;
	}
}
