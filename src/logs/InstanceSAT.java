package logs;

public class InstanceSAT extends Instance{
	
	private String filename;
	private int nbVariables;
	private int nbClauses;
	private int nbUnitaryClauses;
	private int nbBinaryClauses;
	private int nbTernaryClauses;
	private int nbLongClauses;
	private double ratio;
	
	public InstanceSAT(String filename, int nbVariables, int nbClauses, int nbUnitaryClauses, int nbBinaryClauses,
			int nbTernaryClauses, int nbLongClauses) {
		super(filename);
		this.nbVariables = nbVariables;
		this.nbClauses = nbClauses;
		this.nbUnitaryClauses = nbUnitaryClauses;
		this.nbBinaryClauses = nbBinaryClauses;
		this.nbTernaryClauses = nbTernaryClauses;
		this.nbLongClauses = nbLongClauses;
		ratio = nbClauses / nbVariables;
	}

	public String getFilename() {
		return filename;
	}

	public int getNbVariables() {
		return nbVariables;
	}

	public int getNbClauses() {
		return nbClauses;
	}

	public int getNbUnitaryClauses() {
		return nbUnitaryClauses;
	}

	public int getNbBinaryClauses() {
		return nbBinaryClauses;
	}

	public int getNbTernaryClauses() {
		return nbTernaryClauses;
	}

	public int getNbLongClauses() {
		return nbLongClauses;
	}

	public double getRatio() {
		return ratio;
	}
}
