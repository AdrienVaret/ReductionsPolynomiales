package structures;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
	
	//parse domain x..y OR x y z
	public static Domain parseDomain(String domain, String name, int nbValues){
		ArrayList<String> values = new ArrayList<String>();
		
		int maxValue = Integer.MIN_VALUE;
		int minValue = Integer.MAX_VALUE;
		
		String [] splittedDomain = domain.split(Pattern.quote(".."));
		if (splittedDomain.length > 1) { // format x..y
			for (int i = Integer.parseInt(splittedDomain[0]) ; i <= Integer.parseInt(splittedDomain[1]) ; i++) {
				values.add(Integer.toString(i));
				if (i > maxValue) maxValue = i;
				if (i < minValue) minValue = i;
			}
		} else {
			splittedDomain = domain.split(" ");
			for (int i = 0 ; i < splittedDomain.length ; i++) {
				values.add(splittedDomain[i]);
				if (Integer.parseInt(splittedDomain[i]) > maxValue)
					maxValue = Integer.parseInt(splittedDomain[i]);
				
				if (Integer.parseInt(splittedDomain[i]) < minValue)
					minValue = Integer.parseInt(splittedDomain[i]);
			}
		}
		
		return new Domain(name,
	               nbValues,
	               values, maxValue, minValue);
		
	}
	
	public static void shiftDomains(BinCSP csp) {
		int steps[] = new int [csp.getNbDomaines()];
		
		int i = 0;
		for (Domain domain : csp.getDomains()) {
			if (domain.getMinValue() > 0) {
				int j = 0;
				for (String value : domain.getValues()) {
					String newValue = Integer.toString(Integer.parseInt(value) - domain.getMinValue());
					domain.getValues().set(j, new String(newValue));
					steps[i] = domain.getMinValue();
					j++;
				}
			}
			i = i+1;
		}
		
		//Provisoire : marche uniquement pour un domain unique
		
		for (Relation relation : csp.getRelations()) {
			i = 0;
			for (Couple couple : relation.getCouples()) {
				String v1 = Integer.toString(Integer.parseInt(couple.getValue1()) - steps[0]);
				String v2 = Integer.toString(Integer.parseInt(couple.getValue2()) - steps[0]);
				relation.getCouples().set(i, new Couple(v1,v2));
				i++;
			}
		}
		
		/*
		for (Constraint constraint : csp.getConstraints()) {
			int d1 = Integer.parseInt(constraint.getV1().getDomain().getName().substring(1));
			int d2 = Integer.parseInt(constraint.getV2().getDomain().getName().substring(1));
			
			i = 0;
			for (Couple couple : constraint.getRelation().getCouples()) {
				String v1 = Integer.toString(Integer.parseInt(couple.getValue1()) - steps[d1]);
				String v2 = Integer.toString(Integer.parseInt(couple.getValue2()) - steps[d2]);
				constraint.getRelation().getCouples().set(i, new Couple(v1,v2));
			}
		}*/
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
							 domains.add( parseDomain(((Element) n).getFirstChild().getTextContent(),
											          ((Element) n).getAttribute("name"),
											          Integer.parseInt(((Element) n).getAttribute("nbValues"))));
							
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
			e.printStackTrace();
		}
		
		BinCSP csp = new BinCSP(nbDomains, nbVariables, nbRelations, nbConstraints, domains, 
				variables, relations, constraints);
		
		shiftDomains(csp);
		
		return csp;
	}
	
	public static void main(String [] args) {
		@SuppressWarnings("unused")
		BinCSP csp = importFromXML("test.xml");
		System.out.println("");
	}
}
