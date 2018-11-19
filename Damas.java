import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;

public class Damas extends JFrame {

	//Container
	private Container contents;
	
	//Buttons
	private JButton[][] buttons = new JButton[8][8];
	
	//Colors
	private Color colorBlack = Color.BLACK,
				  colorSelected = Color.GREEN;
	
	//Positions
	private int[][] pieces = new int[8][8];
	
	//Images
	private ImageIcon redPiece = new ImageIcon("Red Piece.png");
	private ImageIcon whitePiece = new ImageIcon("White Piece.png");
	
	//Validaciones
	
	//True = turno del usuario
	//False = turno del CPU
	private boolean isTurn = true;
	
	//True = pieza seleccionada
	//False = pieza no seleccionada
	private boolean isPieceSelected = true;
	
	//Ultima posicion
	int lastRow = 0,
		lastCol = 0;
	
	
	
	//Constructor
	public Damas() {
		super("Damas Inglesas - Por favor seleccione solo las piezas de su color correspondiente");
		
		contents = getContentPane();
		contents.setLayout(new GridLayout(8,8));
		
		ButtonHandler bh = new ButtonHandler();
		
		//Instanciar botones en array de cuadrados
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				buttons[i][j] = new JButton();
				if((i + j) % 2 != 0) {
					buttons[i][j].setBackground(colorBlack);
				}
				
				contents.add(buttons[i][j]);
				buttons[i][j].addActionListener(bh);
			}
		}
		//Insertar piezas default y asignar iconos a los botones
		//1 = usuario
		//2 = CPU
		for(int l = 0; l < 8; l++) {
			//TODO revisar SetIcon
			if(l % 2 == 0) {
				pieces[0][l] = 2;
				buttons[0][l].setIcon(whitePiece);
				pieces[2][l] = 2;
				buttons[2][l].setIcon(whitePiece);
				pieces[6][l] = 1;
				buttons[6][l].setIcon(whitePiece);
			} else {
				pieces[1][l] = 2;
				buttons[1][l].setIcon(redPiece);
				pieces[5][l] = 1;
				buttons[5][l].setIcon(redPiece);
				pieces[7][l] = 1;
				buttons[7][l].setIcon(redPiece);
			}
		}
		
		/*
		//imprimir matriz de piezas
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(pieces[i][j] + ", ");
			}
			System.out.println();
		}
		*/
		this.setSize(800,800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//Revisar si un movimiento es valido
	private boolean isValidMove(int i, int j) {
		int tempRow = Math.abs(i - lastRow);
		int tempColumn = Math.abs(j - lastCol);
		
		if(tempRow == 1) {
			return true;
		}
		
		return false;
	}
	
	//Cambio de posicion de la pieza
	private void changePosition(int i, int j) {
		if(!isValidMove(i,j)) {
			//TODO Revisar
			JOptionPane.showMessageDialog(this, "Por favor realiza un movimiento legal");
		} else {
			buttons[lastRow][lastCol].setIcon(null);
			if(isTurn) {
				buttons[i][j].setIcon(redPiece);
			} else {
				buttons[i][j].setIcon(whitePiece);
			}
			lastRow = i;
			lastCol = j;
			
		}
	}


	private class ButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(isTurn) {
				Object source = e.getSource();
				
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						if(source == buttons[i][j]) {
							if(isPieceSelected) {
								changePosition(i,j);
								return;
							} else {
								buttons[i][j].setBackground(colorSelected);
								lastRow = i; 
								lastCol = j;
								isPieceSelected = true;
							}
						} else {
							JOptionPane.showMessageDialog(this, "Por favor selecciona una pieza primero");
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Por favor espera tu turno");
			}
		}
	}
	
	public static void main(String[] args) {
		Damas dama = new Damas();

	}

}

