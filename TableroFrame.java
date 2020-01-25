/*
 * Autores: A01633683 Carlos Ernesto Lopez Solano
 * 			A01633872 Alan Ricardo Gonzalez Aguilar
 * Nombre de la clase (programa): TableroFrame
 * Fecha: 21 de Noviembre de 2018
 * Comentarios u observaciones: perteneciente al proyecto final "Damas Inglesas"
 * 								Aqui se encuentra la mayoria del codigo que hace funcionar
 * 								el tablero y el movimiento de las piezas
 */


import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TableroFrame extends JFrame {
	
	//Algoritmo
	private Algorithm algorithm;
	
	//Piezas
	private int piecesLeft;
	
	//Contenedor
	private Container contents;

	//Botones
	private JButton[][] buttons;
	
	//Colors
	private Color colorBlack = Color.BLACK,
				  colorWhite = Color.WHITE,
				  colorSelected = Color.GREEN;
	
	//Posiciones ocupadas
	private int[][] pieces;

	//Imagenes
	//AQUI debe ingresar la direccion de las imagenes en el directorio local de su computadora
	//Las imagenes deben estar dentro de la carpeta del proyecto para que dicha direccion funcione
	private ImageIcon redPiece = new ImageIcon("C:\\Users\\Carlos López\\eclipse-workspace\\Checkers\\src\\blue.png"),
					  whitePiece = new ImageIcon("C:\\Users\\Carlos López\\eclipse-workspace\\Checkers\\src\\alan.png");
		
	//Validaciones
	
	//True = turno del usuario
	//False = turno del CPU
	private boolean isTurn;
	
	//True = pieza seleccionada
	//False = pieza no seleccionada
	private boolean isPieceSelected;
	
	//Ultima posicion
	int lastRow = 0,
		lastCol = 0;
	
	
	public TableroFrame() {
		super("Damas Inglesas - Por favor seleccione solo las piezas de su color correspondiente");
		
		this.piecesLeft = 12;
		this.buttons = new JButton[8][8];
		this.pieces = new int[8][8];
		this.isTurn = true;
		this.isPieceSelected = false;
		this.algorithm = new Algorithm();
		
		contents = getContentPane();
		contents.setLayout(new GridLayout(8,8));
		
		ButtonHandler bh = new ButtonHandler();
		
		//Instanciar botones en array de cuadrados
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				buttons[i][j] = new JButton();
				if((i + j) % 2 != 0) {
					buttons[i][j].setBackground(colorBlack);
				} else {
					buttons[i][j].setBackground(colorWhite);
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
				pieces[1][l] = 2;
				buttons[1][l].setIcon(whitePiece);
				pieces[5][l] = 1;
				buttons[5][l].setIcon(redPiece);
				pieces[7][l] = 1;
				buttons[7][l].setIcon(redPiece);
			} else {
				pieces[0][l] = 2;
				buttons[0][l].setIcon(whitePiece);
				pieces[2][l] = 2;
				buttons[2][l].setIcon(whitePiece);
				pieces[6][l] = 1;
				buttons[6][l].setIcon(redPiece);
			}
		}
		
		/*
		//imprimir matriz de piezas inicial
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		JOptionPane.showMessageDialog(null, "Comienza el juego! Jugador, tienes la primera jugada", "^-^", JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Comienza el Juego");
		System.out.println("---------------------------------");
		System.out.println();
	}
	
	//Definir ganador
	private void winner() throws Exception {
		if(piecesLeft == 0) {
			System.out.println("Se encontro ganador: jugador");
			JOptionPane.showMessageDialog(null, "JUGADOR GANA!!!!", "GANADOR", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			MenuFrame mf = new MenuFrame();
			throw new Exception("Ganador!!!");
		}
		if(algorithm.pieces == 0) {
			System.out.println("Se encontro ganador: maquina");
			JOptionPane.showMessageDialog(null, "MAQUINA GANA!!!!", "GANADOR", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			MenuFrame mf = new MenuFrame();
			throw new Exception("Ganador!!!");
		}
		
		for(int i = 0; i < 8; i++) {
			if(pieces[0][i] == 1) {
				System.out.println("Se encontro ganador: jugador");
				JOptionPane.showMessageDialog(null, "JUGADOR GANA!!!!", "GANADOR", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				MenuFrame mf = new MenuFrame();
				throw new Exception("Ganador!!!");
			}
			
			if(pieces[7][i] == 2) {
				System.out.println("Se encontro ganador: maquina");
				JOptionPane.showMessageDialog(null, "MAQUINA GANA!!!!", "GANADOR", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				MenuFrame mf = new MenuFrame();
				throw new Exception("Ganador!!!");
			}
		}
	}
	
	//Turno de la maquina
	private void machinesTurn() {
		algorithm.move();
		
		System.out.println(); 
		System.out.println("Jugada realizada por Algorithm");
		System.out.println();
		
		this.updateMatrix();
		this.isTurn = true;
	}

	//Revisar si un movimiento es valido
	//Devuelve:
	//0 = no tiene movimientos / el movimiento no es valido
	//1 = el movimiento es valido
	//2 = el movimiento involucra comer piezas por la izquierda
	//3 = el movimiento involucra comer piezas por la derecha
	private int isValidMove(int i, int j) {
		if ((j == lastCol + 1 || j == lastCol - 1) && i == lastRow - 1) {
			if(((j == lastCol + 1 || j == lastCol - 1) && i == lastRow ) || (pieces[i][j] != 0) || ((i == lastRow + 1 || i == lastRow - 1) && j == lastCol )) {
				JOptionPane.showMessageDialog(null, "El movimiento no es valido", "Atención", JOptionPane.INFORMATION_MESSAGE);
				return 0;
			}
			return 1;
		} else if (((j == lastCol - 2 && i == lastRow - 2) && pieces[lastRow - 1][lastCol - 1] == 2 && pieces[i][j] == 0)) {
			return 2;
		
		} else if ((j == lastCol + 2 && i == lastRow - 2) && pieces[lastRow - 1][lastCol + 1] == 2 && pieces[i][j] == 0) {
			return 3;	
			
		} else {
			JOptionPane.showMessageDialog(null, "El movimiento no es valido", "Atención", JOptionPane.INFORMATION_MESSAGE);
			return 0;
		}
	}
	
	//Cambio de posicion de la pieza
	private void changePosition(int nuevaRow, int nuevaCol) {
		if(isValidMove(nuevaRow,nuevaCol) == 0) {
			JOptionPane.showMessageDialog(this, "Por favor realiza un movimiento legal");
			return;
		} else if (isValidMove(nuevaRow,nuevaCol) == 1) {
			buttons[lastRow][lastCol].setIcon(null);
			pieces[lastRow][lastCol] = 0;

			buttons[nuevaRow][nuevaCol].setIcon(redPiece);
			pieces[nuevaRow][nuevaCol] = 1;
			
			lastRow = nuevaRow;
			lastCol = nuevaCol;

			updateMatrix();
			
			isTurn = false;
			
		} else if (isValidMove(nuevaRow,nuevaCol) == 2) {
			System.out.println("Comida del jugador por el lado izquierdo");
			System.out.println();
			
			algorithm.pieces--;
			buttons[lastRow][lastCol].setIcon(null);
			pieces[lastRow][lastCol] = 0;
			
			buttons[nuevaRow + 1][nuevaCol + 1].setIcon(null);
			pieces[nuevaRow + 1][nuevaCol + 1] = 0;
			
			buttons[nuevaRow][nuevaCol].setIcon(redPiece);
			pieces[nuevaRow][nuevaCol] = 1;
			
			lastRow = nuevaRow;
			lastCol = nuevaCol;

			updateMatrix();
				
			return;	
		} else if (isValidMove(nuevaRow,nuevaCol) == 3) {
			System.out.println("Comida del jugador por el lado derecho");
			System.out.println();
			
			algorithm.pieces--;
			buttons[lastRow][lastCol].setIcon(null);
			pieces[lastRow][lastCol] = 0;
			
			buttons[nuevaRow + 1][nuevaCol - 1].setIcon(null);
			pieces[nuevaRow + 1][nuevaCol - 1] = 0;

			buttons[nuevaRow][nuevaCol].setIcon(redPiece);
			pieces[nuevaRow][nuevaCol] = 1;
			
			lastRow = nuevaRow;
			lastCol = nuevaCol;

			updateMatrix();
				
			return;	
		}
			
		
		
		

	}
	
	//Actualizar matriz
	private void updateMatrix() {
		int piecesL = 0;
		if(isTurn) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(pieces[i][j] == 0) {
						buttons[i][j].setIcon(null);
						algorithm.positions[i][j] = 0;
					} else if(pieces[i][j] == 1) {
						piecesL++;
						buttons[i][j].setIcon(redPiece);
						algorithm.positions[i][j] = 2;
					} else if(pieces[i][j] == 2) {
						buttons[i][j].setIcon(whitePiece);
						algorithm.positions[i][j] = 1;
					}
				}
			}
		} else {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(algorithm.positions[i][j] == 0) {
						buttons[i][j].setIcon(null);
						pieces[i][j] = 0;
					} else if(algorithm.positions[i][j] == 1) {
						buttons[i][j].setIcon(whitePiece);
						pieces[i][j] = 2;
					} else if(algorithm.positions[i][j] == 2) {
						piecesL++;
						buttons[i][j].setIcon(redPiece);
						pieces[i][j] = 1;
					}
				}
			}
		}
		
		
		this.piecesLeft = piecesL;
		System.out.println("-----------------------------------");
		System.out.println("Impresion de matrices:");
		System.out.println();
		
		System.out.println("Matriz de piezas del tablero");
		//imprimir matriz de piezas
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(pieces[i][j] + ", ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Matriz de piezas de Algorithm");
		
		//imprimir matriz de Algorithm
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(algorithm.positions[i][j] + ", ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Piezas restantes del jugador: " + this.piecesLeft);
		System.out.println("Piezas restantes de la maquina: " + algorithm.pieces);
		System.out.println("-----------------------------------");
	}


	private class ButtonHandler implements ActionListener {
		
		private int acc = 1;
		
		public void actionPerformed(ActionEvent e) {
			if(isTurn) {
				Object source = e.getSource();
				
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						if(source == buttons[i][j]) {
							if(isPieceSelected) {
								isPieceSelected = false;
								
								if(i % 2 == 0) {
									if(j % 2 == 0) {
										buttons[lastRow][lastCol].setBackground(colorWhite);
									} else {
										buttons[lastRow][lastCol].setBackground(colorBlack);
									}
								} else {
									if(j % 2 == 0) {
										buttons[lastRow][lastCol].setBackground(colorBlack);
									} else {
										buttons[lastRow][lastCol].setBackground(colorWhite);
									}
								}
								
								if(lastRow == i && lastCol == j) {
									JOptionPane.showMessageDialog(null, "Porfavor selecciona una pieza distinta", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
									return;
									
								} else {
									System.out.println("Turno N: " + acc);
									System.out.println("Jugada realizada por jugador");
									System.out.println("Previa: " + (lastRow + 1) + ", " + (lastCol + 1));
									System.out.println("Actual: " + (i + 1) + ", " + (j + 1));
									System.out.println();
									
									try {
										changePosition(i,j);
										winner();
										if(!isTurn) {
											machinesTurn();
											winner();
											acc++;
											return;
										}
									} catch (Exception ex) {
										
									}
									return;
								}
								
							} else if (pieces[i][j] == 1) {
								buttons[i][j].setBackground(colorSelected);
								lastRow = i; 
								lastCol = j;
								isPieceSelected = true;
								return;
							} else if(pieces[i][j] == 2) {
								JOptionPane.showMessageDialog(null, "Porfavor selecciona una pieza propia", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
					}
					
				}
				JOptionPane.showMessageDialog(null, "Por favor selecciona una pieza primero", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Por favor espera tu turno", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


}
