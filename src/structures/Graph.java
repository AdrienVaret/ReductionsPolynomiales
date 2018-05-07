package structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
	
	private int nbVertex;
	private int nbEdges;
	private ArrayList<Edge> edges;
	private int k; //IMPLIED, used for Vertex Cover problem
	//private ArrayList<Integer> edgesList;
	
	
	public Graph(int nbVertex, int nbEdges, ArrayList<Edge> edges) {
		this.nbEdges = nbEdges;
		this.nbVertex = nbVertex;
		this.edges = edges;
		k = -1;
		//edgesList = edgesToList();
	}
	
	public Graph(int nbVertex, int nbEdges, ArrayList<Edge> edges, int k) {
		this.nbEdges = nbEdges;
		this.nbVertex = nbVertex;
		this.edges = edges;
		this.k = k;
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

	public int getK() {
		return k;
	}
	
	public static Graph importFromDimacs(File file) {
		int nbEdges = 0, nbVertex = 0;
		ArrayList<Edge> edges = new ArrayList<Edge>();
		String currentLine;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			do{
				currentLine = reader.readLine();
			}while(!currentLine.startsWith("p"));
			
			String [] splittedString = currentLine.split(" ");
			nbVertex = Integer.parseInt(splittedString[2]);
			nbEdges  = Integer.parseInt(splittedString[3]);
			
			while((currentLine = reader.readLine()) != null) {
				splittedString = currentLine.split(" ");
					edges.add(new Edge(Integer.parseInt(splittedString[1]), Integer.parseInt(splittedString[2])));
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

	public static Graph generateCompleteGraph(int nbVertex) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		for (int i = 1 ; i <= nbVertex ; i++) {
			for (int j = i+1 ; j <= nbVertex ; j++) {
				edges.add(new Edge(i,j));
			}
		}
		
		return new Graph(nbVertex, edges.size(), edges);
	}
	
	public static Graph generate(int nbVertex, int nbEdges) {
	
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		for (int i = 0 ; i < nbEdges ; i++ ) {	
			int vertex1 = ThreadLocalRandom.current().nextInt(1, nbVertex + 1);
			int vertex2 = ThreadLocalRandom.current().nextInt(1, nbVertex + 1);
			
			if (vertex1 != vertex2) {
				edges.add(new Edge(vertex1, vertex2));
			} else {
				i --;
			}
		}
		
		Set<Edge> hs = new TreeSet<Edge>();
		hs.addAll(edges);
		edges.clear();
		edges.addAll(hs);
		
		while (edges.size() < nbEdges) {
			int n = nbEdges - edges.size();
			for (int i = 0 ; i < n ; i++) {
				int vertex1 = ThreadLocalRandom.current().nextInt(1, nbVertex + 1);
				int vertex2 = ThreadLocalRandom.current().nextInt(1, nbVertex + 1);
				
				if (vertex1 != vertex2) {
					edges.add(new Edge(vertex1, vertex2));
				} else {
					i --;
				}
			}
			
			hs = new TreeSet<Edge>();
			hs.addAll(edges);
			edges.clear();
			edges.addAll(hs);
		}
		
		return new Graph(nbVertex, edges.size(), edges);
	}
	
	public String toString() {
		String str = "c k = " + k + "[-1 : undefined]\n";
		str += "p edges " + nbVertex + " " + nbEdges + "\n";
		for (Edge edge : edges) {
			str += "e " + edge.getVertex1() + " " + edge.getVertex2() + "\n";
		}
		return str;
	}
	
	public static void main (String [] args) {
		Graph g = generateCompleteGraph(4);
		System.out.println(g.toString());
	}
}
