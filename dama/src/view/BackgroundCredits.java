package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Pannello per la visualizzazione dei credits
 * @author <i>Mattia Rebesan, Carlo Tadiello, Andrea Tirapelle</i>
 *
 */
public class BackgroundCredits extends JPanel{
	
	private Image image;
	/**
	 * Costruisce il pannello per la visualizzazione dei credits
	 */
	public BackgroundCredits(){
		image = new ImageIcon("images/credit.png").getImage();
		JLabel developed = new JLabel("Developed by Andrea, Carlo, Mattia.");
		JLabel rights = new JLabel("All rights reserved.");
		JLabel corp = new JLabel("ZaphirCorp");
		
		developed.setFont(new Font("Papyrus", Font.BOLD, 20));
		developed.setForeground(new Color(232,232,156));
		
		rights.setFont(new Font("Papyrus", Font.BOLD, 20));
		rights.setForeground(new Color(232,232,156));
		
		corp.setFont(new Font("Papyrus", Font.BOLD, 20));
		corp.setForeground(new Color(232,232,156));
		
		add(developed);
		add(rights);
		add(corp);
	}
		
	
		@Override
	    protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(image, 0, 0, null);
	}
}
