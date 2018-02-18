package logs;

public class Result {
	private String solver;
	private double initialTime;
	private double finalTime;
	private double ratio;
	
	public Result(String solver, double initialTime, double finalTime) {
		this.solver      = solver;
		this.initialTime = initialTime;
		this.finalTime   = finalTime;
		ratio            = initialTime / finalTime;
	}

	public String getSolver() {
		return solver;
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
