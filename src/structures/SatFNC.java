package structures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class SatFNC {

	private ArrayList<Integer> litterals;

	private int nbLitterals;
	private int nbClauses;

	private ArrayList<String> clauses = new ArrayList<String>();

	public SatFNC(int nbLitterals, int nbClauses, ArrayList<String> clauses) {
		this.nbLitterals = nbLitterals;
		this.nbClauses = nbClauses;
		this.clauses = clauses;
		litterals = litteralsList(clauses);
	}

	public SatFNC(int nbLitterals, int nbClauses, ArrayList<String> clauses, ArrayList<Integer> litterals) {
		this.nbLitterals = nbLitterals;
		this.nbClauses = nbClauses;
		this.clauses = clauses;
		this.litterals = litterals;
	}

	public int getNbLitterals() {
		return nbLitterals;
	}

	public int getNbClauses() {
		return nbClauses;
	}

	public ArrayList<Integer> getLitterals() {
		return litterals;
	}

	public ArrayList<String> getClauses() {
		return clauses;
	}

	public void addClause(String clause) {
		clauses.add(clause);
	}

	
	public static ArrayList<Integer> litteralsList(ArrayList<String> clauses) {
		ArrayList<Integer> litterals = new ArrayList<Integer>();
		
		for (String clause : clauses) {
			String [] splittedClause = clause.split(" ");
			if (!splittedClause[0].equals("c")) {
				for (int i = 0 ; i < splittedClause.length-1 ; i++) {
					int litteral = Integer.parseInt(splittedClause[i]);
				
					if (litteral > 0)
						litterals.add(Integer.parseInt(splittedClause[i]));
					else 
						litterals.add(-Integer.parseInt(splittedClause[i]));
				}
			}
		}
		
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(litterals);
		litterals.clear();
		litterals.addAll(hs);
		
		Collections.sort(litterals, new Comparator<Integer>() {
	        public int compare(Integer a, Integer b)
	        {
	            return  a.compareTo(b);
	        }
	    });
		
		return litterals;
	}

	public static SatFNC importFromDimacs(File f) {
		int nbLitterals = 0;
		int nbClauses = 0;
		ArrayList<String> clauses = new ArrayList<String>();
		ArrayList<Integer> litterals = new ArrayList<Integer>();
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine;
			boolean inClauses = false;
			while ((currentLine = reader.readLine()) != null) {
				String[] splittedLine = currentLine.split(" ");
				if (!splittedLine[0].equals("c")) {
					if (!inClauses && splittedLine[0].equals("p") && splittedLine[1].equals("cnf")) {
						try {
							nbLitterals = Integer.parseInt(splittedLine[2]);
							nbClauses = Integer.parseInt(splittedLine[3]);
							// sat = new SatFNC(nbLitterals, nbClauses);
							inClauses = true;
						} catch (ArrayIndexOutOfBoundsException e) {
							System.out.println("Invalid format");
							return null;
						} catch (NumberFormatException e) {
							System.out.println("Invalid format");
							return null;
						}
					} else if (inClauses) {
						clauses.add(currentLine);
						for (int i = 0 ; i < splittedLine.length-1 ; i++) {
							//experimental !
							if (Integer.parseInt(splittedLine[i]) < 0)
								litterals.add(-Integer.parseInt(splittedLine[i]));
							else
								litterals.add(Integer.parseInt(splittedLine[i])); //que ca avant		
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error while load file.");
		} catch (IOException e) {
			System.out.println("Error while load file");
		}
		
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(litterals);
		litterals.clear();
		litterals.addAll(hs);
		
		Collections.sort(litterals, new Comparator<Integer>() {
	        public int compare(Integer a, Integer b)
	        {
	            return  a.compareTo(b);
	        }
	    });
		
		return new SatFNC(nbLitterals, nbClauses, clauses, /*litteralsList(clauses)*/ litterals);
	}

	public void exportToDimacs(File f) {
		
		BufferedWriter writer = null;
		
		try {
			/*
			FileWriter writer = new FileWriter(f);
			writer.write(toString());
			writer.flush();
			writer.close();
			*/
			writer = new BufferedWriter(new FileWriter(f));
			//writer.write(toString());
			
			writer.write("p cnf " + nbLitterals + " " + nbClauses + "\n");
			
			for (String clause : clauses) {
				writer.write(clause + "\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {

				if (writer != null)
					writer.close();

				if (writer != null)
					writer.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String toString() {
		String sat = new String();
		sat += "p cnf " + nbLitterals + " " + nbClauses + "\n";
		for (String clause : clauses) {
			sat += clause + "\n";
		}
		return sat;
	}

}
