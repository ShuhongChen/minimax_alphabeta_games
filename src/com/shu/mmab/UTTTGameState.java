package com.shu.mmab;

import java.util.LinkedList;
import java.util.List;

public class UTTTGameState extends GameState {
	public int previousMove;
	public int[][] state;
	public final int[][] victory = {
			{0,1,2},{3,4,5},{6,7,8},
			{0,3,6},{1,4,7},{2,5,8},
			{0,4,8},{2,4,6}
	};
	
	public UTTTGameState(int turn) {
		super(turn);
		
		this.previousMove = -1;
		this.state = new int[9][9];
	}
	public UTTTGameState(int turn, int previousMove, int[][] state) {
		super(turn);
		
		this.previousMove = previousMove;
		this.state = state;
	}

	public List<Move> moves() {
		if (moves != null) {
			return moves;
		} else {
			moves = new LinkedList<Move>();
			if (completed()) {return moves;}
			
			if (previousMove == -1) {
				for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (state[i][j] == 0) {
						moves.add(new UTTTMove(new int[]{i,j}, turn%2==0));
					}
				}}
			} else {
				for (int i = 0; i < 9; i++) {
					if (state[previousMove][i] == 0) {
						moves.add(new UTTTMove(new int[]{previousMove, i}, turn%2==0));
					}
				}
				
				if (moves.isEmpty()) {
					for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (state[i][j] == 0) {
							moves.add(new UTTTMove(new int[]{i,j}, turn%2==0));
						}
					}}
				}
			}
			
			return moves;
		}
	}

	public GameState applyMove(Move move) {
		if (completed()) {return null;}
		
		if (isLegalMove(move)) {
			int[][] nextState = new int[9][9];
			for (int i = 0; i < 9; i++) {
				nextState[i] = state[i].clone();
			}
			
			UTTTMove m = (UTTTMove) move;
			nextState[m.next[0]][m.next[1]] = turn%2==0 ? 1 : -1;
			return new UTTTGameState(turn+1, m.next[1], nextState);
		}
		
		return null;
	}

	public boolean completed() {
		int[] awins = new int[9];
		int[] bwins = new int[9];
		
		int sum;
		for (int i = 0; i < 9; i++) {
			for (int[] v: victory) {
				sum = state[i][v[0]]
						+ state[i][v[1]]
						+ state[i][v[2]];
				if (sum == 3) {
					awins[i] = 1;
				} else if (sum == -3) {
					bwins[i] = 1;
				}
			}
		}
		for (int[] v: victory) {
			if (awins[v[0]]+awins[v[1]]+awins[v[2]] == 3 ||
					bwins[v[0]]+bwins[v[1]]+bwins[v[2]] == 3) {
				return true;
			}
		}
		
		for (int[] i: state) {
		for (int j: i) {
			if (j == 0) {
				return false;
			}
		}} return true;
	}

	public Boolean getVictor() {
		int[] awins = new int[9];
		int[] bwins = new int[9];
		
		int sum;
		for (int i = 0; i < 9; i++) {
			for (int[] v: victory) {
				sum = state[i][v[0]]
						+ state[i][v[1]]
						+ state[i][v[2]];
				if (sum == 3) {
					awins[i] = 1;
				} else if (sum == -3) {
					bwins[i] = 1;
				}
			}
		}
		for (int[] v: victory) {
			if (awins[v[0]]+awins[v[1]]+awins[v[2]] == 3) {
				return true;
			} else if (bwins[v[0]]+bwins[v[1]]+bwins[v[2]] == 3) {
				return false;
			}
		}
		
		return null;
	}

	public boolean isLegalMove(Move move) {
		UTTTMove m = (UTTTMove) move;
		
		if ((m.next[0]==previousMove || previousMove==-1) &&
				state[m.next[0]][m.next[1]] == 0) {
			return true;
		}
		
		return false;
	}

	// UNFINISHED
	public double heuristicScore(boolean isA) {
		int[] awins = new int[9];
		int[] bwins = new int[9];
		
		int temp;
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			for (int[] v: victory) {
				temp = state[i][v[0]]
						+ state[i][v[1]]
						+ state[i][v[2]];
				if (temp == 3) {
					awins[i] = 1;
				} else if (temp == -3) {
					bwins[i] = 1;
				}
			}
		}
		for (int[] v: victory) {
			if (awins[v[0]]+awins[v[1]]+awins[v[2]] == 3) {
				return isA ? 1000 : -1000;
			} else if (bwins[v[0]]+bwins[v[1]]+bwins[v[2]] == 3) {
				return isA ? -1000 : 1000;
			}
		}
		
		return sum;
	}

	public Move toMove(String input) {
		return new UTTTMove(input, turn%2==0);
	}
	
	public String toString() {
		String str = "";
		
		int box;
		int pos;
		for (int i = 0; i < 9; i++) {
			if (i%3==0 && i!=0) {
				for (int k = 0; k < 11; k++) {
					str += " # ";
				} str += "\n";
			}
			for (int j = 0; j < 9; j++) {
				if (j%3==0 && j!=0) {
					str += " # ";
				}
				
				box = 3*(i/3) + j/3;
				pos = 3*(i%3) + j%3;
				
				if (state[box][pos] == 1) {
					str += " X ";
				} else if (state[box][pos] == -1) {
					str += " O ";
				} else {
					str += " . ";
				}
			} str += "\n";
		}
		
		return str;
	}

}
