package logs;

public abstract class Instance {
	@SuppressWarnings("unused")
	private String filename;
	
	public Instance (String filename) {
		this.filename = filename;
	}
}
