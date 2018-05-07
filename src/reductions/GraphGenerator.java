package reductions;

import java.io.File;

import structures.Graph;

public class GraphGenerator {

	public static void main (String [] args) {
		System.out.println("# Graph generator ");
		
		if (args.length != 4 && args.length != 3) {
			System.out.println("# Invalid input");
			System.out.println("# USAGE : java -jar (-rand|-comp) graphgen.jar nbvertexs nbedges outputfile");
		} else {			
			if (args[0].equals("-rand")) {
				
				int nbVertexs = Integer.parseInt(args[1]);
				int nbEdges   = Integer.parseInt(args[2]);
				
				if (nbEdges <= (nbVertexs * (nbVertexs-1))/2) {
					File outputFile = new File(args[3]);
					Graph graph = Graph.generate(nbVertexs, nbEdges);
					graph.exportToDimacs(outputFile);
					System.out.println("# random graph (" + nbVertexs + ", " + nbEdges + ") -> " + outputFile.getAbsolutePath());
				} else {
					System.out.println("# Too many edges");
				}
			}
			
			else if (args[0].equals("-comp")) {
				int nbVertexs = Integer.parseInt(args[1]);
				File outputFile = new File(args[2]);
				Graph graph = Graph.generateCompleteGraph(nbVertexs);
				graph.exportToDimacs(outputFile);
				System.out.println("# complete graph (" + nbVertexs + ") -> " + outputFile.getAbsolutePath());
			}
		}
	}
}
