package com.shu.mmab;

public class UTTTGame extends Game {

	public UTTTGame(boolean isAAI, boolean isBAI) {
		super(isAAI, isBAI);
		
		gs = new UTTTGameState(0);
	}

	public static void main(String[] args) {
		//System.out.println(new UTTTGameState(0));
		
		Game utttgame = new UTTTGame(false, false);
		utttgame.play();
	}

}
