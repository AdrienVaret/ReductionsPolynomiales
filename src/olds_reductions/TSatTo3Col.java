package olds_reductions;

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
		System.out.println();
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
	
	public static void connectLitteralsEachOthers(ArrayList<String> vars, ArrayList<Edge> edges) {
		for (int i = 3 ; i < vars.size() ; i += 2) {
			String litteral1 = vars.get(i);
			String litteral2 = vars.get(i+1);
			edges.add(new Edge(getId(vars, litteral1), getId(vars, litteral2)));
		}
	}
	
	public static void addOrGadget(String clause, ArrayList<String> vars, ArrayList<Edge> edges, int nbGadgets) {
		String [] splittedClause = clause.split(" ");
		
		//create vertexs
		for (int i = 1 ; i <= 6 ; i++) {
			vars.add(new String("G" + nbGadgets + "_" + i));
		}
		
		//connect litterals to gadget's inputs
		edges.add(new Edge(getId(vars, splittedClause[0]), getId(vars, "G" + nbGadgets + "_" + 1)));
		edges.add(new Edge(getId(vars, splittedClause[1]), getId(vars, "G" + nbGadgets + "_" + 2)));
		edges.add(new Edge(getId(vars, splittedClause[2]), getId(vars, "G" + nbGadgets + "_" + 5)));
		
		//connect gadget's vertexs each others
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 1), getId(vars, "G" + nbGadgets + "_" + 2)));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 1), getId(vars, "G" + nbGadgets + "_" + 3)));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 2), getId(vars, "G" + nbGadgets + "_" + 3)));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 3), getId(vars, "G" + nbGadgets + "_" + 4)));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 4), getId(vars, "G" + nbGadgets + "_" + 5)));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 4), getId(vars, "G" + nbGadgets + "_" + 6)));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 5), getId(vars, "G" + nbGadgets + "_" + 6)));
		
		//connect gadget's output to base
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 6), getId(vars, "B")));
		edges.add(new Edge(getId(vars, "G" + nbGadgets + "_" + 6), getId(vars, "F")));
	}
	
	public static Graph convert (SatFNC sat) {
		ArrayList<String>  vars    = new ArrayList<String>();
		loadBase(vars);
		loadLitterals(vars, sat);
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		connectBase(vars, edges);
		connectLitteralsToBase(vars, edges);
		connectLitteralsEachOthers(vars, edges);
		
		int nbGadgets = 1;
		
		for (String clause : sat.getClauses()) {
			addOrGadget(clause, vars, edges, nbGadgets);
			nbGadgets += 1;
		}
		
		return new Graph(vars.size(), edges.size(), edges);
	}
	
	public static void main(String [] args) {
	    SatFNC sat = SatFNC.importFromDimacs(new File("3sat.fnc"));
	    Graph g = convert(sat);
	    System.out.println(g.toString());
	  }
}
