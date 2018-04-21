package reductions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SatToCSP {
	/*
	 * fontion à utiliser pour la reduction
	 * prend en paramètre un fichier au format dimacs d'une instance SAT
	 * et le fichier crééer de la reduction de cette instance 
	 * au format xcsp3
	 */
	public static void reduction(File in, File out){
		String currentLine = null;
		BufferedReader reader = null;
		FileWriter writer = null;
		
		try {
			reader = new BufferedReader(new FileReader(in));
			writer = new FileWriter(out);
			writer.append("<instance  format=\"XCSP3\" type=\"CSP\">\r\n");
			
			currentLine = consumeC(currentLine, reader);
			
			String [] splittedString = currentLine.split(" ");
			int nbVar = Integer.parseInt(splittedString[2]);
			
			appendVariables(writer, nbVar);
			
			appendConstraints(writer, reader);
			
			writer.append("</instance>");
			writer.flush();
			writer.close();
			reader.close();
			
		} catch (Exception e) {
			System.out.println("problème");
		}
		
	}
	
	/*
	 * à partir d'une chaine de caractère d'écrivant une clause
	 * renvoit un tableau numérique d'écrivant cette clause
	 */
	private static int[] getClause(String currentLine){
		String[] tab = currentLine.split(" ");
		int[] clause = new int[tab.length-1];
		for (int i = 0; i < tab.length-1; i++) clause[i] = Integer.parseInt(tab[i]);
		return clause;
	}
	
	/*
	 * ajoute la balise <list> au fichier
	 */
	private static void appendList(FileWriter writer, BufferedReader reader, int[] clause) throws Exception{
		
		writer.append("\t\t\t<list>");
		for(int i : clause){
			int varId = (i>0) ? i : -i;
			writer.append("v" + varId + " ");
		}
		writer.append("</list>\r\n");
	}
	
	/*
	 * ajoute la balise <conflics> au fichier
	 */
	private static void appendConflics(FileWriter writer, BufferedReader reader, int[] clause) throws Exception{
		
		writer.append("\t\t\t<conflics>(");
			
			for (int i = 0; i < clause.length-1; i++){
				int conflicValue = (clause[i]>0)? 0 : 1;
				writer.append(conflicValue +", ");
			}
			
			int conflicValue = (clause[clause.length-1]>0)? 0 : 1;
			writer.append(conflicValue+"");
			
		writer.append(")</conflics>\r\n");
	}
	
	/*
	 * ajoute la balise <extension> au fichier
	 */
	private static void appendExtension(FileWriter writer, BufferedReader reader, String currentLine) throws Exception{
		
		int[] clause = getClause(currentLine);
		
		writer.append("\t\t<extension>\r\n");
		appendList(writer, reader, clause);
		appendConflics(writer, reader, clause);
		writer.append("\t\t</extension>\r\n");
	}
	
	/*
	 * ajoute la balise <constaints> au fichier
	 */
	private static void appendConstraints(FileWriter writer, BufferedReader reader) throws Exception{
		writer.append("\t<constraints>\r\n");
		
		String currentLine;
		while((currentLine = reader.readLine()) != null) {
			appendExtension(writer, reader, currentLine);
		}
		
		writer.append("\t</constraints>\r\n");
	}
	
	/*
	 * ajoute la balise <var> au fichier
	 */
	private static void appendVar(FileWriter writer, int varId) throws Exception{
		writer.append("\t\t<var id=\"v"+varId+"\"> 0 1 </var>\r\n");
	}
	
	/*
	 * ajoute la balise <variables> au fichier
	 */
	private static void appendVariables(FileWriter writer, int varCount) throws Exception{
		writer.append("\t<variables>\r\n");
		for (int i = 1; i <= varCount; i++) appendVar(writer, i);
		writer.append("\t</variables>\r\n");
	}
	
	/*
	 * saute les commentaire du fichier au format dimacs
	 */
	private static String consumeC(String currentLine, BufferedReader reader) throws Exception{
		do{
			currentLine = reader.readLine();
		}while(!currentLine.startsWith("p"));
		
		return currentLine;
	}
}
