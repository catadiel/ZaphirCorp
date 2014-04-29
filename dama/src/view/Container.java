package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

/**
 * Pannello che contiene un <tt>BoardPanel</tt> e una cornice
 * @see BoardPanel
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class Container extends JPanel {
	private BoardPanel DamaPanel;

	
	/**
	 * Costruisce il pannello
	 */
	public Container(){
		DamaPanel = new BoardPanel();
		setLayout(new BorderLayout());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		p1.setBackground(new Color(195,195,195));
		p1.setOpaque(true);
		p2.setBackground(new Color(195,195,195));
		p2.setOpaque(true);
		p3.setBackground(new Color(195,195,195));
		p3.setOpaque(true);
		add(p1, BorderLayout.NORTH);
		//add(new JPanel(), BorderLayout.SOUTH);
		add(p2, BorderLayout.EAST);
		add(p3, BorderLayout.WEST);
		add(DamaPanel, BorderLayout.CENTER);
	}

	
	/**
	 * Imposta il BoardPanel per iniziare una nuova partita
	 */
	public void newGame() {
		DamaPanel.newGame();
		revalidate();
		
	}
}
