package cnf_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CNFParser {

	public static void parse(File f) {
		
		int nbVariables = 0, nbClauses = 0, nbUnaryClauses = 0, nbBinaryClauses = 0,nbTernaryClauses = 0, nbLongClauses = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				String [] splittedLine = line.split(" ");
				if (!splittedLine[0].equals("c")) {
					if (splittedLine[0].equals("p")) {
						nbVariables = Integer.parseInt(splittedLine[2]);
						nbClauses   = Integer.parseInt(splittedLine[3]); 
					} else {
						if (splittedLine.length - 1 == 1) nbUnaryClauses ++;
						if (splittedLine.length - 1 == 2) nbBinaryClauses ++;
						if (splittedLine.length - 1 == 3) nbTernaryClauses ++;
						else nbLongClauses ++;
					}
				}
			}
			reader.close();
			double ratio = (double)nbClauses / (double)nbVariables;
			System.out.println("# " + f.getAbsolutePath());
			System.out.println("# " + "nbClauses : "        + nbClauses);
			System.out.println("# " + "nbVariables : "      + nbVariables);
			System.out.println("# " + "nbUnaryClauses : "   + nbUnaryClauses);
			System.out.println("# " + "nbBinaryClauses : "  + nbBinaryClauses);
			System.out.println("# " + "nbTernaryClauses : " + nbTernaryClauses);
			System.out.println("# " + "nbLongClauses : "    + nbLongClauses);
			System.out.println("# " + "ratio : "            + ratio);
			System.out.println("###");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main (String [] args) {
		
		System.out.println("###");
		System.out.println("# CNF Parser");
		System.out.println("# @author : Adrien VARET");
		System.out.println("###");
				
		
		if (args.length != 1) {
			System.out.println("#");
			System.out.println("# USAGE : java -jar Parser file.cnf");
			System.out.println("###");
		} else {
			File f = new File(args[0]);
			parse(f);
		}
	}
}
