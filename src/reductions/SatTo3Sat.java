package reductions;

import java.util.ArrayList;

import structures.SatFNC;

public class SatTo3Sat {
	
	public static SatFNC convert(SatFNC sat) {
		
		int nbLitterals = sat.getNbLitterals();
		ArrayList<String> clauses = new ArrayList<String>();
		
		for (String clause : sat.getClauses()) {
			String [] splittedClause = clause.split(" ");
			int nbLitteralsClause = splittedClause.length - 1; //THE END 0
			// a b c => a b c
			if (nbLitteralsClause == 3) {
				clauses.add(clause);
			}
			// a b => a b a
			else if (nbLitteralsClause < 3 && nbLitteralsClause > 0) {
				String newClause = new String();
				for (int i = 0 ; i < 3 ; i++) {
					if (i < nbLitteralsClause) {
						newClause += splittedClause[i] + " ";
					} else {
						newClause += splittedClause[0] + " ";
					}
				}
				newClause += " 0";
				clauses.add(newClause);
			}
			/*
			 * a b c d e f => a b g
			 *                !g c f
			 *                !f d h
			 *                !h e f
			 */
			else {	
				int i = 0;
				int newLitteral = sat.getNbLitterals() + 1;
				while (i < nbLitteralsClause) {
					//generate first clause
					if (i == 0) {
						String newClause = new String(splittedClause[0] + " " + splittedClause[1] + " ");
						newClause += newLitteral + " 0";
						clauses.add(newClause);
						nbLitterals += 1;
						i += 2;
					}
					//generate last clause
					if (i == (nbLitteralsClause-2)) {
						String newClause = "-"+ newLitteral + " " + splittedClause[i] + " " + splittedClause[i+1] + " 0";
						clauses.add(newClause);
						i += 2;
					}
					//general case
					else {
						String newClause = "-" + newLitteral + " " + splittedClause[i] + " ";
						newLitteral += 1;
						newClause += newLitteral + " 0";
						clauses.add(newClause);
						nbLitterals += 1;
						i += 1;			
					}
				}
			}
		}
		
		return new SatFNC(nbLitterals, clauses.size(), clauses);
	}
	
}
