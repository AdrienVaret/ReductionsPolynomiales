package logs;

import java.util.ArrayList;

public class Benchmark {

	private String name;
	private ArrayList<Reduction> reductions = new ArrayList<Reduction>();
	
	public Benchmark(String name) {
		this.name = name;
	}
	
	public ArrayList<Reduction> getReductions() {
		return reductions;
	}
	
	public String getName() {
		return name;
	}
}
