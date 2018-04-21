package reductions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import structures.BinCSP;
import structures.Constraint;
import structures.Couple;
import structures.Domain;
import structures.Relation.TypeRelation;
import structures.SatFNC;
import structures.Variable;

@SuppressWarnings("unused")
public class CSPToSat {

	@SuppressWarnings("unused")
	public static SatFNC supportEncoding(BinCSP csp) {
		ArrayList<String> clauses = new ArrayList<String>();
		int litteral = 1;
		
		/* encoding variables and domains */
		for (Variable variable : csp.getVariables()) {
			String clause = new String();
			for (Domain domain : csp.getDomains()) {
				for (String value : domain.getValues()) {
					clause += Integer.toString(litteral) + " ";
					litteral += 1;
				}
			}
			clause += "0";
			clauses.add(clause);
			String [] splittedClause = clause.split(" ");
			for (int i = 0 ; i < splittedClause.length-1 ; i++) {
				for (int j = i+1 ; j < splittedClause.length-1 ; j++) {
					clauses.add(new String("-" + splittedClause[i] + " -" + splittedClause[j] + " 0"));
				}
			}
		}
		
		/* encoding constraints */
		for (Constraint constraint : csp.getConstraints()) {
			Variable v1 = constraint.getV1();
			Variable v2 = constraint.getV2();
			int v1IndexBase = Integer.parseInt(v1.getName().substring(1));
			int v2IndexBase = Integer.parseInt(v2.getName().substring(1)); 
			
			ArrayList<String> values1 = new ArrayList<String>();
			ArrayList<String> values2 = new ArrayList<String>();
			
			for (Couple c1 : constraint.getRelation().getCouples()) {
				String firstValue1 = c1.getValue1();
				String firstValue2 = c1.getValue2();
				for (Couple c2 : constraint.getRelation().getCouples()) {
					if (!c1.equals(c2)) {
						if (!c2.getValue1().equals(firstValue1)) {
							if (!values1.contains(c2.getValue2()))
								values1.add(c2.getValue2());
						} else {
							values1.remove(c2.getValue2());
						}
						/*
						if (!c2.getValue2().equals(firstValue2)) {
							values2.add(c2.getValue2());
						} else {
							values2.remove(c2.getValue1());
						}*/
					}
				}
				String clause = new String();
				clause += "-" + (v1IndexBase * v1.getDomain().getSize() + Integer.parseInt(firstValue1) + 1) + " ";
				for (String value : values1) {
					clause += (v2IndexBase * v1.getDomain().getSize() + Integer.parseInt(value) + 1) + " ";
				}
				clause += "0";
				clauses.add(clause);
				
				values1.clear();
				values2.clear();
			}
		}
		
		return new SatFNC(csp.getNbVariables() * csp.getDomains().get(0).getSize(), clauses.size(), clauses);
	}
	
	public static int getId(Variable variable, int value) {
		int base = Integer.parseInt(variable.getName().substring(1));
		return (base * (variable.getDomain().getMaxValue()+1) + value + 1);
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public static SatFNC directEncoding(BinCSP csp) {
		ArrayList<String> clauses = new ArrayList<String>();
		int litteral = 1;
		
		boolean support = false;
		
		/* encoding variables and domains */
		for (Variable variable : csp.getVariables()) {
			String clause = new String();
			for (Domain domain : csp.getDomains()) {
				for (String value : domain.getValues()) {
					clause += Integer.toString(litteral) + " ";
					litteral += 1;
				}
			}
			clause += "0";
			clauses.add(clause);
			String [] splittedClause = clause.split(" ");
			for (int i = 0 ; i < splittedClause.length-1 ; i++) {
				for (int j = i+1 ; j < splittedClause.length-1 ; j++) {
					clauses.add(new String("-" + splittedClause[i] + " -" + splittedClause[j] + " 0"));
				}
			}
		}
		
		/* encoding constraints ONLY ONE DOMAIN */
		for (Constraint constraint : csp.getConstraints()) {
			Variable v1 = constraint.getV1();
			Variable v2 = constraint.getV2();
			ArrayList<String> values1 = new ArrayList<String>();
			ArrayList<String> values2 = new ArrayList<String>();
			int v1IndexBase = Integer.parseInt(v1.getName().substring(1));
			int v2IndexBase = Integer.parseInt(v2.getName().substring(1)); 		
			
			if (constraint.getRelation().getType().equals(TypeRelation.R_CONFLICT)) {
				for (Couple couple : constraint.getRelation().getCouples()) {
					//int v1Index = v1IndexBase * v1.getDomain().getSize() + Integer.parseInt(couple.getValue1()) + 1;
					//int v2Index = v2IndexBase * v2.getDomain().getSize() + Integer.parseInt(couple.getValue2()) + 1;
					int v1Index = getId(v1, Integer.parseInt(couple.getValue1()));
					int v2Index = getId(v2, Integer.parseInt(couple.getValue2()));
					clauses.add(-v1Index + " " + -v2Index + " 0");
				}
			} else {
				support = true;
				for (Couple c1 : constraint.getRelation().getCouples()) {
					//int v1Index = v1IndexBase * v1.getDomain().getSize() + Integer.parseInt(c1.getValue1()) + 1;
					//int v2Index = v2IndexBase * v2.getDomain().getSize() + Integer.parseInt(c1.getValue2()) + 1;
					int v1Index = getId(v1, Integer.parseInt(c1.getValue1()));
					int v2Index = getId(v2, Integer.parseInt(c1.getValue2()));
					ArrayList<String> allowedValues1 = new ArrayList<String>();
					ArrayList<String> allowedValues2 = new ArrayList<String>();
					for (Couple c2 : constraint.getRelation().getCouples()) {
						if (c2.getValue1().equals(c1.getValue1())) {
							allowedValues1.add(c2.getValue2());
						}
						if (c2.getValue2().equals(c1.getValue2())) {
							allowedValues2.add(c2.getValue1());
						}
					}
					ArrayList<String> forbiddenValues1 = (ArrayList<String>) v1.getDomain().getValues().clone();
					forbiddenValues1.removeAll(allowedValues1);
				
					for (String value : forbiddenValues1) {
						v2Index =  v2IndexBase * v2.getDomain().getSize() + Integer.parseInt(value) + 1;
						clauses.add("-" + v1Index + " -" + v2Index + " 0");
					}
				
					ArrayList<String> forbiddenValues2 = (ArrayList<String>) v2.getDomain().getValues().clone();
					forbiddenValues2.removeAll(allowedValues2);
					
					for (String x : v1.getDomain().getValues()) {
						for (String y : v1.getDomain().getValues()) {
							if (!x.equals(c1.getValue1())) {
								v1Index = v1IndexBase * v1.getDomain().getSize() + Integer.parseInt(x) + 1;
								v2Index = v2IndexBase * v2.getDomain().getSize() + Integer.parseInt(y) + 1;
								clauses.add("-" + v1Index + " -" + v2Index + " 0");
							}
						}
					}
				}
			}
		}
		
		//delete duplicates
	/*	if (support) {
			Set<String> hs = new HashSet<>();
			hs.addAll(clauses);
			clauses.clear();
			clauses.addAll(hs);
		}*/
		
		return new SatFNC(litteral - 1, clauses.size(), clauses);
	}
	
	public static void main(String [] args) {
		BinCSP csp = BinCSP.importFromXML("test.xml");
		SatFNC sat = directEncoding(csp);
		System.out.println(sat.toString());
	}
}
