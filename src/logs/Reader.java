package logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

	public static ArrayList<String> read (File f){
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static boolean isComment(String [] splittedLine) {
		return (splittedLine[0].equals("c"));
	}
	
	public static boolean isData(String [] splittedLine) {
		return (splittedLine[0].equals("d"));
	}
	
	public static boolean isInfo(String [] splittedLine) {
		return (splittedLine[0].equals("i"));
	}
	
	public static boolean isResult(String [] splittedLine) {
		return (splittedLine[0].equals("r"));
	}
	
	public static boolean isIntro(String [] splittedLine) {
		return (splittedLine[0].equals("b"));
	}
	
	@SuppressWarnings("unused")
	public static ArrayList<Data> getData(File f){
		
		ArrayList<String> lines = read(f);
		String filename;

		int nbSatSolvers, nb3ColSolvers, nbCSPSolvers;
		ArrayList<Data> datas = new ArrayList<Data>();
		
		int i = 0;
		
		while (i < lines.size()) {
			String [] splittedLine = lines.get(i).split(" ");
			
			String competitionName = null, benchmarkName = null, reductionName = null, satisfiability = null, initialFile = null, finalFile = null;
			
			int nbInitialVar = 0, nbInitialClauses = 0, nbInitialUnitaryClauses = 0, nbInitialBinaryClauses = 0, 
			    nbInitialTernaryClauses = 0, nbInitialLongClauses = 0;
			
			int nbFinalVar = 0, nbFinalClauses = 0, nbFinalUnitaryClauses = 0, nbFinalBinaryClauses = 0, 
		        nbFinalTernaryClauses = 0, nbFinalLongClauses = 0;
			
			ArrayList<ResultSolver> results = new ArrayList<ResultSolver>();
			
			if (isIntro(splittedLine)) {
				filename      = splittedLine[1];
				nbSatSolvers  = Integer.parseInt(splittedLine[2]);
				nb3ColSolvers = Integer.parseInt(splittedLine[3]);
				nbCSPSolvers  = Integer.parseInt(splittedLine[4]);
				i += 1;
				splittedLine = lines.get(i).split(" ");
			}
				
			if (isData(splittedLine)) {
				competitionName = splittedLine[1];
				benchmarkName   = splittedLine[2];
				reductionName   = splittedLine[3];
				satisfiability  = splittedLine[4];
				initialFile     = splittedLine[5];
				finalFile       = splittedLine[6];
				i += 1;
				splittedLine = lines.get(i).split(" ");
			}
			
			if (isInfo(splittedLine)) {
				try {
					nbInitialVar            = Integer.parseInt(splittedLine[1]);
					nbInitialClauses        = Integer.parseInt(splittedLine[2]);
					nbInitialUnitaryClauses = Integer.parseInt(splittedLine[3]);
					nbInitialBinaryClauses  = Integer.parseInt(splittedLine[4]);
					nbInitialTernaryClauses = Integer.parseInt(splittedLine[5]);
					nbInitialLongClauses    = Integer.parseInt(splittedLine[6]);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("ERROR : line " + (i+1));
				}
				
				i += 1;
				splittedLine = lines.get(i).split(" ");
						
				if (isInfo(splittedLine)) {
					nbFinalVar            = Integer.parseInt(splittedLine[1]);
					nbFinalClauses        = Integer.parseInt(splittedLine[2]);
					nbFinalUnitaryClauses = Integer.parseInt(splittedLine[3]);
					nbFinalBinaryClauses  = Integer.parseInt(splittedLine[4]);
					nbFinalTernaryClauses = Integer.parseInt(splittedLine[5]);
					nbFinalLongClauses    = Integer.parseInt(splittedLine[6]);
						
					i += 1;
					splittedLine = lines.get(i).split(" ");
						
					while (isResult(splittedLine)) {
						
						String initialSolverName = "";
						String finalSolverName = "";
						double initialTime = -1, finalTime = -1;
						
						try {
							try {
								initialSolverName  = splittedLine[1];
								finalSolverName = splittedLine[2];
								initialTime = Double.parseDouble(splittedLine[3]);
								finalTime   = Double.parseDouble(splittedLine[4]);
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("ERROR : line " + (i+1));
							}
						} catch (NumberFormatException e) {
							System.out.println("Error : line " + (i+1));
						}
						ResultSolver result = new ResultSolver(initialSolverName, finalSolverName, initialTime, finalTime);
						
						datas.add(new Data(competitionName, reductionName, satisfiability, initialFile,
							      finalFile, nbInitialVar, nbInitialClauses, nbInitialUnitaryClauses,
								  nbInitialBinaryClauses, nbInitialTernaryClauses, nbInitialLongClauses,
								  nbFinalVar, nbFinalClauses, nbFinalUnitaryClauses, nbFinalBinaryClauses,
								  nbFinalTernaryClauses, nbFinalLongClauses, result, benchmarkName));
						
						i += 1;
						if (i >= lines.size()) break;
						splittedLine = lines.get(i).split(" ");
					}	
				}
			}
			
			
	
		}
		return datas;
	}
}
