package reductions;

import java.util.ArrayList;
import java.util.Collections;

import structures.SatFNC;

public class ShuffleSat {

	public static SatFNC shuffle (SatFNC sat) {
		
		ArrayList<String> clauses = (ArrayList<String>) sat.getClauses().clone();
		Collections.shuffle(clauses);
		
		return new SatFNC(sat.getNbLitterals(), sat.getNbClauses(), clauses);
	}
	
	public static void main (String [] args) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		l.add(5);
		
		ArrayList<Integer> l2 = (ArrayList<Integer>) l.clone();
		
		Collections.shuffle(l2);
		
		for (Integer i : l) System.out.print(i + " ");
		System.out.println("");
		for (Integer i : l2) System.out.print(i + " ");
	}
}
