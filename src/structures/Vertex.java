package structures;

public class Vertex {
	private int id;
	private int value;
	private int idClause;
	
	public Vertex(int value, int idClause, int id) {
		this.idClause = idClause;
		this.value = value;
		this.id = id;
	}
	
	public int getValue() {
		return value;
	}

	public int getIdClause() {
		return idClause;
	}
	
	public int getId() {
		return id;
	}
}
