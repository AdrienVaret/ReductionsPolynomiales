package logs;

public class ResultSolver {

	private String solverName;
	private double initialTime;
	private double finalTime;
	private double ratio;
	
	public ResultSolver(String solverName, double initialTime, double finalTime) {
		super();
		this.solverName = solverName;
		this.initialTime = initialTime;
		this.finalTime = finalTime;
		this.ratio = initialTime / finalTime;
	}

	public String getSolverName() {
		return solverName;
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
