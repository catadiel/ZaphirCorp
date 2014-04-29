package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Pannello per la visualizzazione delle regole
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class BackgroundRules extends JPanel{
	private Image image;
	
	/**
	 * Costruisce il pannello per la visualizzazione delle regole
	 */
	public BackgroundRules(){
		final String rules = "Le pedine muovono sempre in avanti di una casella sulle caselle scure e quando\n raggiungono la base avversaria diventano dame.\n La dama viene contrassegnata da una corona.\nLa dama muove in avanti o indietro di una casella sempre sulle caselle scure.\n La presa e' obbligatoria: quando una pedina incontra una pedina di colore\n diverso, con una casella libera dietro, sulla stessa diagonale, e' obbligata a prenderla\n (si dice anche catturarla o mangiarla). La pedina, dopo la prima presa, qualora si trovi\n nelle condizioni di poter nuovamente\n prendere, deve continuare a catturare pezzi (fino a\n un massimo di tre), potendo anche prendere la dama.\n La pedina puo' prendere solo in avanti, lungo le due diagonali.\n La dama puo' prendere (catturare, mangiare) su tutte e quattro le diagonali.\n La dama, dopo la prima presa, qualora si trovi nelle condizioni di poter\n nuovamente prendere, deve continuare a catturare pezzi.\n \n Sia per la dama sia per la pedina e' obbligatorio prendere dalla par ove c'e' il maggior\n numero di pezzi in presa.\n Se una dama e una pedina possono prendere un egual numero di pezzi, o una dama\n puo'˛ scegliere tra la presa di una dama e di una pedina, e' obbligatorio prendere con il\n pezzo di maggior qualita' , cioe' la dama. Se una dama ha la possibilita' di scegliere tra\n prese di un egual numero di pezzi di qualita' diversa, deve prendere dalla parte ove\n c'e' una maggior qualita' complessiva. Se una dama puo' scegliere tra prese di eguale\nqualita' compessiva, deve prendere dalla parte ove incontra per primo un pezzo di\n maggior qualita'. Nel caso le possibilita' siano 'pari', anche dopo l'applicazione dei\n criteri di cui sopra, il giocatore scegliera' secondo le proprie esigenze tattiche.\n \nI risultati possibili al termine di una partita sono due:\nVittoria: uno dei due contendenti vince sull'altro o l'avversario non puo' fare nessuna mossa;\nPartita patta: se non viene eseguita nessuna presa per 40 turni.";
		image = new ImageIcon("images/rules.png").getImage();
		JTextPane rulesPane = new JTextPane();
		rulesPane.setForeground(Color.white);
		rulesPane.setOpaque(false);
		rulesPane.setText(rules);
		rulesPane.setFont(new Font("Papyrus", Font.BOLD, 15));
		rulesPane.setEditable(false);
		add(rulesPane);
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
}

}
