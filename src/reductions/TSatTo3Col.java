package reductions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
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
	
	public static Graph convert (SatFNC sat, File outputFile) throws IOException {
		
		FileWriter w = new FileWriter(outputFile);
		
		ArrayList<Integer> endGadgets = new ArrayList<Integer>();
		ArrayList<Edge> edges = new ArrayList<Edge>();	
		
		int v = 3 + (2 * sat.getLitterals().size() + (6 * sat.getNbClauses()));
		int e = 3 + sat.getNbLitterals() + (2 * sat.getNbLitterals()) + (12 * sat.getNbClauses());

		w.write("p edges " + v + " " + e + "\n");
		
		int nbVertexs = 0;
		
		//connect litterals to their negation
		int maxLitteral = 0;
		int maxLitteralVariable = 0;
		for (Integer litteral : sat.getLitterals()) {
			if (litteral > maxLitteral) {
				maxLitteral = litteral;
				maxLitteralVariable = litteral;
			}
			
			//edges.add(new Edge(litteral, -litteral));
			//w.write("e " + litteral + " -" + litteral + "\n");
			//nbVertexs += 2;
		}
		
		for (Integer litteral : sat.getLitterals()) {
			int negation = litteral + maxLitteralVariable;
			edges.add(new Edge(litteral, negation));
			w.write("e " + litteral + " " + negation + "\n");
			nbVertexs += 2;
		}
		
		maxLitteral = maxLitteral * 2;
		
		//define T, F, N
		int b = maxLitteral + 1;
		int t = maxLitteral + 2;
		int f = maxLitteral + 3;
		nbVertexs += 3;
		
		//connect T, F, N
		edges.add(new Edge(b, t));
		edges.add(new Edge(t, f));
		edges.add(new Edge(f, b));
		
		w.write("e " + b + " " + t + "\n");
		w.write("e " + t + " " + f + "\n");
		w.write("e " + f + " " + b + "\n");
		
		//connect litterals to N
		for (Integer litteral : sat.getLitterals()) {			
			int negation = litteral + maxLitteralVariable;
			
			edges.add(new Edge(b, litteral));
			edges.add(new Edge(b, negation));
			
			w.write("e " + b + " " + litteral + "\n");
			w.write("e " + b + " " + negation + "\n");
		}
		
		maxLitteral += 3;
		
		for (String clause : sat.getClauses()) {
			//create gadget
			
			int v1 = maxLitteral + 1;
			int v2 = maxLitteral + 2;
			int v3 = maxLitteral + 3;
			int v4 = maxLitteral + 4;
			int v5 = maxLitteral + 5;
			int v6 = maxLitteral + 6;
			
			// 1 - 2
			edges.add(new Edge(maxLitteral + 1, maxLitteral + 2));
			w.write("e " + v1 + " " + v2 + "\n");
			// 1 - 3
			edges.add(new Edge(maxLitteral + 1, maxLitteral + 3));
			w.write("e " + v1 + " " + v3 + "\n");
			// 2 - 3
			edges.add(new Edge(maxLitteral + 2, maxLitteral + 3));
			w.write("e " + v2 + " " + v3 + "\n");
			// 3 - 4
			edges.add(new Edge(maxLitteral + 3, maxLitteral + 4));
			w.write("e " + v3 + " " + v4 + "\n");
			// 4 - 5
			edges.add(new Edge(maxLitteral + 4, maxLitteral + 5));
			w.write("e " + v4 + " " + v5 + "\n");
			//4 - 6
			edges.add(new Edge(maxLitteral + 4, maxLitteral + 6));
			w.write("e " + v4 + " " + v6 + "\n");
			//5 - 6
			edges.add(new Edge(maxLitteral + 5, maxLitteral + 6));
			w.write("e " + v5 + " " + v6 + "\n");
			
			String [] splittedClause = clause.split(" ");
			/*
			edges.add(new Edge(Integer.parseInt(splittedClause[0]), maxLitteral + 1));
			edges.add(new Edge(Integer.parseInt(splittedClause[1]), maxLitteral + 2));
			edges.add(new Edge(Integer.parseInt(splittedClause[2]), maxLitteral + 5));
			
			w.write("e " + Integer.parseInt(splittedClause[0]) + " " + (maxLitteral+1) + "\n");
			w.write("e " + Integer.parseInt(splittedClause[1]) + " " + (maxLitteral+2) + "\n");
			w.write("e " + Integer.parseInt(splittedClause[2]) + " " + (maxLitteral+5) + "\n");
			*/
			
			if (Integer.parseInt(splittedClause[0]) > 0) {
				edges.add(new Edge(Integer.parseInt(splittedClause[0]), maxLitteral + 1));
				w.write("e " + Integer.parseInt(splittedClause[0]) + " " + (maxLitteral+1) + "\n");
			} else {
				int negation = -Integer.parseInt(splittedClause[0]) + maxLitteralVariable;
				edges.add(new Edge(negation, maxLitteral + 1));
				w.write("e " + negation + " " + (maxLitteral+1) + "\n");
			}
			
			if (Integer.parseInt(splittedClause[1]) > 0) {
				edges.add(new Edge(Integer.parseInt(splittedClause[1]), maxLitteral + 2));
				w.write("e " + Integer.parseInt(splittedClause[1]) + " " + (maxLitteral+2) + "\n");
			} else {
				int negation = -Integer.parseInt(splittedClause[1]) + maxLitteralVariable;
				edges.add(new Edge(negation, maxLitteral + 2));
				w.write("e " + negation + " " + (maxLitteral+2) + "\n");
			}
			
			if (Integer.parseInt(splittedClause[2]) > 0) {
				edges.add(new Edge(Integer.parseInt(splittedClause[2]), maxLitteral + 5));
				w.write("e " + Integer.parseInt(splittedClause[2]) + " " + (maxLitteral+5) + "\n");
			} else {
				int negation = -Integer.parseInt(splittedClause[2]) + maxLitteralVariable;
				edges.add(new Edge(negation, maxLitteral + 5));
				w.write("e " + negation + " " + (maxLitteral+5) + "\n");
			}
			
			
			endGadgets.add(maxLitteral + 6);
			
			nbVertexs += 6;
			maxLitteral += 6;
		}
		
		for (Integer vertex : endGadgets) {
			edges.add(new Edge(vertex, b));
			edges.add(new Edge(vertex, f));
			
			w.write("e " + vertex + " " + b + "\n");
			w.write("e " + vertex + " " + f + "\n");
		}
		
		w.close();

		return new Graph(nbVertexs, edges.size(), edges, 3);
	}
	
	public static void main(String [] args) throws IOException {
		 SatFNC sat = SatFNC.importFromDimacs(new File("test.cnf"));
		 //SatFNC sat = SatFNC.importFromDimacs(new File("3sat.fnc"));
		 SatFNC TSat = SatTo3Sat.convert(sat);
		 @SuppressWarnings("unused")
		 Graph graph = convert(TSat, new File("testgraph.graph"));
	//	 graph.exportToDimacsSafe(new File("testgraph.graph"));
		// System.out.println(graph.getNbEdges() + " " + graph.getNbVertex());
		 
	}
}
