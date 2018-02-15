package main;

import java.io.File;

import reductions.SatTo3Sat;
import reductions.TColToSat;
import reductions.TSatTo3Col;
import structures.Graph;
import structures.SatFNC;

public class Main {

	public static void displayUsage() {
		System.out.println("Invalid command.");
		System.out.println("USAGE: java -jar Converter $PIn $POut inputFile outputFile");
		System.out.println("$P = { SAT | 3SAT | 3COL | CSP | CLIQUE }");
	}
	
	public static void main (String [] args) {
		if (args.length == 4) {
			String inputFormat = args[0];
			String outputFormat = args[1];
			File inputFile = new File(args[2]);
			File outputFile = new File (args[3]);
			
			if (inputFormat.equals("SAT") && outputFormat.equals("3SAT")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				SatFNC Tsat = SatTo3Sat.convert(sat);
				Tsat.exportToDimacs(outputFile);
				System.out.println("polynomial reduction written at " + outputFile.getAbsolutePath());
			} else if (inputFormat.equals("3COL") && outputFormat.equals("SAT")) {
				Graph graph = Graph.importFromDimacs(inputFile);
				SatFNC sat  = TColToSat.convert(graph);
				sat.exportToDimacs(outputFile);
				System.out.println("polynomial reduction written at " + outputFile.getAbsolutePath());
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("3COL")) {
				SatFNC Tsat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatTo3Col.convert(Tsat);
				graph.exportToDimacs(outputFile);
				System.out.println("polynomial reduction written at " + outputFile.getAbsolutePath());
			} else {
				displayUsage();
			}
			
		} else {
			displayUsage();
		}
	}
}
