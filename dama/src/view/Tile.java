package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.Board;
import model.Dama;

/**
 * Bottone grafico rappresentante una cella e una pedina, dove presente
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */

public class Tile extends JButton {
	private final int x,y;
	private boolean pressed;
	private boolean lock;
	private final ImageIcon BLACK = new ImageIcon("images/nera.png");
	private final ImageIcon WHITE = new ImageIcon("images/bianca.png");
	private final ImageIcon BLACKDAMA  = new ImageIcon("images/dama_nera.png");
	private final ImageIcon WHITEDAMA = new ImageIcon("images/dama_bianca.png");
	private final ImageIcon WHITEBG = new ImageIcon("images/cellabianca.png");
	private final ImageIcon BLACKBG = new ImageIcon("images/cellanera.png");
	
	/**
	 * Costruttore che inizializza il pulsante
	 * @param x riga del bottone
	 * @param y colonna del bottone
	 * @param board damiera di riferimento per il bottone
	 */
	
	public Tile(int x, int y, Board board){
		pressed = false;
		this.x=x;
		this.y=y;
		setOpaque(true);
		if(!(board.getCell(x, y).getColor() ) ){			//cella nera, sfondo nero
			setIcon(BLACKBG);
				}
		else{												//cella bianca, sfondo bianco
			setIcon(WHITEBG); 
		}
		
		
		if(!board.getCell(x,y).isEmpty())								//aggiunge le pedine
			if(!(board.getCell(x,y).getColorPawn()))
				if(!(board.getCell(x,y).getPawn() instanceof Dama))
					add(new JLabel(BLACK));
				else 
					add(new JLabel(BLACKDAMA));
			
			else
				if(!(board.getCell(x,y).getPawn() instanceof Dama))
					add(new JLabel(WHITE));
				else 
					add(new JLabel(WHITEDAMA));
			
		setBorder(null);
		
	}
	
	/**
	 * Restituisce la riga in cui e' contenuto il bottone
	 * @return riga del bottone
	 */
	
	public int getx(){
		return x;
	}
	
	/**
	 * Restituisce la colonna in cui e' contenuto il bottone
	 * @return colonna del bottone
	 */
	
	public int gety(){
		return y;
	}
	
	
	/**
	 * Ritorna se il bottone e' stato premuto
	 * @return <code>TRUE</code> se il bottone e' premuto <br>
	 * <code>FALSE</code> se il bottone non e' premuto
	 * @see #setPressed(boolean)
	 */

	public boolean isPressed(){
		return pressed;
	}

	
	/**
	 * Imposta un bottone come premuto o meno, in base al parametro <code>pressed</code>
	 * @param pressed booleano usato per settare il bottone premuto se vale <code>TRUE</code>
	 * @see #isPressed()
	 */
	
	public void setPressed(boolean pressed){
		this.pressed=pressed;
		if (pressed)
			setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
		else
			setBorder(null);
	}
	
	/**
	 * Blocca un pulsante, un pulsante bloccato non puo' essere premuto
	 * @see #unlock()
	 * @see #isLocked()
	 */
	
	public void lock(){
		lock=true;
	}
	
	/**
	 * Sblocca un pulsante
	 * @see #lock()
	 * @see #isLocked()
	 */
	public void unlock(){
		lock=false;
	}
	
	/**
	 * Controlla se un bottone e' bloccato
	 * @return
	 * @see #lock()
	 * @see #unlock()
	 */
	
	public boolean isLocked(){
		return lock;
	}
	
}