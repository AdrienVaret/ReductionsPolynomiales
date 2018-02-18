package logs;

import java.util.ArrayList;

public class FileInstance {

	enum State {
		SAT, UNSAT, TCOL, UNTCOL; 
	}
	
	private State state;
	
	private Instance initialInstance;
	private Instance finalInstance;
	private ArrayList<Result> results;
	
	public FileInstance(State state, Instance initialInstance, Instance finalInstance, ArrayList<Result> results) {
		this.state = state;
		this.initialInstance = initialInstance;
		this.finalInstance = finalInstance;
		this.results = results;
	}

	public State getState() {
		return state;
	}

	public Instance getInitialInstance() {
		return initialInstance;
	}

	public Instance getFinalInstance() {
		return finalInstance;
	}
	
	public ArrayList<Result> getResults(){
		return results;
	}
	
}
