package reductions;

import java.io.File;
import java.util.ArrayList;

import structures.Edge;
import structures.Graph;
import structures.SatFNC;

public class TSatToVertexCover {
	
	public static int getId(ArrayList<String> vars, String var) {
		int i = 1;
		for (String string : vars) {
			if (var.equals(string)) return i;
			i++;
		}
		return -1;
	}
	
	public static void loadLitterals(ArrayList<String> vars, SatFNC sat) {
		for (Integer litteral : sat.getLitterals()) {
			vars.add(Integer.toString(litteral));
			vars.add(Integer.toString(-litteral));
		}
	}
	
	public static void connectLitteralsEachOthers(ArrayList<String> vars, ArrayList<Edge> edges, int nbVertex) {
		for (int i = 0 ; i < vars.size() ; i += 2) {
			String litteral1 = vars.get(i);
			String litteral2 = vars.get(i+1);
			edges.add(new Edge(getId(vars, litteral1), getId(vars, litteral2)));
			//nbVertex += 2;
		}
	}
	
	public static void createAndConnectClauses(SatFNC sat, ArrayList<String> vars, ArrayList<Edge> edges, int nbVertex) {
		
		for (String clause : sat.getClauses()) {
			//Ids of 3 new vertexs
			int idVertex1 = nbVertex;
			int idVertex2 = nbVertex + 1;
			int idVertex3 = nbVertex + 2;
			nbVertex += 3;
			
			edges.add(new Edge(idVertex1, idVertex2));
			edges.add(new Edge(idVertex2, idVertex3));
			edges.add(new Edge(idVertex3, idVertex1));

			String [] splittedClause = clause.split(" ");
			
			//connect clause's vertexs to base
			edges.add(new Edge(idVertex1, getId(vars, splittedClause[0])));
			edges.add(new Edge(idVertex2, getId(vars, splittedClause[1])));
			edges.add(new Edge(idVertex3, getId(vars, splittedClause[2])));
		}
		
		
	}
	
	public static Graph convert(SatFNC sat) {
		
		int nbVertex = 0;
		int k = sat.getNbLitterals() + ( 2 * sat.getNbClauses());
		ArrayList<String> vars = new ArrayList<String>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		loadLitterals(vars, sat);
		nbVertex = vars.size() + 1;
		connectLitteralsEachOthers(vars, edges, nbVertex);
		createAndConnectClauses(sat, vars, edges, nbVertex);
		
		nbVertex += (3 * sat.getNbClauses()) - 1;
		
		return new Graph(nbVertex, edges.size(), edges, k);
	}
	
	public static void main(String [] args) {
		SatFNC sat = SatFNC.importFromDimacs(new File("3sat.fnc"));
		Graph graph = convert(sat);
		System.out.println(graph.toString());
	}
}
