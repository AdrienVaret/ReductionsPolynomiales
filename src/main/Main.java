package main;

import java.io.File;
import java.util.ArrayList;

import reductions.SatTo3Sat;
import reductions.TColToSat;
import reductions.TSatTo3Col;
import structures.Graph;
import structures.SatFNC;
import structures.Edge;

public class Main {

	public static void displayUsage() {
		System.out.println("# Invalid command.");
		System.out.println("# USAGE: java -jar Converter $PIn $POut inputFile outputFile");
		System.out.println("# $P = { SAT | 3SAT | 3COL | CSP | CLIQUE }");
		System.out.println("###");
	}
	 
	public static void displayStart() {
		System.out.println("###");
		System.out.println("# Polynomials reduction related to SAT");
		System.out.println("# @authors : VARET Adrien\n"
				         + "#            ENTAKLI Romain\n"
				         + "#            D'ARRIGO Vincent");
		System.out.println("###");	
	}
	
	public static void test3ColToXCSP3(){
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge(0, 1)); edges.add(new Edge(0, 4)); edges.add(new Edge(1, 2));
		edges.add(new Edge(2, 3)); edges.add(new Edge(3, 4)); edges.add(new Edge(0, 5));
		edges.add(new Edge(1, 6)); edges.add(new Edge(2, 7)); edges.add(new Edge(3, 8));
		edges.add(new Edge(4, 9)); edges.add(new Edge(5, 7)); edges.add(new Edge(7, 9));
		edges.add(new Edge(9, 6)); edges.add(new Edge(6, 8)); edges.add(new Edge(8, 5));
		
		Graph g = new Graph(10, edges.size(), edges);
		System.out.println(g.TreeColToXCSP3());
	}
	
	public static void main (String [] args) {

		displayStart();
		
		if (args.length == 4) {
			String inputFormat = args[0];
			String outputFormat = args[1];
			File inputFile = new File(args[2]);
			File outputFile = new File (args[3]);
			
			if (inputFormat.equals("SAT") && outputFormat.equals("3SAT")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				SatFNC Tsat = SatTo3Sat.convert(sat);
				Tsat.exportToDimacs(outputFile);
				System.out.println("# SAT to 3-SAT réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
				
			} else if (inputFormat.equals("3COL") && outputFormat.equals("SAT")) {
				Graph graph = Graph.importFromDimacs(inputFile);
				SatFNC sat  = TColToSat.convert(graph);
				sat.exportToDimacs(outputFile);
				System.out.println("# 3-COL to SAT réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
				
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("3COL")) {
				SatFNC Tsat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatTo3Col.convert(Tsat);
				graph.exportToDimacs(outputFile);
				System.out.println("# 3-SAT to 3-COL réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
			} else {
				displayUsage();
			}
			
			System.out.println("###");
			
		} else {
			displayUsage();
		}
		
	}
	
}
