package com.shu.mmab;

public class UTTTMove extends Move {
	public int[] next;
	
	public UTTTMove(String input, boolean isA) {
		super(input, isA);
		
		double i = Double.parseDouble(input);
		this.next = new int[] {(int) i, (int) ((i-((int)i))*11)};
	}
	
	public UTTTMove(int[] next, boolean isA) {
		super(Double.toString(next[0]+next[1]/10), isA);
		
		this.next = next;
	}
	
	public String toString() {
		return (isA?"X":"O")+" TO "+input;
	}
}
