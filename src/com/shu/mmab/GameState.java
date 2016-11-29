package com.shu.mmab;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class GameState {
	public int turn;
	public List<Move> moves;
	
	public static final boolean useAB = true;
	public static final boolean useHeurSort = true;
	public static final int depth = 8;
	
	public GameState(int turn) {
		this.turn = turn;
		this.moves = null;
	}
	
	public abstract List<Move> moves();
	public abstract GameState applyMove(Move move);
	public abstract boolean completed();
	public abstract Boolean getVictor();
	public abstract boolean isLegalMove(Move move);
	public abstract double heuristicScore(boolean isA);
	public abstract Move toMove(String input);
	
	public Move minimax() {
		final long startTime = System.currentTimeMillis();
		
		if (depth == 0 || completed()) {
			return null;
		}
		
		System.out.println((turn%2==0 ? "X":"O")+" AI CONSIDERING: ");
		
		double[] v = new double[moves().size()];
		if (useAB && useHeurSort) {heuristicSort();}
		double a = Integer.MIN_VALUE;
		double b = Integer.MAX_VALUE;
		for (int m = 0; m < moves.size(); m++) {
			System.out.print("\t"+moves.get(m)+" : ");
			v[m] = applyMove(moves.get(m)).alphabeta(depth-1, a, b, false);
			a = Math.max(a, v[m]);
			System.out.printf("%.2f", v[m]);
			System.out.println();
		}
		
		final long endTime = System.currentTimeMillis();
		System.out.println("\tTIME: "+(endTime-startTime)+"ms");
		
		System.out.println((turn%2==0 ? "X":"O")+" AI CHOICES: ");
		List<Move> choices = new LinkedList<Move>();
		for (int m = 0; m < v.length; m++) {
			if (v[m] == a) {
				System.out.println("\t"+moves.get(m));
				choices.add(moves.get(m));
			}
		}
		
		return choices.get((new Random().nextInt(choices.size())));
	}
	
	public double alphabeta(int depth, double a, double b, boolean isMax) {
		if (depth == 0 || completed()) {
			return heuristicScore((turn%2==0) == isMax);
		}
		
		double v;
		moves = moves();
		if (useAB && useHeurSort) {heuristicSort();}
		if (isMax) {
			v = Integer.MIN_VALUE;
			for (Move m: moves) {
				v = Math.max(v, applyMove(m).alphabeta(depth-1, a, b, false));
				if (!useAB) {continue;}
				
				a = Math.max(a, v);
				if (b < a) {break;}
			}
		} else {
			v = Integer.MAX_VALUE;
			for (Move m: moves) {
				v = Math.min(v, applyMove(m).alphabeta(depth-1, a, b, true));
				if (!useAB) {continue;}
				
				b = Math.min(b, v);
				if (b < a) {break;}
			}
		}
		
		return v;
	}
	
	public final Comparator<Move> heurComp =
		(Move a, Move b) -> {return
			((Double) applyMove(b).heuristicScore(turn%2==0)).compareTo(
			(Double) applyMove(a).heuristicScore(turn%2==0));
		};
	public void heuristicSort() {
		Collections.sort(moves, heurComp);
	}
}
