package logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Lister {

	public enum Filter {
		COMPETITION, BENCHMARK, SAT, I_FILE, F_FILE, REDUCTION, I_VARS, F_VARS,
		I_CLAUSES, I_UCLAUSES, I_BCLAUSES, I_TCLAUSES, I_LCLAUSES, I_RATIO_CX, I_TIME, I_RATIO, F_RATIO_CX,
		F_CLAUSES, F_UCLAUSES, F_BCLAUSES, F_TCLAUSES, F_LCLAUSES, F_RATIO, F_TIME, 
		RATIO_TIME, SOLVER_NAME;
	}
	
	public enum Sorter {
		COMPETITION, BENCHMARK, SAT, I_FILE, F_FILE, REDUCTION, I_VARS, F_VARS,
		I_CLAUSES, I_UCLAUSES, I_BCLAUSES, I_TCLAUSES, I_LCLAUSES, I_RATIO_CX, I_TIME, I_RATIO, F_RATIO_CX,
		F_CLAUSES, F_UCLAUSES, F_BCLAUSES, F_TCLAUSES, F_LCLAUSES, F_RATIO, F_TIME,
		RATIO_TIME, SOLVER_NAME;
	}

	public enum SortType {
		ASCENDANT, DESCENDANT;
	}
	
	/*
	 * Apply one filter to a Data List
	 */
	@SuppressWarnings("incomplete-switch")
	public static ArrayList<Data> filter (ArrayList<Data> oldDatas, Filter filter, 
			                              String minValue, String maxValue){
		
		ArrayList<Data> newDatas = new ArrayList<Data>();
		
		switch (filter) {
			/*
			 * maxValue ignored
			 */
			case COMPETITION :
				for (Data data : oldDatas) {
					if (data.getCompetitionYear().equals(minValue))
						newDatas.add(data);
				}
				return newDatas;
		
			/*
			 * maxValue ignored
			 */
			case BENCHMARK : 
				for (Data data : oldDatas) {
					if (data.getBenchmarkName().equals(minValue))
						newDatas.add(data);
				}
				return newDatas;
				
			/*
			 * maxValue ignored
			 */
			case SAT : 
				for (Data data : oldDatas) {
					if (data.getSatisfiability().equals(minValue))
						newDatas.add(data);
				}
				return newDatas;
			
			/*
			 * Filter initial problem
			 */
			case I_CLAUSES : 
				for (Data data : oldDatas) {
					if (data.getInitialNbClauses() >= Integer.parseInt(minValue) &&
						data.getInitialNbClauses() <= Integer.parseInt(maxValue)) {
						newDatas.add(data);
					}
				}
				return newDatas;
				
			case I_BCLAUSES : 
				for (Data data : oldDatas) {
					if (data.getInitialNbBinaryClauses() >= Integer.parseInt(minValue) &&
						data.getInitialNbBinaryClauses() <= Integer.parseInt(maxValue)) {
						newDatas.add(data);
					}
				}
				return newDatas;
				
			case I_TCLAUSES : 
				for (Data data : oldDatas) {
					if (data.getInitialNbTernaryClauses() >= Integer.parseInt(minValue) &&
						data.getInitialNbTernaryClauses() <= Integer.parseInt(maxValue)) {
						newDatas.add(data);
					}
				}
				return newDatas;
				
			case I_LCLAUSES : 
				for (Data data : oldDatas) {
					if (data.getInitialNbLongClauses() >= Integer.parseInt(minValue) &&
						data.getInitialNbLongClauses() <= Integer.parseInt(maxValue)) {
						newDatas.add(data);
					}
				}
				return newDatas;
				
			case I_RATIO_CX : 
				for (Data data : oldDatas) {
					if (data.getInitialRatio() >= Double.parseDouble(minValue) &&
						data.getInitialRatio() <= Double.parseDouble(maxValue)) {
						newDatas.add(data);
					}
				}
				return newDatas;
		
			case I_TIME : 
				for (Data data : oldDatas) {
					if (data.getResult().getInitialTime() >= Double.parseDouble(minValue) &&
						data.getResult().getInitialTime() <= Double.parseDouble(maxValue)) {
						newDatas.add(data);
					}
				}
				return newDatas;
			
				
				/*
				 * Filter final problem
				 */
				case F_CLAUSES : 
					for (Data data : oldDatas) {
						if (data.getFinalNbClauses() >= Integer.parseInt(minValue) &&
							data.getFinalNbClauses() <= Integer.parseInt(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_BCLAUSES : 
					for (Data data : oldDatas) {
						if (data.getFinalNbBinaryClauses() >= Integer.parseInt(minValue) &&
							data.getFinalNbBinaryClauses() <= Integer.parseInt(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_TCLAUSES : 
					for (Data data : oldDatas) {
						if (data.getFinalNbTernaryClauses() >= Integer.parseInt(minValue) &&
							data.getFinalNbTernaryClauses() <= Integer.parseInt(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_LCLAUSES : 
					for (Data data : oldDatas) {
						if (data.getFinalNbLongClauses() >= Integer.parseInt(minValue) &&
							data.getFinalNbLongClauses() <= Integer.parseInt(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_RATIO : 
					for (Data data : oldDatas) {
						if (data.getFinalRatio() >= Double.parseDouble(minValue) &&
							data.getFinalRatio() <= Double.parseDouble(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
			
				case F_TIME : 
					for (Data data : oldDatas) {
						if (data.getResult().getFinalTime() >= Double.parseDouble(minValue) &&
							data.getResult().getFinalTime() <= Double.parseDouble(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
				
				case RATIO_TIME : 
					for (Data data : oldDatas) {
						if (data.getResult().getRatio() >= Double.parseDouble(minValue) &&
							data.getResult().getRatio() <= Double.parseDouble(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
				/*
				 * maxValue is ignored
				 */
				case SOLVER_NAME : 
					for (Data data : oldDatas) {
						if (data.getResult().getSolverName().equals(minValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case I_FILE : 
					for (Data data : oldDatas) {
						if (data.getInitialFile().equals(minValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_FILE : 
					for (Data data : oldDatas) {
						if (data.getFinalFile().equals(minValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case REDUCTION : 
					for (Data data : oldDatas) {
						if (data.getReductionName().equals(minValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case I_VARS : 
					for (Data data : oldDatas) {
						if (data.getInitialNbVariables() >= Integer.parseInt(minValue) &&
							data.getInitialNbVariables() <= Integer.parseInt(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_VARS : 
					for (Data data : oldDatas) {
						if (data.getFinalNbVariables() >= Integer.parseInt(minValue) &&
							data.getFinalNbVariables() <= Integer.parseInt(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case I_RATIO :
					for (Data data : oldDatas) {
						if (data.getInitialRatio() >= Double.parseDouble(minValue) &&
							data.getInitialRatio() <= Double.parseDouble(maxValue)) {
							newDatas.add(data);
						}
					}
					return newDatas;
					
				case F_RATIO_CX : 
					for (Data data : oldDatas) {
						if (data.getFinalRatio() >= Double.parseDouble(minValue) &&
							data.getFinalRatio() <= Double.parseDouble(maxValue)) {
							newDatas.add(data);
						}
							
					}
					return newDatas;
		}
		return newDatas;
	}
	
	/*
	 * Sort a list
	 */
	public static ArrayList<Data> sort(ArrayList<Data> oldDatas, Sorter sorter, SortType type){
		
		ArrayList<Data> newDatas = oldDatas;
		
		switch(sorter) {
			
			case COMPETITION : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return o1.getCompetitionYear().compareTo(o2.getCompetitionYear());
						else
							return -o1.getCompetitionYear().compareTo(o2.getCompetitionYear());
					}
					
				});
				return newDatas;
				
			case BENCHMARK : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return o1.getBenchmarkName().compareTo(o2.getBenchmarkName());
						else
							return -o1.getBenchmarkName().compareTo(o2.getBenchmarkName());
					}
			
				});
				return newDatas;
				
			case SAT : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return o1.getSatisfiability().compareTo(o2.getSatisfiability());
						else
							return -o1.getSatisfiability().compareTo(o2.getSatisfiability());
					}
			
				});
				return newDatas;
		
			/*
			 * Sort on initial problem values
			 */
			case I_CLAUSES : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Integer(o1.getInitialNbClauses()).compareTo(new Integer(o2.getInitialNbClauses()));
						else
							return -new Integer(o1.getInitialNbClauses()).compareTo(new Integer(o2.getInitialNbClauses()));
					}
					
				});
				return newDatas;
				
			case I_UCLAUSES : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Integer(o1.getInitialNbUnaryClauses()).compareTo(new Integer(o2.getInitialNbUnaryClauses()));
						else
							return -new Integer(o1.getInitialNbUnaryClauses()).compareTo(new Integer(o2.getInitialNbUnaryClauses()));
					}
					
				});
				return newDatas;
				
			case I_BCLAUSES : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Integer(o1.getInitialNbBinaryClauses()).compareTo(new Integer(o2.getInitialNbBinaryClauses()));
						else
							return -new Integer(o1.getInitialNbBinaryClauses()).compareTo(new Integer(o2.getInitialNbBinaryClauses()));
					}
					
				});
				return newDatas;
				
			case I_TCLAUSES : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Integer(o1.getInitialNbTernaryClauses()).compareTo(new Integer(o2.getInitialNbTernaryClauses()));
						else 
							return -new Integer(o1.getInitialNbTernaryClauses()).compareTo(new Integer(o2.getInitialNbTernaryClauses()));
					}
					
				});
				return newDatas;
				
			case I_LCLAUSES : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Integer(o1.getInitialNbLongClauses()).compareTo(new Integer(o2.getInitialNbLongClauses()));
						else 
							return -new Integer(o1.getInitialNbLongClauses()).compareTo(new Integer(o2.getInitialNbLongClauses()));
					}
					
				});
				return newDatas;
				
			case I_RATIO_CX : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Double(o1.getInitialRatio()).compareTo(new Double(o2.getInitialRatio()));
						else
							return -new Double(o1.getInitialRatio()).compareTo(new Double(o2.getInitialRatio()));
					}
				});
				
			case I_TIME : 
				Collections.sort(newDatas, new Comparator<Data>() {
					@Override
					public int compare(Data o1, Data o2) {
						if (type == SortType.ASCENDANT)
							return new Double(o1.getResult().getInitialTime()).compareTo(new Double(o2.getResult().getInitialTime()));
						else
							return -new Double(o1.getResult().getInitialTime()).compareTo(new Double(o2.getResult().getInitialTime()));
					}
				});
				
				/*
				 * Sort on initial problem values
				 */
				case F_CLAUSES : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getFinalNbClauses()).compareTo(new Integer(o2.getFinalNbClauses()));
							else 
								return -new Integer(o1.getFinalNbClauses()).compareTo(new Integer(o2.getFinalNbClauses()));
						}
						
					});
					return newDatas;
					
				case F_UCLAUSES : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getFinalNbUnaryClauses()).compareTo(new Integer(o2.getFinalNbUnaryClauses()));
							else 
								return -new Integer(o1.getFinalNbUnaryClauses()).compareTo(new Integer(o2.getFinalNbUnaryClauses()));
						}
						
					});
					return newDatas;
					
				case F_BCLAUSES : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getFinalNbBinaryClauses()).compareTo(new Integer(o2.getFinalNbBinaryClauses()));
							else 
								return -new Integer(o1.getFinalNbBinaryClauses()).compareTo(new Integer(o2.getFinalNbBinaryClauses()));
						}
						
					});
					return newDatas;
					
				case F_TCLAUSES : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getFinalNbTernaryClauses()).compareTo(new Integer(o2.getFinalNbTernaryClauses()));
							else
								return -new Integer(o1.getFinalNbTernaryClauses()).compareTo(new Integer(o2.getFinalNbTernaryClauses()));
						}
						
					});
					return newDatas;
					
				case F_LCLAUSES : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getFinalNbLongClauses()).compareTo(new Integer(o2.getFinalNbLongClauses()));
							else 
								return -new Integer(o1.getFinalNbLongClauses()).compareTo(new Integer(o2.getFinalNbLongClauses()));
						}
						
					});
					return newDatas;
					
				case F_RATIO : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Double(o1.getFinalRatio()).compareTo(new Double(o2.getFinalRatio()));
							else
								return -new Double(o1.getFinalRatio()).compareTo(new Double(o2.getFinalRatio()));								
						}
					});
					return newDatas;
					
				case F_TIME : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							return new Double(o1.getResult().getFinalTime()).compareTo(new Double(o2.getResult().getFinalTime()));
						}
					});
					return newDatas;
				
				case RATIO_TIME : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Double(o1.getResult().getRatio()).compareTo(new Double(o2.getResult().getRatio()));
							else 
								return -new Double(o1.getResult().getRatio()).compareTo(new Double(o2.getResult().getRatio()));
						}
						
					});
					return newDatas;
					
				case SOLVER_NAME : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return o1.getResult().getSolverName().compareTo(o2.getResult().getSolverName());
							else 
								return -o1.getResult().getSolverName().compareTo(o2.getResult().getSolverName());
						}
						
					});
					return newDatas;
					
				case I_FILE : 
					Collections.sort(newDatas, new Comparator<Data>(){
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return o1.getInitialFile().compareTo(o2.getInitialFile());
							else
								return -o1.getInitialFile().compareTo(o2.getInitialFile());
						}
					});
					return newDatas;
					
				case F_FILE : 
					Collections.sort(newDatas, new Comparator<Data>(){
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return o1.getFinalFile().compareTo(o2.getFinalFile());
							else
								return -o1.getFinalFile().compareTo(o2.getFinalFile());
						}
					});
					return newDatas;
					
				case REDUCTION : 
					Collections.sort(newDatas, new Comparator<Data>(){
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return o1.getReductionName().compareTo(o2.getReductionName());
							else
								return -o1.getReductionName().compareTo(o2.getReductionName());
						}
					});
					return newDatas;
					
				case I_VARS : 
					Collections.sort(newDatas, new Comparator<Data>(){
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getInitialNbVariables()).compareTo(new Integer(o2.getInitialNbVariables()));
							else
								return -new Integer(o1.getInitialNbVariables()).compareTo(new Integer(o2.getInitialNbVariables()));
						}
					});
					return newDatas;
					
				case F_VARS : 
					Collections.sort(newDatas, new Comparator<Data>(){
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Integer(o1.getFinalNbVariables()).compareTo(new Integer(o2.getFinalNbVariables()));
							else
								return -new Integer(o1.getFinalNbVariables()).compareTo(new Integer(o2.getFinalNbVariables()));
						}
					});
					return newDatas;
					
				case I_RATIO : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Double(o1.getResult().getRatio()).compareTo(new Double(o2.getResult().getRatio()));
							else 
								return -new Double(o1.getResult().getRatio()).compareTo(new Double(o2.getResult().getRatio()));
						}
						
					});
					return newDatas;
					
				case F_RATIO_CX : 
					Collections.sort(newDatas, new Comparator<Data>() {
						@Override
						public int compare(Data o1, Data o2) {
							if (type == SortType.ASCENDANT)
								return new Double(o1.getFinalRatio()).compareTo(new Double(o2.getFinalRatio()));
							else
								return -new Double(o1.getFinalRatio()).compareTo(new Double(o2.getFinalRatio()));
						}
					});
					return newDatas;
		}
		return newDatas;
	}
}
