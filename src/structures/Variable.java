package structures;

public class Variable {

	private String name;
	private Domain domain;
	
	public Variable(String name, Domain domain) {
		this.name = name;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public Domain getDomain() {
		return domain;
	}
}
