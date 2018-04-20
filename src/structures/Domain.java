package structures;

import java.util.ArrayList;

public class Domain {

	private String name;
	private ArrayList<String> values;
	private int size;
	private int maxValue;
	private int minValue;
	
	public Domain(String name, int size, ArrayList<String> values, int maxValue, int minValue) {
		this.name = name;
		this.size = size;
		this.values = values;
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public int getSize() {
		return size;
	}
	
	public int getMaxValue() {
		return maxValue;
	}
	
	public int getMinValue() {
		return minValue;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
}
