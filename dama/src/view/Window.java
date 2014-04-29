package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
/**
 * Finestra contenente tutti gli elementi grafici
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 * @see Container
 * @see UserPanel
 *
 */
public class Window extends JFrame {
	

	private UserPanel userPanel;
	private String userName = new String("Utente");
	private JMenuBar bar;
	private JMenu game;
	private JMenu help;
	private JMenuItem rules;
	private JMenuItem credits;
	private JMenuItem newGame;
	private Container container;
	private JMenuItem resetGame;
	private JMenuItem exit;

	/**
	 * Costruttore che crea la finestra grafica
	 */
	public Window(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize( new Dimension( 600, 700 ) );
		setIconImage((new ImageIcon("images/iconDama.png").getImage()));
		setResizable(false);
		setLayout( new BorderLayout() );
		
		container = new Container();
		setMenu();
		
		userPanel = new UserPanel( "NuovoGiocatore" );
		
		
		add( container, BorderLayout.CENTER );
		add( userPanel, BorderLayout.SOUTH );
		
		setVisible(true);
		userName=JOptionPane.showInputDialog("Inserisci un nome!");
		
		if(userName== null || userName.length() == 0)
			userName="NuovoGiocatore";
		userPanel.newUser(userName);
		
		setTitle("Dama - " + userName + " vs Pc");
	}

	private void setMenu() {			//crea e personalizza la barra menu' e i sotto menu' con i relativi action listener
		bar = new JMenuBar();
		game = new JMenu("Partita");
		help = new JMenu("Aiuto...");
		rules = new JMenuItem("Regole");
		credits = new JMenuItem("Crediti");
		newGame = new JMenuItem("Nuova Partita...");
		resetGame = new JMenuItem("Ricomincia Partita");
		exit = new JMenuItem("Esci");
		
		exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		});
		
		resetGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chooseNewGame(false);
		}
		});
		
		newGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chooseNewGame(true);
		}
		});
		
		rules.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				JFrame regole = new JFrame("Regole della Dama Italiana");	//finestra regole con pannello regole
				regole.setSize(new Dimension(700, 650));
				regole.setResizable(false);
				BackgroundRules background = new BackgroundRules();
				regole.add(background);
				regole.setVisible(true);
				
				
			}
		});
		
		credits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame credit = new JFrame("I creatori");					//finestra credits con pannello credits
				BackgroundCredits background = new BackgroundCredits();
				credit.setSize(new Dimension(600,120));
				credit.add(background);
				credit.setVisible(true);
			}
		});
		
		
		help.add(rules);
		help.add(credits);
		
		game.add(resetGame);
		game.add(newGame);
		game.addSeparator();
		game.add(exit);
		
		bar.add(game);
		bar.add(help);
		setJMenuBar(bar);
	}
	
	
	
	private void chooseNewGame(boolean name){   //seleziona nuovo gioco con possibilita' di cambiare nome
		if(!name){								//name == FALSE allora non cambio nome
			container.newGame();
			userPanel.resetIcon();
		}
		else{									//name == TRUE allora cambio nome
			userName=JOptionPane.showInputDialog("Inserisci un nome!");
			if(userName== null || userName.length() == 0)
				userName="NuovoGiocatore";
			container.newGame();
			setTitle("Dama - " + userName + " vs Pc");
			userPanel.newUser( userName );
		}
	}
 
}
