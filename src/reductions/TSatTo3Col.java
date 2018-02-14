package reductions;

import java.io.File;
import java.util.ArrayList;

import structures.Edge;
import structures.Graph;
import structures.SatFNC;

public class TSatTo3Col {

	
	public static void loadBase(ArrayList<String> vars) {
		vars.add("T"); //1
		vars.add("F"); //2
		vars.add("B"); //3
	}
	
	public static void loadLitterals(ArrayList<String> vars, SatFNC sat) {
		for (Integer litteral : sat.getLitterals()) {
			vars.add(Integer.toString(litteral));
			vars.add(Integer.toString(-litteral));
		}
	}
	
	public static int getId(ArrayList<String> vars, String var) {
		int i = 1;
		for (String string : vars) {
			if (var.equals(string)) return i;
			i++;
		}
		return -1;
	}
	
	public static void connectBase(ArrayList<String> vars, ArrayList<Edge> edges) {
		edges.add(new Edge(getId(vars, "T"), getId(vars, "F")));
		edges.add(new Edge(getId(vars, "F"), getId(vars, "B")));
		edges.add(new Edge(getId(vars, "B"), getId(vars, "T")));
	}
	
	public static void connectLitteralsToBase(ArrayList<String> vars, ArrayList<Edge> edges) {
		for (int i = 3 ; i < vars.size() ; i++) {
			String litteral = vars.get(i);
			edges.add(new Edge(getId(vars, litteral), getId(vars, "B")));
		}
	}
	
	public static Graph convert (SatFNC sat) {
		ArrayList<String>  vars    = new ArrayList<String>();
		loadBase(vars);
		loadLitterals(vars, sat);
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		connectBase(vars, edges);
		
		connectLitteralsToBase(vars, edges);
		
		return null;
	}
	
	public static void main(String [] args) {
		SatFNC sat = SatFNC.importFromDimacs(new File("formule.fnc"));
		System.out.println(sat.toString());
		sat.exportToDimacs(new File("export.fnc"));
		SatFNC Tsat = SatTo3Sat.convert(sat);
		System.out.print(Tsat.toString());
		Tsat.exportToDimacs(new File("export.fnc"));
		convert(Tsat);
	}
}
