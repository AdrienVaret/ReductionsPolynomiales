package logs;

public class ResultSolver {

	private String initialSolverName;
	private String finalSolverName;
	private double initialTime;
	private double finalTime;
	private double ratio;
	
	public ResultSolver(String initalSolverName, String finalSolverName, double initialTime, double finalTime) {
		super();
		this.initialSolverName = initialSolverName;
		this.finalSolverName = finalSolverName;
		this.initialTime = initialTime;
		this.finalTime = finalTime;
		this.ratio = initialTime / finalTime;
	}

	
	public String getFinalSolverName() {
		return finalSolverName;
	}


	public String getSolverName() {
		return initialSolverName;
	}

	public double getInitialTime() {
		return initialTime;
	}

	public double getFinalTime() {
		return finalTime;
	}

	public double getRatio() {
		return ratio;
	}
	
	
	
}
