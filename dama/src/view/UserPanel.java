package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Pannello che tiene conto visivamente dei punteggi 
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */

public class UserPanel extends JPanel{
	private static JLabel[] iconsW = new JLabel[12];
	private static JLabel[] iconsB = new JLabel[12];
	private static ImageIcon whiteIcon = new ImageIcon ( "images/iconabianca.png" );
	private static ImageIcon blackIcon = new ImageIcon ( "images/iconanera.png" );
	private static ImageIcon greyIcon = new ImageIcon ( "images/iconagrigia.png" );
	private static ImageIcon whiteDamaIcon = new ImageIcon ("images/icona_dama_bianca.png");
	private static ImageIcon blackDamaIcon = new ImageIcon ("images/icona_dama_nera.png");
	private JPanel blacks;
	private JPanel whites;
	private JLabel user;
	private Image image;
	
	/**
	 * Costruttore che inizializza il nome dell'utente e il conteggio delle pedine
	 * @param name Stringa contenente il nome utente
	 */
	
	public UserPanel( String name ){
		image =  new ImageIcon("images/Panel.png").getImage();
		Dimension size = new Dimension(600, 80);
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
		setSize(size);
		setLayout( new BorderLayout() );
	
		blackPanel();
		add(blacks, BorderLayout.SOUTH);
		whitePanel(name);
		add(whites, BorderLayout.NORTH);
		
		
			
		
	}
	@Override
	public void paintComponent(final Graphics g) {
	    g.drawImage(image, 0,0,null);  					//stampa a sfondo image
	}

	private void whitePanel(String name) {		// crea il pannello del conteggio delle pedine bianche
		whites = new JPanel();
		whites.setLayout(new FlowLayout());
		whites.setOpaque(false);
		user = new JLabel(name);
		user.setFont(new Font("Papyrus", Font.BOLD, 20));
		user.setForeground(Color.black);
		whites.add(user);
		for( int i = 0; i < 12; i++ ){
			iconsW[i] = new JLabel();
			iconsW[i].setIcon( whiteIcon );
			whites.add(iconsW[i]);
		}
		
	}

	private void blackPanel() {					// crea il pannello del conteggio delle pedine nere
		blacks = new JPanel();
		blacks.setLayout(new FlowLayout());
		blacks.setOpaque(false);
		JLabel pc = new JLabel("PC");
		pc.setForeground(Color.black);
		pc.setFont(new Font("Papyrus", Font.BOLD, 20));
		blacks.add(pc);
		for( int i = 0; i < 12; i++ ){
			iconsB[i] = new JLabel();
			iconsB[i].setIcon( blackIcon);
			blacks.add(iconsB[i]);
		}
	}
	
	
	/**
	 * Aggiorna le icone in base a quante ne sono state mangiate
	 * @param white numero di pedine bianche totali presenti
	 * @param whiteDama numero di dame bianche presenti 
	 * @param black numero di pedine nere totali presenti
	 * @param blackDama numero di dame nere presenti
	 */
	
	public static void refreshIcons(int white, int whiteDama, int black, int blackDama) {								//white = pedine mangiate bianche; black = pedine mangiate nere
		for(int i = 11; i > white - 1; i--)
			iconsW[i].setIcon( greyIcon );
		for(int i = 11; i > black - 1; i--)
			iconsB[i].setIcon( greyIcon );
		
		for(int i = 0; i < white; i++){
			if( i < whiteDama)
				iconsW[i].setIcon(whiteDamaIcon);
			else
				iconsW[i].setIcon(whiteIcon);
		}
		
		for(int i = 0; i < black; i++){
			if( i < blackDama)
				iconsB[i].setIcon(blackDamaIcon);
			else
				iconsB[i].setIcon(blackIcon);
		}
	}

	/**
	 * Cambia il nome del giocatore e resetta le icone
	 * @param userName Stringa contenente il nome del giocatore
	 */
	public void newUser(String userName) {
		user.setText(userName);
		resetIcon();
		
	}
	
	
	/**
	 * Resetta la grafica delle icone
	 */
	protected static void resetIcon() {
		for(int i = 0; i < 12; i++){
			iconsW[i].setIcon( whiteIcon );
			iconsB[i].setIcon( blackIcon );
		}
		
	}
	
	}

