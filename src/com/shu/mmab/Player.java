package com.shu.mmab;

import java.util.Scanner;

public class Player {
	Game game;
	boolean isAI;
	boolean isA;
	Scanner sc;
	
	public Player(Game game, boolean isA, boolean isAI) {
		this.game = game;
		this.isAI = isAI;
		this.isA = isA;
		
		if (!isAI) {sc = new Scanner(System.in);}
	}
	
	public Move prompt() {
		return isAI ? game.gs.minimax() : game.gs.toMove(sc.nextLine());
	}
	
	public String toString() {
		String str = "";
		
		if (isAI) {
			str += "AI";
		} else {
			str += "HUMAN";
		}
		
		str += "-";
		
		if (isA) {
			str += "A";
		} else {
			str += "B";
		}
		
		return str;
	}
}
