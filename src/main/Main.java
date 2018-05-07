package main;

import java.io.File;

import reductions.CSPToSat;
import reductions.SatTo3Sat;
import reductions.SatToCliqueBis;
import reductions.TColToSat;
import reductions.TSatTo3Col;
import reductions.TSatToCliqueThread;
import reductions.TSatToVertexCover;
import structures.BinCSP;
import structures.Graph;
import structures.SatFNC;

public class Main {

	public static void displayUsage() {
		System.out.println("# USAGE: java -jar Converter $PIn $POut inputFile outputFile [SUPPORT | DIRECT]");
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
	
	public static void displayGraphInfos(Graph graph) {
		System.out.println("# @nb_vertexs : "      + graph.getNbVertex());
		System.out.println("# @nb_edges : "        + graph.getNbEdges());
		System.out.println("# @k [-1:not_used] : " + graph.getK());
	}
	
	public static void displaySATInfos(SatFNC sat) {
		System.out.println("@nb_variables : " + sat.getNbLitterals());
		System.out.println("@nb_clauses : " + sat.getNbClauses());
		
		int nbUnitaryClauses = 0;
		int nbBinaryClauses  = 0;
		int nbTernaryClauses = 0;
		int nbLongClauses    = 0;
		
		for (String clause : sat.getClauses()) {
			String [] splittedClause = clause.split(" ");
			if (splittedClause.length - 1 == 1) nbUnitaryClauses ++;
			else if (splittedClause.length - 1 == 2) nbBinaryClauses ++;
			else if (splittedClause.length - 1 == 3) nbTernaryClauses ++;
			else nbLongClauses ++;
		}
		
		System.out.println("# @nb_unitary_clauses : " + nbUnitaryClauses);
		System.out.println("# @nb_binary_clauses : "  + nbBinaryClauses);
		System.out.println("# @nb_ternary_clauses : " + nbTernaryClauses);
		System.out.println("# @nb_long_clauses : "    + nbLongClauses);
		
		System.out.println("@ratio_cx : " + (float)sat.getNbClauses() / (float)sat.getNbLitterals());
	}
	
	public static void displayCSPInfos(BinCSP csp) {
		System.out.println("# @nb_variables : "   + csp.getNbVariables());
		System.out.println("# @nb_constraints : " + csp.getNbConstraints());
		System.out.println("# @nb_domains : "     + csp.getNbDomaines());
		System.out.println("# @nb_relations : "   + csp.getNbRelations());
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
			
			
			String benchmark = new String("");
			if (args.length > 5) {
				benchmark = args[5];
			}
			
			if (inputFormat.equals("SAT") && outputFormat.equals("3SAT")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				SatFNC Tsat = SatTo3Sat.convert(sat);
				Tsat.exportToDimacs(outputFile);
				System.out.println("# SAT to 3-SAT réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displaySATInfos(sat);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displaySATInfos(Tsat);
				
			} else if (inputFormat.equals("3COL") && outputFormat.equals("SAT")) {
				Graph graph = Graph.importFromDimacs(inputFile);
				SatFNC sat  = TColToSat.convert(graph);
				sat.exportToDimacs(outputFile);
				System.out.println("# 3-COL to SAT réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displayGraphInfos(graph);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displaySATInfos(sat);
				
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("3COL")) {
				SatFNC Tsat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatTo3Col.convert(Tsat);
				graph.exportToDimacs(outputFile);
				System.out.println("# 3-SAT to 3-COL réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displaySATInfos(Tsat);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displayGraphInfos(graph);
				
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("VERTEX_COVER")) {
				SatFNC TSat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatToVertexCover.convert(TSat);
				graph.exportToDimacs(outputFile);
				System.out.println("# 3-SAT to VERTEX_COVER réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displaySATInfos(TSat);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displayGraphInfos(graph);
				
			} else if (inputFormat.equals("SAT") && outputFormat.equals("CLIQUE")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				Graph graph = SatToCliqueBis.convert(sat);
				graph.exportToDimacs(outputFile);
				System.out.println("# SAT to CLIQUE réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displaySATInfos(sat);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displayGraphInfos(graph);
					
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("CLIQUE")) {
				SatFNC sat = SatFNC.importFromDimacs(inputFile);
				Graph graph = TSatToCliqueThread.convert(sat);
				graph.exportToDimacs(outputFile);
				System.out.println("# 3SAT to CLIQUE réduction");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displaySATInfos(sat);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displayGraphInfos(graph);
				
			} else if (inputFormat.equals("CSP") && outputFormat.equals("SAT") && encoding.equals("DIRECT")) {
				BinCSP csp = BinCSP.importFromXML(inputFile.getAbsolutePath());
				SatFNC sat = CSPToSat.directEncoding(csp);
				sat.exportToDimacs(outputFile);
				System.out.println("# CSP to SAT réduction [DIRECT_ENCODING]");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displayCSPInfos(csp);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displaySATInfos(sat);
			
			} else if (inputFormat.equals("CSP") && outputFormat.equals("SAT") && encoding.equals("SUPPORT")) {
				BinCSP csp = BinCSP.importFromXML(inputFile.getAbsolutePath());
				SatFNC sat = CSPToSat.supportEncoding(csp);
				sat.exportToDimacs(outputFile);
				System.out.println("# CSP to SAT réduction [SUPPORT_ENCODING]");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displayCSPInfos(csp);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displaySATInfos(sat);
			
			} else if (inputFormat.equals("CSP3") && outputFormat.equals("SAT") && encoding.equals("DIRECT") && benchmark.equals("RANDOM")) {
				BinCSP csp = BinCSP.importFromXCSP3Rand(inputFile.getAbsolutePath());
				SatFNC sat = CSPToSat.directEncoding(csp);
				sat.exportToDimacs(outputFile);
				System.out.println("# CSP to SAT réduction [DIRECT_ENCODING]");
				System.out.println("# input "  + inputFile.getAbsolutePath());
				displayCSPInfos(csp);
				System.out.println("# output " + outputFile.getAbsolutePath());
				displaySATInfos(sat);
				
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
