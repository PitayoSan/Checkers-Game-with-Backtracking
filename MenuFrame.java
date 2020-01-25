/*
 * Autores: A01633683 Carlos Ernesto Lopez Solano
 * 			A01633872 Alan Ricardo Gonzalez Aguilar
 * Nombre de la clase (programa): MenuFrame
 * Fecha: 21 de Noviembre de 2018
 * Comentarios u observaciones: perteneciente al proyecto final "Damas Inglesas"
 */

import javax.swing.JFrame;

public class MenuFrame extends JFrame {
	
	public MenuFrame() {
		super("Damas Inglesas - Menú Principal");
		this.add(new MenuPanel(this));
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		MenuFrame menuFrame = new MenuFrame();

	}

}
