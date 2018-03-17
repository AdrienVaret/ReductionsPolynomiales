package logs;

public class Data {

	private String competitionYear;
	private String reductionName;
	private String satisfiability;
	private String initialFile;
	private String finalFile;
	private String benchmarkName;
	
	private int initialNbVariables;
	private int initialNbClauses;
	private int initialNbUnaryClauses;
	private int initialNbBinaryClauses;
	private int initialNbTernaryClauses;
	private int initialNbLongClauses;
	private double initialRatio; // |C|/|x| 
	
	private int finalNbVariables;
	private int finalNbClauses;
	private int finalNbUnaryClauses;
	private int finalNbBinaryClauses;
	private int finalNbTernaryClauses;
	private int finalNbLongClauses;
	private double finalRatio; // |C|/|x| 
	
	private ResultSolver result;
	
	public Data(String competitionYear, String reductionName, String satisfiability, String initialFile,
			String finalFile, int initialNbVariables, int initialNbClauses, int initialNbUnaryClauses,
			int initialNbBinaryClauses, int initialNbTernaryClauses, int initialNbLongClauses,
			int finalNbVariables, int finalNbClauses, int finalNbUnaryClauses, int finalNbBinaryClauses,
			int finalNbTernaryClauses, int finalNbLongClauses, ResultSolver result, String benchmarkName) {
		super();
		this.competitionYear = competitionYear;
		this.reductionName = reductionName;
		this.satisfiability = satisfiability;
		this.initialFile = initialFile;
		this.finalFile = finalFile;
		this.initialNbVariables = initialNbVariables;
		this.initialNbClauses = initialNbClauses;
		this.initialNbUnaryClauses = initialNbUnaryClauses;
		this.initialNbBinaryClauses = initialNbBinaryClauses;
		this.initialNbTernaryClauses = initialNbTernaryClauses;
		this.initialNbLongClauses = initialNbLongClauses;
		this.initialRatio = (double)initialNbClauses / (double)initialNbVariables;
		this.finalNbVariables = finalNbVariables;
		this.finalNbClauses = finalNbClauses;
		this.finalNbUnaryClauses = finalNbUnaryClauses;
		this.finalNbBinaryClauses = finalNbBinaryClauses;
		this.finalNbTernaryClauses = finalNbTernaryClauses;
		this.finalNbLongClauses = finalNbLongClauses;
		this.finalRatio = (double)finalNbClauses / (double)finalNbVariables;
		this.result = result;
		this.benchmarkName = benchmarkName;
	}

	public String getCompetitionYear() {
		return competitionYear;
	}

	public String getReductionName() {
		return reductionName;
	}

	public String getSatisfiability() {
		return satisfiability;
	}

	public String getInitialFile() {
		return initialFile;
	}

	public String getFinalFile() {
		return finalFile;
	}

	public int getInitialNbVariables() {
		return initialNbVariables;
	}

	public int getInitialNbClauses() {
		return initialNbClauses;
	}

	public int getInitialNbUnaryClauses() {
		return initialNbUnaryClauses;
	}

	public int getInitialNbBinaryClauses() {
		return initialNbBinaryClauses;
	}

	public int getInitialNbTernaryClauses() {
		return initialNbTernaryClauses;
	}

	public int getInitialNbLongClauses() {
		return initialNbLongClauses;
	}

	public double getInitialRatio() {
		return initialRatio;
	}

	public int getFinalNbVariables() {
		return finalNbVariables;
	}

	public int getFinalNbClauses() {
		return finalNbClauses;
	}

	public int getFinalNbUnaryClauses() {
		return finalNbUnaryClauses;
	}

	public int getFinalNbBinaryClauses() {
		return finalNbBinaryClauses;
	}

	public int getFinalNbTernaryClauses() {
		return finalNbTernaryClauses;
	}

	public int getFinalNbLongClauses() {
		return finalNbLongClauses;
	}

	public double getFinalRatio() {
		return finalRatio;
	}

	public ResultSolver getResult() {
		return result;
	}
	
	public String getBenchmarkName() {
		return benchmarkName;
	}
	
}
