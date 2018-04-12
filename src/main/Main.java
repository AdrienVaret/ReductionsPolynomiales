package main;

import java.io.File;

import reductions.CSPToSat;
import reductions.SatTo3Sat;
import reductions.SatToClique;
import reductions.SatToCliqueBis;
import reductions.SatToCliqueThread;
import reductions.TColToSat;
import reductions.TSatTo3Col;
import reductions.TSatToCliqueThread;
import reductions.TSatToVertexCover;
import structures.BinCSP;
import structures.Graph;
import structures.SatFNC;

public class Main {

	public static void displayUsage() {
		System.out.println("# USAGE: java -jar Converter $PIn $POut inputFile outputFile");
		System.out.println("# $P(In|Out) = { SAT | 3SAT | 3COL | CSP | CLIQUE | VERTEX_COVER }");
		System.out.println("###");
		System.out.println("# Reductions possibles : ");
		System.out.println("# * SAT TO 3-SAT");
		System.out.println("# * 3-COL TO SAT");
		System.out.println("# * 3-SAT TO 3-COL");
		System.out.println("# * 3-SAT TO VERTEX_COVER");
		System.out.println("# * SAT TO 3-SAT");
		System.out.println("# * 3-COL TO CSP");
		System.out.println("# * SAT TO 3-COL");
		System.out.println("# * SAT TO VERTEX_COVER");
		System.out.println("# * CSP TO SAT [DIRECT_ENCODING]");
		System.out.println("# * CSP TO SAT [SUPPORT_ENCODING]");
		System.out.println("###");
	}
	 
	public static void displayStart() {
		System.out.println("###");
		System.out.println("# Polynomials reduction related to SAT");
		System.out.println("# @authors : VARET Adrien\n"
				         + "#            ENTAKLI Romain\n"
				         + "#            D'ARRIGO Valentin");
		System.out.println("###");	
	}
	
	public static void main (String [] args) {

		displayStart();
		
		if (args.length >= 4) {
			String inputFormat = args[0];
			String outputFormat = args[1];
			File inputFile = new File(args[2]);
			File outputFile = new File (args[3]);
			
			String encoding = new String("");
			if (args.length > 4) {
				encoding = args[4];
			}
			
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
			
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("VERTEX_COVER")) {
				SatFNC TSat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatToVertexCover.convert(TSat);
				graph.exportToDimacs(outputFile);
				System.out.println("# 3-SAT to VERTEX_COVER réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
			
			} else if (inputFormat.equals("SAT") && outputFormat.equals("CLIQUE")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				Graph graph = SatToCliqueBis.convert(sat);
				graph.exportToDimacs(outputFile);
				System.out.println("# SAT to CLIQUE réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
			
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("CLIQUE")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatToCliqueThread.convert(sat);
				graph.exportToDimacs(outputFile);
				System.out.println("# 3SAT to CLIQUE réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
				
			} else if (inputFormat.equals("CSP") && outputFormat.equals("SAT") && encoding.equals("DIRECT")) {
				BinCSP csp = BinCSP.importFromXML(inputFile.getAbsolutePath());
				SatFNC sat = CSPToSat.directEncoding(csp);
				sat.exportToDimacs(outputFile);
				System.out.println("# CSP to SAT réduction [DIRECT_ENCODING]");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				System.out.println("# output " + outputFile.getAbsolutePath());
			
			} else {
				System.out.println("# Invalid command.");
				displayUsage();
			
			}
			
			System.out.println("###");
		} else {
			displayUsage();
		}	
	}	
}
