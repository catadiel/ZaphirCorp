package model;
/**
 * Definisce una dama come estensione di una pedina
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *@see Pawn
 */
public class Dama extends Pawn {

	/**
	 * Crea una dama del colore passato come argomento
	 * @param color Colore della dama
	 */
	public Dama(boolean color) {
		super(color);
	}
	
	/**
	 * Crea una dama copia di quella passata come argomento
	 */
	public Dama(Pawn other) {
		super(other.color);
	}
}
