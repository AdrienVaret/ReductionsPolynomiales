package structures;

import java.util.ArrayList;

public class Domain {

	private String name;
	private ArrayList<String> values;
	private int size;
	
	public Domain(String name, int size, ArrayList<String> values) {
		this.name = name;
		this.size = size;
		this.values = values;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public int getSize() {
		return size;
	}
	
	public String getName() {
		return name;
	}
}
