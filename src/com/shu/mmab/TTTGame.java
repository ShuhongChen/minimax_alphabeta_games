package com.shu.mmab;

import java.util.LinkedList;
import java.util.List;

public class TTTGame extends Game {

	public TTTGame(boolean isAAI, boolean isBAI) {
		super(isAAI, isBAI);
		
		gs = new TTTGameState(0);
	}

	public static void main(String[] args) {
		Game tttgame = new TTTGame(true, true);
		tttgame.play();
	}

}


class TTTMove extends Move {
	public int next;
	
	public TTTMove(String input, boolean isA) {
		super(input, isA);
		
		this.next = Integer.parseInt(input);
	}
	
	public TTTMove(int next, boolean isA) {
		super(Integer.toString(next), isA);
		
		this.next = next;
	}
	
	public String toString() {
		return (isA?"X":"O")+" TO "+next;
	}
}


class TTTGameState extends GameState {
	public int[] state;
	public final int[][] victory = {
			{0,1,2},{3,4,5},{6,7,8},
			{0,3,6},{1,4,7},{2,5,8},
			{0,4,8},{2,4,6}
	};

	public TTTGameState(int turn) {
		super(turn);
		
		state = new int[9];
	}
	public TTTGameState(int turn, int[] state) {
		super(turn);
		
		this.state = state;
	}
	
	public List<Move> moves() {
		if (moves != null) {
			return moves;
		} else {
			moves = new LinkedList<Move>();
			if (completed()) {return moves;}
			
			for (int i = 0; i < 9; i++) {
				if (state[i] == 0) {
					moves.add(new TTTMove(i, turn%2==0));
				}
			}
			
			return moves;
		}
	}

	public GameState applyMove(Move move) {
		if (completed()) {return null;}
		
		if (isLegalMove(move)) {
			int[] nextState = state.clone();
			nextState[((TTTMove) move).next] = turn%2==0 ? 1 : -1;
			return new TTTGameState(turn+1, nextState);
		}
		
		return null;
	}

	public boolean completed() {
		boolean full = true;
		for (int i: state) {
			if (i == 0) {
				full = false;
				break;
			}
		}
		if (full) {return true;}
		
		for (int[] v: victory) {
			if (Math.abs(state[v[0]]
					+state[v[1]]
					+state[v[2]]) == 3) {
				return true;
			}
		}
		
		return false;
	}

	public Boolean getVictor() {
		int sum;
		for (int[] v: victory) {
			sum = state[v[0]]
				+state[v[1]]
				+state[v[2]];
			if (sum == 3) {
				return true;
			} else if (sum == -3) {
				return false;
			}
		}
		
		return null;
	}

	public boolean isLegalMove(Move move) {
		return state[((TTTMove) move).next] == 0;
	}

	public double heuristicScore(boolean isA) {
		Boolean victor = getVictor();
		if (victor == null) {
			return 0;
		} else {
			return (isA==victor) ? 1 : -1;
		}
	}
	
	public Move toMove(String input) {
		return new TTTMove(input, turn%2==0);
	}
	
	public String toString() {
		String str = "";
		for (int i = 0; i < 9; i++) {
			if (state[i] == 1) {
				str += " X ";
			} else if (state[i] == -1) {
				str += " O ";
			} else {
				str += " . ";
			}
			
			if (i == 2 || i == 5) {
				str += "\n";
			}
		}
		
		return str;
	}
	
}
