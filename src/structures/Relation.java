package structures;

import java.util.ArrayList;

public class Relation {

	public enum TypeRelation {
		R_ALLOWED, R_CONFLICT;
	}
	
	private String name;
	private TypeRelation type;
	private int nbTuples;
	private ArrayList<Couple> couples;
	
	public Relation(String name, TypeRelation type, int nbTuples, ArrayList<Couple> couples) {
		this.name = name;
		this.type     = type;
		this.nbTuples = nbTuples;
		this.couples  = couples;
	}

	public TypeRelation getType() {
		return type;
	}

	public int getNbTuples() {
		return nbTuples;
	}

	public ArrayList<Couple> getCouples() {
		return couples;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCouples(ArrayList<Couple> couples) {
		this.couples = couples;
	}
}
