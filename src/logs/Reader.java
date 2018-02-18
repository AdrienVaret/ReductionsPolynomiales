package logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

	public static String getRef(String [] strings) {
		return strings[0];
	}
	
	public static ArrayList<String> read(File f){
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {
				lines.add(currentLine);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
	
	public static String getName(String [] strings) {
		String name = new String();
		
		for (int i = 1 ; i < strings.length ; i++)
			name += strings[i] + " ";
		
		return name;
	}
	
	public static FileInstance.State getState(String satisfiability){
		if (satisfiability.equals("SAT")) return FileInstance.State.SAT;
		else if (satisfiability.equals("UNSAT")) return FileInstance.State.UNSAT;
		return FileInstance.State.UNSAT;
	}
	
	public static ArrayList<Year> getData (File f){
		ArrayList<String> lines = read(f);
		ArrayList<Year> data = new ArrayList<Year>();
		
		int i = 0;
		String [] splittedLine;
		
		while (i < lines.size()) {
			Year year = null;
			splittedLine = lines.get(i).split(" ");
			
			if (getRef(splittedLine).equals("#YEAR")) {
				year = new Year(getName(splittedLine));
				i += 1;
				splittedLine = lines.get(i).split(" ");
				Benchmark benchmark = null;
				
				if (getRef(splittedLine).equals("#BENCHMARK")) {
					benchmark = new Benchmark(getName(splittedLine));
					i += 1;
					splittedLine = lines.get(i).split(" ");
					Reduction reduction = null;
					
					if (getRef(splittedLine).equals("#REDUCTION")) {
						String reductionName = getName(splittedLine);
						reduction = new Reduction(reductionName);
						i += 1;
						splittedLine = lines.get(i).split(" ");
						FileInstance fileInstance = null;
						if (getRef(splittedLine).equals("#FILE")) {
							Instance initialInstance = null;
							Instance finalInstance = null;
							
							String initialName    = splittedLine[1];
							String finalName      = splittedLine[2];
							String satisfiability = splittedLine[3];
							
							i += 1;
							splittedLine = lines.get(i).split(" ");
							if (getRef(splittedLine).equals("#INITIAL")){
							
								if (reductionName.equals("SAT TO 3SAT ")) {
									int nbVariables = Integer.parseInt(splittedLine[1]);
									int nbClauses = Integer.parseInt(splittedLine[2]);
									int nbUnitaryClauses = Integer.parseInt(splittedLine[3]);
									int nbBinaryClauses = Integer.parseInt(splittedLine[4]);
									int nbTernaryClauses = Integer.parseInt(splittedLine[5]);
									int nbLongClauses = Integer.parseInt(splittedLine[6]);
									initialInstance = new InstanceSAT(initialName, nbVariables, nbClauses, nbUnitaryClauses,
											                          nbBinaryClauses, nbTernaryClauses, nbLongClauses);
									
									i += 1;
									splittedLine = lines.get(i).split(" ");
									
									if (getRef(splittedLine).equals("#FINAL")){
										nbVariables = Integer.parseInt(splittedLine[1]);
										nbClauses = Integer.parseInt(splittedLine[2]);
										nbUnitaryClauses = Integer.parseInt(splittedLine[3]);
										nbBinaryClauses = Integer.parseInt(splittedLine[4]);
										nbTernaryClauses = Integer.parseInt(splittedLine[5]);
										nbLongClauses = Integer.parseInt(splittedLine[6]);
										finalInstance = new InstanceSAT(initialName, nbVariables, nbClauses, nbUnitaryClauses,
												                          nbBinaryClauses, nbTernaryClauses, nbLongClauses);
										
										i += 1;
										splittedLine = lines.get(i).split(" ");
									}
								}
								
								ArrayList<Result> results = new ArrayList<Result>();
								while (getRef(splittedLine).equals("#RESULT")) {
									String solver = splittedLine[1];
									double initialTime = Double.parseDouble(splittedLine[2]);
									double finalTime = Double.parseDouble(splittedLine[3]);
									results.add(new Result(solver, initialTime, finalTime));
									i += 1;
									if ( i < lines.size()) {
										splittedLine = lines.get(i).split(" ");
									} else {
										break;
									}
								}
								
								fileInstance = new FileInstance(getState(satisfiability), initialInstance, finalInstance, results);
							}
							reduction.getInstances().add(fileInstance);
						}
						benchmark.getReductions().add(reduction);
					}
					
					year.getBenchmarks().add(benchmark);
				}
				data.add(year);
			}
		}	
		return data;
	}
}
