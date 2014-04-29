package model;

/**
 * Definisce l'eccezione causata dal tentativo di accesso ad una cella che non fa parte della damiera
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
@SuppressWarnings("serial")
public class OutOfBorderException extends IllegalArgumentException {

	/**
	 * Crea l'eccezione
	 */
	public OutOfBorderException() {
		super ("OutOfBorder Exeption: Location provided exceed board dimension.");
	}
}
