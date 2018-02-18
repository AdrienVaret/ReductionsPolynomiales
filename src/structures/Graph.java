package structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {
	
	private int nbVertex;
	private int nbEdges;
	private ArrayList<Edge> edges;
	//private ArrayList<Integer> edgesList;
	
	public Graph(int nbVertex, int nbEdges, ArrayList<Edge> edges) {
		this.nbEdges = nbEdges;
		this.nbVertex = nbVertex;
		this.edges = edges;
		//edgesList = edgesToList();
	}
	
	public int getNbVertex() {
		return nbVertex;
	}



	public int getNbEdges() {
		return nbEdges;
	}

	
	public ArrayList<Integer> edgesToList() {
		ArrayList <Integer> list = new ArrayList<Integer>();
		for (Edge edge : edges) {
			list.add(edge.getVertex1());
			list.add(edge.getVertex2());
		}
		return list;
	}


	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public static Graph importFromDimacs(File file) {
		int nbEdges = 0, nbVertex = 0;
		ArrayList<Edge> edges = new ArrayList<Edge>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String currentLine;
			boolean firstLine = true;
			
			while((currentLine = reader.readLine()) != null) {
				String [] splittedString = currentLine.split(" ");
				
				if (firstLine) {
					nbVertex = Integer.parseInt(splittedString[2]);
					nbEdges  = Integer.parseInt(splittedString[3]);
					firstLine = false;
				} else {
					edges.add(new Edge(Integer.parseInt(splittedString[1]), Integer.parseInt(splittedString[2])));
				}
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file " + file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Error while loading file " + file.getAbsolutePath());
		} catch (NumberFormatException e) {
			System.out.println("Invalid format file " + file.getAbsolutePath());
		}
		return new Graph(nbVertex, nbEdges, edges);
	}
	
	public void exportToDimacs(File f) {
		try {
			FileWriter writer = new FileWriter(f);
			writer.write(toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String str = "p edges " + nbVertex + " " + nbEdges + "\n";
		for (Edge edge : edges) {
			str += "e " + edge.getVertex1() + " " + edge.getVertex2() + "\n";
		}
		return str;
	}
	
	/*les fonctions en dessous servent à écrire un problème 3-col au format XCSP3
	 * et à partir de ce format on résoudre ce prolème avec un solver adéquat */
	
	/*
	 * retourne une chaine de caractère representant un fichier au format XCSP3
	 * correspodant au problème 3-col du graphe
	 * */
	public String TreeColToXCSP3(){
		StringBuilder str = new StringBuilder();
		str.append("<instance  format=\"XCSP3\" type=\"CSP\">\r\n");
		appendVariablesFor3ColToXCSP(str);
		appendConstraintsFor3ColToXCSP(str);
		str.append("</instance>");
		return str.toString();
	}
	
	/*
	 * ecrit un fichier au format XCSP3 correspodant au problème 3-col du graphe
	 * vers le fichier f dans le répertoire courrant
	 */
	public void export3ColToXCSP3(File f) {
		try {
			FileWriter writer = new FileWriter(f);
			writer.write(TreeColToXCSP3());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ajoute la valise <var> correspondant au sommet à la chaine de carractère
	 * */
	private void appendVarFor3ColToXCSP(StringBuilder str, int vertex){
		str.append("\t\t<var id=\"v"+vertex+"\"> 0 1 2 </var>\r\n");
	}
	
	/*
	 * ajoute la valise <variables> correspondant aux sommets à la chaine de carractère
	 * */
	private void appendVariablesFor3ColToXCSP(StringBuilder str){
		str.append("\t<variables>\r\n");
		
		for (int i =0; i < nbVertex; i++)
			appendVarFor3ColToXCSP(str, i);
		
		str.append("\t</variables>\r\n");
	}
	
	/*
	 * ajoute la valise <extension> correspondant a une arête à la chaine de carractère
	 * */
	private void appendExtensionFor3ColToXCSP(StringBuilder str, Edge edge){
		str.append("\t\t<extension>\r\n");
		
		str.append("\t\t\t<list>v"+edge.getVertex1() + " v" + edge.getVertex2() +"</list>\r\n");
		
		str.append("\t\t\t<supports>(0,1)(0,2)(1,0)(1,2)(2,0)(2,1)</supports>\r\n");
		
		str.append("\t\t</extension>\r\n");
	}
	
	/*
	 * ajoute la valise <constraints> correspondant aux arêtes à la chaine de carractère
	 * */
	private void appendConstraintsFor3ColToXCSP(StringBuilder str){
		str.append("\t<constraints>\r\n");
		
		for (Edge edge : edges)
			appendExtensionFor3ColToXCSP(str, edge);
		
		str.append("\t</constraints>\r\n");
	}
}
