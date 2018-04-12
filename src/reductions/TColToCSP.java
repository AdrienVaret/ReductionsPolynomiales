package reductions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import structures.Edge;
import structures.Graph;

public class TColToCSP {

	/*les fonctions en dessous servent à écrire un problème 3-col au format XCSP3
	 * et à partir de ce format on résoudre ce prolème avec un solver adéquat */
	
	/*
	 * retourne une chaine de caractère representant un fichier au format XCSP3
	 * correspodant au problème 3-col du graphe
	 * */
	public static String TreeColToXCSP3(Graph g){
		StringBuilder str = new StringBuilder();
		str.append("<instance  format=\"XCSP3\" type=\"CSP\">\r\n");
		appendVariablesFor3ColToXCSP(str, g);
		appendConstraintsFor3ColToXCSP(str, g);
		str.append("</instance>");
		return str.toString();
	}
	
	/*
	 * ecrit un fichier au format XCSP3 correspodant au problème 3-col du graphe
	 * vers le fichier f dans le répertoire courrant
	 */
	public static void export3ColToXCSP3(File f, Graph g) {
		try {
			FileWriter writer = new FileWriter(f);
			writer.write(TreeColToXCSP3(g));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ajoute la valise <var> correspondant au sommet à la chaine de carractère
	 * */
	private static void appendVarFor3ColToXCSP(StringBuilder str, int vertex){
		str.append("\t\t<var id=\"v"+vertex+"\"> 0 1 2 </var>\r\n");
	}
	
	/*
	 * ajoute la valise <variables> correspondant aux sommets à la chaine de carractère
	 * */
	private static void appendVariablesFor3ColToXCSP(StringBuilder str, Graph g){
		str.append("\t<variables>\r\n");
		
		for (int i =0; i < g.getNbVertex(); i++)
			appendVarFor3ColToXCSP(str, i);
		
		str.append("\t</variables>\r\n");
	}
	
	/*
	 * ajoute la valise <extension> correspondant a une arête à la chaine de carractère
	 * */
	private static void appendExtensionFor3ColToXCSP(StringBuilder str, Edge edge){
		str.append("\t\t<extension>\r\n");
		
		str.append("\t\t\t<list>v"+edge.getVertex1() + " v" + edge.getVertex2() +"</list>\r\n");
		
		str.append("\t\t\t<supports>(0,1)(0,2)(1,0)(1,2)(2,0)(2,1)</supports>\r\n");
		
		str.append("\t\t</extension>\r\n");
	}
	
	/*
	 * ajoute la valise <constraints> correspondant aux arêtes à la chaine de carractère
	 * */
	private static void appendConstraintsFor3ColToXCSP(StringBuilder str, Graph g){
		str.append("\t<constraints>\r\n");
		
		for (Edge edge : g.getEdges())
			appendExtensionFor3ColToXCSP(str, edge);
		
		str.append("\t</constraints>\r\n");
	}
}
