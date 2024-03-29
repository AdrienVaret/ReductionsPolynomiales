package structures;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import reductions.CSPToSat;
import structures.Relation.TypeRelation;

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
			domain.setMaxValue(domain.getMaxValue() - domain.getMinValue());
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
	
	public static BinCSP importFromXCSP3Rand(String filename) {
		
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
		
		ArrayList<CoupleVariables> couplesVariables = new ArrayList<CoupleVariables>();
		
		try {
			factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(filename);
			DocumentTraversal traversal = (DocumentTraversal) document;
			NodeIterator iterator = traversal.createNodeIterator(
					document.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);
			
			for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
				switch  (((Element) n).getTagName()) {
					case "array" :
						String baseName = ((Element) n).getAttribute("id");
						String baseSize = ((Element) n).getAttribute("size");
						int size = Integer.parseInt(baseSize.split(Pattern.quote("["))[1].split(Pattern.quote("]"))[0]);
						String domain = ((Element) n).getFirstChild().getTextContent();
						String [] splittedDomain = domain.split(Pattern.quote(".."));
						int domainSize = 0;
						ArrayList<String> values = new ArrayList<String>();
						
						for (int i = 0 ; i < splittedDomain.length ; i++) {
							splittedDomain[i] = splittedDomain[i].replaceAll(Pattern.quote(" "), "");
						}
						
						for (int i = Integer.parseInt(splittedDomain[0]) ; i <= Integer.parseInt(splittedDomain[1]) ; i++) {
							values.add(Integer.toString(i));
							domainSize ++;
						}
						
						domains.add(new Domain("D0", domainSize, values, Integer.parseInt(splittedDomain[1]), 
								Integer.parseInt(splittedDomain[0])));
						
						for (int i = 0 ; i < size ; i++) {
							variables.add(new Variable("V" + i, domains.get(0)));
						}
						
						break;
						
					case "extension" : 
						break;
						
					case "list" : 
						String strVariables = ((Element)n).getFirstChild().getTextContent();
						strVariables = strVariables.replaceAll(Pattern.quote(" "), "");
						strVariables = strVariables.replaceAll(Pattern.quote("]x["), " ");
						strVariables = strVariables.replaceAll(Pattern.quote("x["), "");
						strVariables = strVariables.replaceAll(Pattern.quote("]"), "");
						
						String [] splittedVariables = strVariables.split(" ");
						int v1 = Integer.parseInt(splittedVariables[0]);
						int v2 = Integer.parseInt(splittedVariables[1]);
						couplesVariables.add(new CoupleVariables(variables.get(v1), variables.get(v2)));
						break;
						
					case "conflicts" : 
						String strCouples = ((Element) n).getFirstChild().getTextContent();
						strCouples = strCouples.replaceAll(Pattern.quote(")("), " ");
						strCouples = strCouples.replaceAll(Pattern.quote("("), "");
						strCouples = strCouples.replaceAll(Pattern.quote(")"), "");
						strCouples = strCouples.replaceFirst(Pattern.quote(" "), "");
						
						String [] splittedCouples = strCouples.split(" ");
						ArrayList<Couple> couples = new ArrayList<Couple>();
						for (int j = 0 ; j < splittedCouples.length ; j++) {
							String [] couple = splittedCouples[j].split(Pattern.quote(","));
							couples.add(new Couple(couple[0], couple[1]));
						}
						
						Relation relation = new Relation("R" + relations.size(), 
								TypeRelation.R_CONFLICT, couples.size(), couples);
						
						relations.add(relation);
						break;
						
				}
			}
			for (int i = 0 ; i < couplesVariables.size() ; i++) {
				Constraint constraint = new Constraint("C" + i, couplesVariables.get(i).getV1(),
						                               couplesVariables.get(i).getV2(),
						                               relations.get(i));
				constraints.add(constraint);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new BinCSP(domains.size(), variables.size(), relations.size(), constraints.size(), domains,
			variables, relations, constraints);
	}
	
	public static void main(String [] args) {
	/*	@SuppressWarnings("unused")
		BinCSP csp = importFromXML("test.xml");
		System.out.println("");
		
		String strVariables = " x[9] x[13] ";
		strVariables = strVariables.replaceAll(Pattern.quote(" "), "");
		strVariables = strVariables.replaceAll(Pattern.quote("]x["), " ");
		strVariables = strVariables.replaceAll(Pattern.quote("x["), "");
		strVariables = strVariables.replaceAll(Pattern.quote("]"), "");
		System.out.println(strVariables);*/
		
		BinCSP csp = importFromXCSP3Rand("testcsp3.xml");
	//	System.out.println("");
		SatFNC sat = CSPToSat.directEncoding(csp);
		System.out.println(sat.toString());
		
	}
}
