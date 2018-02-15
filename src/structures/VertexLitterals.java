package structures;

public class VertexLitterals {

	private int idSommet;
	private int variable1;
	private int variable2;
	private int variable3;
	
	public VertexLitterals(int idSommet, int variable1, int variable2, int variable3) {
		this.idSommet = idSommet;
		this.variable1 = variable1;
		this.variable2 = variable2;
		this.variable3 = variable3;
	}
	
	public int id() {
		return idSommet;
	}
	
	public int var1() {
		return variable1;
	}
	
	public int var2() {
		return variable2;
	}
	
	public int var3() {
		return variable3;
	}
}
