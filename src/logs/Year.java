package logs;

import java.util.ArrayList;

public class Year {

	private String name;
	private ArrayList<Benchmark> benchmarks = new ArrayList<Benchmark>();
	
	public Year(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Benchmark> getBenchmarks() {
		return benchmarks;
	}
	
}
