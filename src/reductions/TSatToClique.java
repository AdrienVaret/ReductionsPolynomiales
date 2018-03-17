package reductions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

import structures.Edge;
import structures.Graph;
import structures.SatFNC;

public class TSatToClique {

	
	public static HashMap<Integer, ArrayList<Integer>> initHashMap (SatFNC sat, ArrayList<ArrayList<Integer>> vertexClauses){
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		int idVertex = 1;
		for (String clause : sat.getClauses()) {
			ArrayList<Integer> litterals = new ArrayList<Integer>();
			String [] splittedClause = clause.split(" ");
			for (int i = 0 ; i < splittedClause.length -1 ; i++) { //3 avant
				int litteral = Integer.parseInt(splittedClause[i]);
				if (map.get(litteral) == null) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					list.add(idVertex);
					map.put(litteral, list);
				} else {
					map.get(litteral).add(idVertex);
				}
				litterals.add(idVertex);
				idVertex += 1;
			}
			vertexClauses.add(litterals);
		}
		
		return map;
	}
	
	public static boolean inSameClause(int vertex1, int vertex2, ArrayList<ArrayList<Integer>> vertexClauses) {
	/*	double q1 = ((double)vertex1) / 3.0;
		double q2 = ((double)vertex2) / 3.0;
		if (Math.ceil(q1) == Math.ceil(q2)) return true;
		else return false; */
		boolean v1 = false, v2 = false;
		for (ArrayList<Integer> list : vertexClauses) {
			for (Integer vertex : list) {
				if (vertex == vertex1) v1=true;
				if (vertex == vertex2) v2=true;
				if (v1 && v2) return true;
			}
			v1 = false;
			v2 = false;
		}
		return false;
	}
	
	public static Graph convert(SatFNC sat) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		ArrayList<ArrayList<Integer>> vertexClauses = new ArrayList<ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> map = initHashMap(sat, vertexClauses);
		java.util.Iterator<Entry<Integer, ArrayList<Integer>>> i = map.entrySet().iterator();
		
		while (i.hasNext()) {
			java.util.Iterator<Entry<Integer, ArrayList<Integer>>> j = map.entrySet().iterator();
			Map.Entry <Integer, ArrayList<Integer>> mi = (Entry<Integer, ArrayList<Integer>>)i.next();
			while (j.hasNext()) {
				Map.Entry <Integer, ArrayList<Integer>> mj = (Entry<Integer, ArrayList<Integer>>)j.next();
				if ((mi.getKey() != -mj.getKey())) {
					ArrayList<Integer> l1 = mi.getValue();
					ArrayList<Integer> l2 = mj.getValue();
					for (Integer k : l1) {
						for (Integer l : l2) {
							if (!inSameClause(k, l, vertexClauses) && (l>k)) edges.add(new Edge(k, l));
						}
					}
				}
			}
		}
		
		return new Graph(3 * sat.getNbClauses(), edges.size(), edges, sat.getNbClauses());
	}
	
	public static void main(String [] args) {
		SatFNC sat = SatFNC.importFromDimacs(new File("test.cnf"));
		Graph graph = convert(sat);
		System.out.println(graph.toString());
	}
}
