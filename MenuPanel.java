/*
 * Autores: A01633683 Carlos Ernesto Lopez Solano
 * 			A01633872 Alan Ricardo Gonzalez Aguilar
 * Nombre de la clase (programa): MenuPanel
 * Fecha: 21 de Noviembre de 2018
 * Comentarios u observaciones: perteneciente al proyecto final "Damas Inglesas"
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	public MenuPanel(JFrame jFrame) {
		super();
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(200,200));
		this.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();

		//Titulo del menu
		JLabel title = new JLabel("Escoga una opción");
		title.setHorizontalAlignment(JLabel.CENTER);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		
		this.add(title, gbc);
		
		//Botones de seleccion
		gbc.insets = new Insets(50,0,0,0);
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		
		JButton jbtJugar = new JButton("Jugar: Usuario vs. CPU");
		jbtJugar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();
				TableroFrame tableroFrame = new TableroFrame();
				
			}
		});
		
		this.add(jbtJugar, gbc);
		
		/*
		JButton jbtDemo = new JButton("Demo");
		jbtDemo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();
				CheckersFrame checkersFrame = new CheckersFrame();
			}
		});
		
		this.add(jbtDemo, gbc);
		*/
	}
}
