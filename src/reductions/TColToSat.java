package reductions;

import java.util.ArrayList;

import structures.Edge;
import structures.Graph;
import structures.SatFNC;
import structures.VertexLitterals;

public class TColToSat {

	
	public static int getNbVariables(Graph graph){
		return 3 * graph.getNbVertex();
	}
	
	public static int getNbClauses(Graph graph){
		return (4 * graph.getNbVertex()) + (3 * graph.getNbEdges()); 
	}
	
	public static void clausesPlacement(Graph graph, SatFNC sat){ /* CREE 4 * nbSommets clauses */
		for(int i = 0 ; i < getNbVariables(graph) ; i += 3){
			
			int var1 = (i)+1;
			int var2 = (i+1)+1;
			int var3 = (i+2)+1;
			
			sat.addClause(new String (var1 + " " + var2 + " " + var3));
			sat.addClause(new String (-var1 + " " + -var2));
			sat.addClause(new String (-var1 + " " + -var3));
			sat.addClause(new String (-var2 + " " + -var3));
		}
	}
	
	public static VertexLitterals[] remplirVariableSommets(Graph graph){
		VertexLitterals[] sommetsVariables = new VertexLitterals[graph.getNbVertex()];
	
		int idSommet = 0;
		for(int i = 0 ; i < (3 * graph.getNbVertex()) ; i += 3){
			sommetsVariables[idSommet] = new VertexLitterals(idSommet+1, (i)+1, (i+1)+1, (i+2)+1);
			idSommet += 1;
		}
		return sommetsVariables;
	}
	
	public static void clausesAretes(Graph graph, SatFNC sat){/* CREE 3 * nbAretes clauses */
		//ArrayList<Integer> graphArray = graphe.getDimacs();
		VertexLitterals[] sommetsVariables = remplirVariableSommets(graph);
		
		@SuppressWarnings("unused")
		int i = 0;
		for (Edge edge : graph.getEdges()) {
			VertexLitterals src  = sommetsVariables[edge.getVertex1()-1];
			VertexLitterals dest = sommetsVariables[edge.getVertex2()-2] ;
			
			sat.addClause(-src.var1() + " " + -dest.var1() + " 0");
			sat.addClause(-src.var2() + " " + -dest.var2() + " 0");
			sat.addClause(-src.var3() + " " + -dest.var3() + " 0");
			
			i += 2;
		}
	}
	/*
		for(int i = 0 ; i < (2*graphe.getNbAretes()) ; i += 2){
			SommetVariable origine = sommetsVariables[graphArray.get(i)-1];
			SommetVariable destination = sommetsVariables[graphArray.get(i+1)-1];
			
			sat.ajouterClause(new String(-origine.var1() + " " + -destination.var1()));
			sat.ajouterClause(new String(-origine.var2() + " " + -destination.var2()));
			sat.ajouterClause(new String(-origine.var3() + " " + -destination.var3()));
		}
	*/
	
	public static SatFNC convert(Graph graph) {
		SatFNC sat = new SatFNC(getNbVariables(graph), getNbClauses(graph), new ArrayList<String>());
		clausesPlacement(graph, sat);
		clausesAretes(graph, sat);
		return null;
	}
}
