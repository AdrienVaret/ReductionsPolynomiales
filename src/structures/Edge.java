package structures;

public class Edge implements Comparable<Edge>{

	private int vertex1, vertex2;
	
	public Edge(int vertex1, int vertex2) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	}

	public int getVertex1() {
		return vertex1;
	}

	public int getVertex2() {
		return vertex2;
	}

	@Override
	public int compareTo(Edge o) {
		if (vertex1 == o.getVertex1() && vertex2 == o.getVertex2()) return 0;
		if (vertex1 == o.getVertex2() && vertex2 == o.getVertex1()) return 0;
		return 1;
	}
/*	
	@Override
	public boolean equals(Object other){
		if (!(other instanceof Edge)) return false;
		else {
			Edge otherEdge = (Edge) other;
			if (vertex1 == otherEdge.getVertex1() && vertex2 == otherEdge.getVertex2()) {
				return true;
			} else {
				return false;
			}
		}
	} */
}
