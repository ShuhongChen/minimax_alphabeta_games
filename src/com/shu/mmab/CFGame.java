package com.shu.mmab;

import java.util.LinkedList;
import java.util.List;

public class CFGame extends Game {
	/* GAMESTATE TESTING
	cfgame.gs = new CFGameState(0, new int[][] {
			{	-1,	-1,	1,	0,	0,	0	},
			{	-1,	-1,	1,	0,	0,	0	},
			{	-1,	1,	0,	0,	0,	0	},
			{	1,	0,	0,	0,	0,	0	},
			{	0,	0,	0,	0,	0,	0	},
			{	0,	0,	0,	0,	0,	0	},
			{	0,	0,	0,	0,	0,	0	},
	});
	//*/

	public CFGame(boolean isAAI, boolean isBAI) {
		super(isAAI, isBAI);
		
		gs = new CFGameState(0);
	}
	
	public static void main(String[] args) {
		Game cfgame = new CFGame(false, true);
		cfgame.play();
	}

}


class CFMove extends Move {
	public int next;

	public CFMove(String input, boolean isA) {
		super(input, isA);
		
		this.next = Integer.parseInt(input);
	}
	
	public CFMove(int next, boolean isA) {
		super(Integer.toString(next), isA);
		
		this.next = next;
	}
	
	public String toString() {
		return (isA?"X":"O")+" TO "+next;
	}
}


class CFGameState extends GameState {
	public int col = 7;
	public int row = 6;
	public int[][] state;
	
	public CFGameState(int turn) {
		super(turn);
		
		this.state = new int[col][row];
	}
	public CFGameState(int turn, int[][] state) {
		super(turn);
		
		this.state = state;
	}

	public List<Move> moves() {
		if (moves != null) {
			return moves;
		} else {
			moves = new LinkedList<Move>();
			if (completed()) {return moves;}
			
			for (int i = 0; i < col; i++) {
				if (state[i][row-1] == 0) {
					moves.add(new CFMove(i, turn%2==0));
				}
			}
			
			return moves;
		}
	}
	
	public GameState applyMove(Move move) {
		if (completed()) {return null;}
		
		if (isLegalMove(move)) {
			int[][] nextState = new int[col][row];
			for (int i = 0; i < col; i++) {
				nextState[i] = state[i].clone();
			}
			
			int n = ((CFMove) move).next;
			for (int i = 0; i < row; i++) {
				if (nextState[n][i] == 0) {
					nextState[n][i] = turn%2==0 ? 1 : -1;
					break;
				}
			}
			
			return new CFGameState(turn+1, nextState);
		}
		
		return null;
	}
	
	public boolean completed() {
		boolean full = true;
		for (int[] i: state) {
		for (int j: i) {
			if (j == 0) {
				full = false;
				break;
			}
		}}
		if (full) {return true;}
		
		int s;
		int t;
		
		for (int i = 0; i < col-3; i++) {
		for (int j = 0; j < row; j++) {
			s = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i+k][j];
			} if (Math.abs(s) == 4) {
				return true;
			}
		}}

		for (int i = 0; i < col; i++) {
		for (int j = 0; j < row-3; j++) {
			s = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i][j+k];
			} if (Math.abs(s) == 4) {
				return true;
			}
		}}

		for (int i = 0; i < col-3; i++) {
		for (int j = 0; j < row-3; j++) {
			s = 0;
			t = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i+k][j+k];
				t += state[i+k][j+3-k];
			} if (Math.abs(s) == 4 ||
					Math.abs(t) == 4) {
				return true;
			}
		}}
		
		return false;
	}
	
	public Boolean getVictor() {
		int s;
		int t;
		
		for (int i = 0; i < col-3; i++) {
		for (int j = 0; j < row; j++) {
			s = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i+k][j];
			} if (s == 4) {
				return true;
			} else if (s == -4) {
				return false;
			}
		}}

		for (int i = 0; i < col; i++) {
		for (int j = 0; j < row-3; j++) {
			s = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i][j+k];
			} if (s == 4) {
				return true;
			} else if (s == -4) {
				return false;
			}
		}}

		for (int i = 0; i < col-3; i++) {
		for (int j = 0; j < row-3; j++) {
			s = 0;
			t = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i+k][j+k];
				t += state[i+k][j+3-k];
			} if (s == 4 || t == 4) {
				return true;
			} else if (s == -4 || t == -4) {
				return false;
			}
		}}
		
		return null;
	}
	
	public boolean isLegalMove(Move move) {
		if (state[((CFMove)move).next][5] == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public double heuristicScore(boolean isA) {
		int det = isA ? 1 : -1;
		int sum = 0;
		int s;
		int t;
		
		for (int i = 0; i < col-3; i++) {
		for (int j = 0; j < row; j++) {
			s = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i+k][j];
			} if (Math.abs(s) == 4) {
				return det*s*1000;
			} sum += Math.pow(s, 3);
		}}

		for (int i = 0; i < col; i++) {
		for (int j = 0; j < row-3; j++) {
			s = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i][j+k];
			} if (Math.abs(s) == 4) {
				return det*s*1000;
			} sum += Math.pow(s, 3);
		}}

		for (int i = 0; i < col-3; i++) {
		for (int j = 0; j < row-3; j++) {
			s = 0;
			t = 0;
			for (int k = 0; k < 4; k++) {
				s += state[i+k][j+k];
				t += state[i+k][j+3-k];
			} if (Math.abs(s) == 4) {
				return det*s*1000;
			} else if (Math.abs(t) == 4) {
				return det*t*1000;
			} sum += Math.pow(s, 3) + Math.pow(t, 3);
		}}
		
		return det*sum;
	}
	
	public Move toMove(String input) {
		return new CFMove(input, turn%2==0);
	}
	
	public String toString() {
		String str = "";
		for (int j = row-1; j >= 0; j--) {
			for (int i = 0; i < col; i++) {
				if (state[i][j] == 1) {
					str += " X ";
				} else if (state[i][j] == -1) {
					str += " O ";
				} else {
					str += " . ";
				}
			} str += "\n";
		}
		
		for (int i = 0; i < col; i++) {
			str += " "+i+" ";
		}
		
		return str;
	}
	
}
