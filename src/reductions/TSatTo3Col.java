package reductions;

import java.io.File;
import java.util.ArrayList;

import structures.Edge;
import structures.Graph;
import structures.SatFNC;

public class TSatTo3Col {

	public static int connectLitterals(SatFNC sat, ArrayList<Edge> edges){
		int maxLitteral = 0;
		for (Integer litteral : sat.getLitterals()) {
			if (litteral > maxLitteral) maxLitteral = litteral;
			edges.add(new Edge(litteral, -litteral));
		}
		return maxLitteral;
	}
	
	public static Graph convert (SatFNC sat) {
		
		ArrayList<Integer> endGadgets = new ArrayList<Integer>();
		ArrayList<Edge> edges = new ArrayList<Edge>();	
		int nbVertexs = 0;
		
		//connect litterals to their negation
		int maxLitteral = 0;
		for (Integer litteral : sat.getLitterals()) {
			if (litteral > maxLitteral) maxLitteral = litteral;
			edges.add(new Edge(litteral, -litteral));
			nbVertexs += 2;
		}
		
		//define T, F, N
		int b = maxLitteral + 1;
		int t = maxLitteral + 2;
		int f = maxLitteral + 3;
		nbVertexs += 3;
		
		//connect T, F, N
		edges.add(new Edge(b, t));
		edges.add(new Edge(t, f));
		edges.add(new Edge(f, b));
		
		//connect litterals to N
		for (Integer litteral : sat.getLitterals()) {
			edges.add(new Edge(b, litteral));
			edges.add(new Edge(b, -litteral));
		}
		
		maxLitteral += 3;
		
		for (String clause : sat.getClauses()) {
			//create gadget
			// 1 - 2
			edges.add(new Edge(maxLitteral + 1, maxLitteral + 2));
			// 1 - 3
			edges.add(new Edge(maxLitteral + 1, maxLitteral + 3));
			// 2 - 3
			edges.add(new Edge(maxLitteral + 2, maxLitteral + 3));
			// 3 - 4
			edges.add(new Edge(maxLitteral + 3, maxLitteral + 4));
			// 3 - 5
			edges.add(new Edge(maxLitteral + 3, maxLitteral + 5));
			// 4 - 5
			edges.add(new Edge(maxLitteral + 4, maxLitteral + 5));
			
			String [] splittedClause = clause.split(" ");
			edges.add(new Edge(Integer.parseInt(splittedClause[0]), maxLitteral + 1));
			edges.add(new Edge(Integer.parseInt(splittedClause[1]), maxLitteral + 2));
			edges.add(new Edge(Integer.parseInt(splittedClause[2]), maxLitteral + 4));
			
			endGadgets.add(maxLitteral + 5);
			
			nbVertexs += 5;
			maxLitteral += 5;
		}
		
		for (Integer vertex : endGadgets) {
			edges.add(new Edge(vertex, b));
			edges.add(new Edge(vertex, f));
		}
		
		return new Graph(nbVertexs, edges.size(), edges, 3);
	}
	
	public static void main(String [] args) {
		 SatFNC sat = SatFNC.importFromDimacs(new File("3sat.fnc"));
		 Graph g = convert(sat);
		 System.out.println(g.toString());
	}
}
