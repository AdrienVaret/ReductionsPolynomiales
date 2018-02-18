package logs;

import java.util.ArrayList;

public class Reduction {
	
	private String name;
	
	private ArrayList<FileInstance> instances = new ArrayList<FileInstance>();
	
	public Reduction(String name) {
		this.name = name;
	}
	
	public ArrayList<FileInstance> getInstances(){
		return instances;
	}
	
}
