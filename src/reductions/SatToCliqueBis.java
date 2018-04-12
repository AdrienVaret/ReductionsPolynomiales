package reductions;

import java.io.File;
import java.util.ArrayList;

import structures.Edge;
import structures.Graph;
import structures.SatFNC;
import structures.Vertex;

public class SatToCliqueBis {

	public static ArrayList<Vertex> createVertexs(SatFNC sat){
		
		ArrayList<Vertex> vertexs = new ArrayList<Vertex>();
		int idClause = 1;
		int idVertex = 1;
		for (String clause : sat.getClauses()) {
			String [] splittedClause = clause.split(" ");
			for (int i = 0 ; i < splittedClause.length - 1 ; i++) {
				vertexs.add(new Vertex(Integer.parseInt(splittedClause[i]), idClause, idVertex));
				idVertex += 1;
			}
			idClause += 1;
		}
		return vertexs; 
	}
	
	public static ArrayList<Edge> connectVertexs(SatFNC sat, ArrayList<Vertex> vertexs){
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		for (Vertex  i : vertexs) {
			for (Vertex j : vertexs) {
				if ((i.getId() < j.getId()) && 
					(i.getIdClause() != j.getIdClause()) && 
					(i.getValue() != -j.getValue())) {
					edges.add(new Edge(i.getId(), j.getId()));
				}
			}
		}
		
		return edges;
	}
	
	public static Graph convert(SatFNC sat) {
		ArrayList<Vertex> vertexs = createVertexs(sat);
		ArrayList<Edge> edges = connectVertexs(sat, vertexs);
		
		return new Graph(vertexs.size(), edges.size(), edges, sat.getNbClauses());
	}
	
	public static void main(String [] args) {
		SatFNC sat = SatFNC.importFromDimacs(new File("test.cnf"));
		Graph graph = convert(sat);
		System.out.println(graph.toString());
	}
}
