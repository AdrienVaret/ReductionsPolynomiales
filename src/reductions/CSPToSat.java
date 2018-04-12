package reductions;

import java.util.ArrayList;

import structures.BinCSP;
import structures.Constraint;
import structures.Couple;
import structures.Domain;
import structures.SatFNC;
import structures.Variable;

public class CSPToSat {

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
			ArrayList<String> v1Values = new ArrayList<String>();
			ArrayList<String> v2Values = new ArrayList<String>();
			for (Couple c1 : constraint.getRelation().getCouples()) {
				for (Couple c2 : constraint.getRelation().getCouples()) {
					if (!c1.equals(c2)) {
						
					}
				}
			}
		}
		
		return null;
	}
	
	public static SatFNC directEncoding(BinCSP csp) {
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
		
		/* encoding constraints ONLY ONE DOMAIN */
		for (Constraint constraint : csp.getConstraints()) {
			Variable v1 = constraint.getV1();
			Variable v2 = constraint.getV2();
			int v1IndexBase = Integer.parseInt(v1.getName().substring(1));
			int v2IndexBase = Integer.parseInt(v2.getName().substring(1)); 
			
			for (Couple couple : constraint.getRelation().getCouples()) {
				int v1Index = v1IndexBase * v1.getDomain().getSize() + Integer.parseInt(couple.getValue1()) + 1;
				int v2Index = v2IndexBase * v2.getDomain().getSize() + Integer.parseInt(couple.getValue2()) + 1;
				clauses.add(-v1Index + " " + -v2Index + " 0");
			}
		}
		
		return new SatFNC(litteral - 1, clauses.size(), clauses);
	}
}
