package structures;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

public class BinCSP {

	private int nbDomaines, nbVariables, nbRelations, nbConstraints;
	
	private ArrayList<Domain> domains = new ArrayList<Domain>();
	private ArrayList<Variable> variables = new ArrayList<Variable>();
	private ArrayList<Relation> relations = new ArrayList<Relation>();
	private ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	
	public BinCSP(int nbDomaines, int nbVariables, int nbRelations, int nbConstraints, ArrayList<Domain> domains,
			ArrayList<Variable> variables, ArrayList<Relation> relations, ArrayList<Constraint> constraints) {
		this.nbDomaines = nbDomaines;
		this.nbVariables = nbVariables;
		this.nbRelations = nbRelations;
		this.nbConstraints = nbConstraints;
		this.domains = domains;
		this.variables = variables;
		this.relations = relations;
		this.constraints = constraints;
	}

	
	
	public int getNbDomaines() {
		return nbDomaines;
	}



	public int getNbVariables() {
		return nbVariables;
	}



	public int getNbRelations() {
		return nbRelations;
	}



	public int getNbConstraints() {
		return nbConstraints;
	}



	public ArrayList<Domain> getDomains() {
		return domains;
	}



	public ArrayList<Variable> getVariables() {
		return variables;
	}



	public ArrayList<Relation> getRelations() {
		return relations;
	}



	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}



	public static Domain getDomain(String name, ArrayList<Domain> domains) {
		for (Domain domain : domains) {
			if (domain.getName().equals(name))
				return domain;
		}
		return null;
	}
	
	public static Variable getVariable(String name, ArrayList<Variable> variables) {
		for (Variable variable : variables) {
			if (variable.getName().equals(name))
				return variable;
		}
		return null;
	}
	
	public static Relation getRelation(String name, ArrayList<Relation> relations) {
		for (Relation relation : relations) {
			if (relation.getName().equals(name))
				return relation;
		}
		return null;
	}
	
	//parse (x,y)|(a,b)
	public static ArrayList<Couple> parseRelation(String relation){
		ArrayList<Couple> couples = new ArrayList<Couple>();
		String [] strCouples = relation.split(Pattern.quote("|"));
		for (int i = 0 ; i < strCouples.length ; i++) {
			String [] strCouple = strCouples[i].split(" ");
			strCouple[0] = strCouple[0].replaceAll("\\r|\\n", "");
			strCouple[1] = strCouple[1].replaceAll("\\r|\\n", "");
			couples.add(new Couple(strCouple[0], strCouple[1]));
		}
		return couples;
	}
	
	//parse domain x..y
	public static ArrayList<String> parseDomain(String domain){
		ArrayList<String> values = new ArrayList<String>();
		
		String [] splittedDomain = domain.split(Pattern.quote(".."));
		
		for (int i = Integer.parseInt(splittedDomain[0]) ; i <= Integer.parseInt(splittedDomain[1]) ; i++) {
			values.add(Integer.toString(i));
		}
		
		return values;
		
	}
	
	public static BinCSP importFromXML(String filename) {
	
		ArrayList<Domain> domains         = new ArrayList<Domain>();
		ArrayList<Variable> variables     = new ArrayList<Variable>();
		ArrayList<Relation> relations     = new ArrayList<Relation>();
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		
		Document document = null;
		DocumentBuilderFactory factory = null;
		
		int nbVariables = 0;
		int nbDomains = 0;
		int nbRelations = 0;
		int nbConstraints = 0;
		
		try {
			factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(filename);
			DocumentTraversal traversal = (DocumentTraversal) document;
			NodeIterator iterator = traversal.createNodeIterator(
					document.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);
			
			for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
				switch  (((Element) n).getTagName()) {
				
					case "domains" : 
						nbDomains = Integer.parseInt(((Element) n).getAttribute("nbDomains"));
						break;
						
					case "domain" :
							ArrayList<String> values = 
									parseDomain(((Element) n).getFirstChild().getTextContent());
							domains.add(new Domain(((Element) n).getAttribute("name"),
									               Integer.parseInt(((Element) n).getAttribute("nbValues")),
									               values));
						break;
				
					case "variable" :
						variables.add(new Variable(((Element)n).getAttribute("name"),
								                   getDomain(((Element)n).getAttribute("domain"), domains)));
						break;
						
					case "variables" : 
						nbVariables = Integer.parseInt(((Element) n).getAttribute("nbVariables"));
						break;
						
					case "relation" : 
						ArrayList<Couple> couples = parseRelation(((Element) n).getFirstChild().getTextContent());
						String name = ((Element)n).getAttribute("name");
						Relation.TypeRelation type;
						if (((Element)n).getAttribute("semantics").equals("conflicts")) {
							type = Relation.TypeRelation.R_CONFLICT;
						} else {
							type = Relation.TypeRelation.R_ALLOWED;
						}
						int nbTuples = Integer.parseInt(((Element) n).getAttribute("nbTuples"));
						relations.add(new Relation(name, type, nbTuples, couples));
						break;
						
					case "relations" :
						nbRelations = Integer.parseInt(((Element)n).getAttribute("nbRelations"));
						break;
						
					case "constraint" : 
						String scope = ((Element) n).getAttribute("scope");
						String [] splittedScope = scope.split(" ");
						constraints.add(new Constraint(((Element) n).getAttribute("name"),
								                       getVariable(splittedScope[0], variables),
								                       getVariable(splittedScope[1], variables),
								                       getRelation(((Element) n).getAttribute("reference"), relations)));
						break;
						
					case "constraints" :
						nbConstraints = Integer.parseInt(((Element) n).getAttribute("nbConstraints"));
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new BinCSP(nbDomains, nbVariables, nbRelations, nbConstraints, domains, 
				variables, relations, constraints);
	}
	
	public static void main(String [] args) {
		BinCSP csp = importFromXML("test3col.xml");
		System.out.println(".");
	}
}
