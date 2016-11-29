package com.shu.mmab;

public abstract class Game {
	public GameState gs;
	public Player playerA;
	public Player playerB;
	public int turn;
	
	public static final String header = "============================";
	
	public Game(boolean isAAI, boolean isBAI) {
		playerA = new Player(this, true, isAAI);
		playerB = new Player(this, false, isBAI);
		turn = 0;
	}
	
	public Boolean play() {
		final long startTime = System.currentTimeMillis();
		
		
		boolean first;
		Move next;
		while (!gs.completed()) {
			System.out.println(header);
			System.out.println("TURN "+turn+"\t\t"
				+(turn%2==0?playerA:playerB)+" MOVE");
			System.out.println(gs);
			
			first = true;
			do {
				if (!first) {System.out.println("INVALID INPUT");}
				next = (turn%2==0) ? playerA.prompt() : playerB.prompt();
				first = false;
			} while (!executeMove(next));
			
			System.out.println("EXECUTED: "+next+"\n");
			
			turn++;
		} Boolean victor = getVictor();
		
		final long endTime = System.currentTimeMillis();
		
		
		System.out.println(header+"\n"+header);
		System.out.println("TURN "+turn);
		System.out.println(gs);
		if (victor == null) {
			System.out.println("TIE GAME");
		} else if (victor) {
			System.out.println(playerA+" VICTORY");
		} else {
			System.out.println(playerB+" VICTORY");
		}
		System.out.println("TOTAL TIME: "+(endTime-startTime)+"ms");
		System.out.println(header+"\n"+header);
		
		return victor;
	}
	
	public boolean executeMove(Move move) {
		GameState next = gs.applyMove(move);
		if (next != null) {
			gs = next;
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean getVictor() {
		return gs.getVictor();
	}
}
