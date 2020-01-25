/*
 * Autores: A01633683 Carlos Ernesto Lopez Solano
 * 			A01633872 Alan Ricardo Gonzalez Aguilar
 * Nombre de la clase (programa): Algorithm
 * Fecha: 21 de Noviembre de 2018
 * Comentarios u observaciones: perteneciente al proyecto final "Damas Inglesas"
 * 								Aqui se encuentra el algoritmo de Backtracking y
 * 								el codigo necesario para la jugabilidad del CPU
 */

import java.util.concurrent.ThreadLocalRandom;

public class Algorithm {
	public int[][] positions;
	public int[][] past;
	public int pieces;
	
	public Algorithm(){
		//Change constructor with if
		this.positions = new int[8][8];
		this.pieces = 12;
		this.positions[0] = new int[] {0,1,0,1,0,1,0,1};
		this.positions[1] = new int[] {1,0,1,0,1,0,1,0};
		this.positions[2] = new int[] {0,1,0,1,0,1,0,1};
		this.positions[3] = new int[] {0,0,0,0,0,0,0,0};
		this.positions[4] = new int[] {0,0,0,0,0,0,0,0};
		this.positions[5] = new int[] {2,0,2,0,2,0,2,0};
		this.positions[6] = new int[] {0,2,0,2,0,2,0,2};
		this.positions[7] = new int[] {2,0,2,0,2,0,2,0};
		//Se movera una pieza aleatoria que se pueda llegar a mover
	}
	
	public void move() {
		int grade = 0;
		int pos[] = {0,0};
		int move[] = {0,0};
		int kills;
		for(int i = 0;i<this.pieces;i++) {
			if(this.positions[pos[0]][pos[1]]!=1) {
				while(pos[0]<8) {
					while(pos[1]<8) {
						if(this.positions[pos[0]][pos[1]]!=1) {
							pos[1]++;
						} else {
							break;
						}
					}
					if(pos[1]==8) {
						pos[1]=0;
						pos[0]++;
					}
					if(this.positions[pos[0]][pos[1]]==1) {
						break;
					}
				}
			}
			kills = move(pos);
			if(kills>grade) {
				grade = kills;
				move[0] = pos[0];
				move[1] = pos[1];
			}
			if(pos[1]<7) {
				pos[1]++;
			} else {
				pos[0]++;
				pos[1]=0;
			}
		}
		if(grade==0) {
			try {
				while(grade==0) {
					int randomNum = ThreadLocalRandom.current().nextInt(1, this.pieces+1);
					pos[0]=0;
					pos[1]=0;
					for(int i = 0;i<randomNum;i++) {
						if(this.positions[pos[0]][pos[1]]!=1) {
							while(pos[0]<8) {
								while(pos[1]<8) {
									if(this.positions[pos[0]][pos[1]]!=1) {
										pos[1]++;
									} else {
										break;
									}
								}
								if(pos[1]==8) {
									pos[1]=0;
									pos[0]++;
								}
								if(this.positions[pos[0]][pos[1]]==1) {
									break;
								}
							}
						}
						if(pos[1]<7) {
							pos[1]++;
						} else {
							pos[0]++;
							pos[1]=0;
						}
					}
					int y = pos[0];
					int x = pos[1]-1;
					if(y<7 && x<7) {
						if(Math.random() < 0.5) {
							if(y<7 && x<7) {
								if(this.positions[y+1][x+1] == 0) {
									this.positions[y+1][x+1] = 1;
									this.positions[y][x] = 0;
									grade--;
								} else {
									if(this.positions[y+1][x-1] == 0) {
										this.positions[y+1][x-1] = 1;
										this.positions[y][x] = 0;
										grade--;
									}
								}
							}
						} else {
							if(y<7 && x<7) {
								if(this.positions[y+1][x-1] == 0) {
									this.positions[y+1][x-1] = 1;
									this.positions[y][x] = 0;
									grade--;
								} else {
									if(this.positions[y+1][x+1] == 0) {
										this.positions[y+1][x+1] = 1;
										this.positions[y][x] = 0;
										
									}
								}
							}
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
		} else {
			int y = move[0];
			int x = move[1];
			while(grade>0) {
				try {
					if(this.positions[y+1][x+1]==2) {
						this.positions[y][x]=0;
						this.positions[y+1][x+1]=0;
						this.positions[y+2][x+2]=1;
						x+=2;
						y+=2;
					} else {
						this.positions[y][x]=0;
						this.positions[y+1][x-1]=0;
						this.positions[y+2][x-2]=1;
						x-=2;
						y+=2;
					}
					grade--;
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				//System.out.print(this.positions[i][j]+",");
			}
			//System.out.println();
		}
		//pos[0] son y y pos[1] son x
	}
	
	public int move(int[] pos) {
		int nR = 0;
		int nL = 0;
		int y = pos[0];
		int x = pos[1];
		try {
			if((this.positions[y+1][x+1])!=1) {
				if((this.positions[y+2][x+2])==0) {
					if((this.positions[y+1][x+1])==2) {
						nR++;
					}
				}
			}
			if((this.positions[y+1][x-1])!=1) {
				if((this.positions[y+2][x-2])==0) {
					if((this.positions[y+1][x-1])==2) {
						nL++;
					}
				}
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			try {
				if((this.positions[y+1][x-1])!=1) {
					if((this.positions[y+2][x-2])==0) {
						if((this.positions[y+1][x-1])==2) {
							nL++;
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException r) {
				return 0;
			}
		}
		
		
		if(nR>nL) {
			y+=2;
			x+=2;
			int[] posf = {y,x};
			return nR + move(posf);
		} else if(nR<nL) {
			y+=2;
			x-=2;
			int[] posf = {y,x};
			return nL + move(posf);
		} else {
			if(nR==nL && nR==0) {
				return 0;
			} else {
				if(Math.random() < 0.5) {
					y+=2;
					x+=2;
					int[] posf = {y,x};
					return nR + move(posf);
				} else {
					y+=2;
					x-=2;
					int[] posf = {y,x};
					return nL + move(posf);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		/*
		Algorithm prueba = new Algorithm();
		System.out.println(prueba.pieces);
		prueba.move();
		System.out.println(prueba.pieces);
		*/
	}
}
