package structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import reductions.SatTo3Sat;

public class SatFNC {

	private ArrayList<Integer> litterals;

	private int nbLitterals;
	private int nbClauses;

	private ArrayList<String> clauses = new ArrayList<String>();

	public SatFNC(int nbLitterals, int nbClauses, ArrayList<String> clauses) {
		this.nbLitterals = nbLitterals;
		this.nbClauses = nbClauses;
		this.clauses = clauses;
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

	@SuppressWarnings("unlikely-arg-type")
	public static ArrayList<Integer> litteralsList(ArrayList<String> clauses) {
		ArrayList<Integer> litterals = new ArrayList<Integer>();
		for (String clause : clauses) {
			String [] splittedLine = clause.split(" ");
			for (int i = 0 ; i < splittedLine.length - 1 ; i++) {
				String litteral = splittedLine[i];
				if (!litterals.contains(Integer.parseInt(litteral)) && !litterals.contains(-Integer.parseInt(litteral))) {
					if (Integer.parseInt(litteral) < 0)
						litterals.add(-Integer.parseInt(litteral));
					else 
						litterals.add(Integer.parseInt(litteral));
				}
			}
		}
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
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error while load file.");
		} catch (IOException e) {
			System.out.println("Error while load file");
		}
		return new SatFNC(nbLitterals, nbClauses, clauses, litteralsList(clauses));
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

	public String toString() {
		String sat = new String();
		sat += "p fnc " + nbLitterals + " " + nbClauses + "\n";
		for (String clause : clauses) {
			sat += clause + "\n";
		}
		return sat;
	}

	public static void main(String[] args) {
		SatFNC sat = importFromDimacs(new File("formule.fnc"));
		// System.out.println(sat.toString());
		// sat.exportDimacs(new File("export.fnc"));
		SatFNC Tsat = SatTo3Sat.convert(sat);
		System.out.print(Tsat.toString());
		Tsat.exportToDimacs(new File("export.fnc"));
	}
}
