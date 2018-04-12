package structures;

public class Constraint {

	private String name;
	private Variable v1, v2;
	private Relation relation;
	
	public Constraint(String name, Variable v1, Variable v2, Relation relation) {
		this.name = name;
		this.v1 = v1;
		this.v2 = v2;
		this.relation = relation;
	}

	public String getName() {
		return name;
	}

	public Variable getV1() {
		return v1;
	}

	public Variable getV2() {
		return v2;
	}

	public Relation getRelation() {
		return relation;
	}
}
