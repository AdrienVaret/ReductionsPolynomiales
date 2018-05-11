package reductions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TColToCSP2 {
	
	/*
	 * fonction à utiliser pour la reduction
	 * prend en paramètre un fichier au format dimacs d'un graphe
	 * et le fichier créée de la reduction de ce graphe
	 * au format xcsp3 pour la 3 colorisation
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
			int nbVertex = Integer.parseInt(splittedString[2]);
			
			appendVariables(writer, nbVertex);
			
			appendConstraints(writer, reader);
			
			writer.append("</instance>");
			writer.flush();
			writer.close();
			reader.close();
			
		} catch (Exception e) {
			System.out.println("problème");
		}
		
	}
	
	private static int[] getVertices(String currentLine){
		
		int[] vertices = new int[2];
		String[] tab = currentLine.split(" ");
		//System.out.println(tab[1] + " " + tab[2]);
		vertices[0] = Integer.parseInt(tab[1]);
		vertices[1] = Integer.parseInt(tab[2]);
		return vertices;
	}
	
	private static void appendList(FileWriter writer, BufferedReader reader, int[] vertices) throws Exception{
		
		writer.append("\t\t\t<list>");
		writer.append("v" + vertices[0] + " v" + vertices[1]);
		
		writer.append("</list>\r\n");
	}
	
	private static void appendConflics(FileWriter writer, BufferedReader reader) throws Exception{
		
		writer.append("\t\t\t<supports>(0,0) (1,1) (2,2)");
	
			
		writer.append("</supports>\r\n");
	}
	
	private static void appendSupports(FileWriter writer, BufferedReader reader) throws Exception{
		
		writer.append("\t\t\t<supports>(0,1) (0,2) (1,0) (1,2) (2,0) (2,1)");
	
			
		writer.append("</supports>\r\n");
	}
	
	private static void appendExtension(FileWriter writer, BufferedReader reader, String currentLine) throws Exception{
		
		//int[] clause = getClause(currentLine);
		
		writer.append("\t\t<extension>\r\n");
		int[] vertices = getVertices(currentLine);
		appendList(writer, reader, vertices);
		appendSupports(writer, reader);
		writer.append("\t\t</extension>\r\n");
	}
	
	private static void appendConstraints(FileWriter writer, BufferedReader reader) throws Exception{
		writer.append("\t<constraints>\r\n");
		
		String currentLine;
		while((currentLine = reader.readLine()) != null) {
			appendExtension(writer, reader, currentLine);
		}
		
		writer.append("\t</constraints>\r\n");
	}
	
	private static void appendVariables(FileWriter writer, int varCount) throws Exception{
		writer.append("\t<variables>\r\n");
		for (int i = 1; i <= varCount; i++) appendVar(writer, i);
		writer.append("\t</variables>\r\n");
	}
	
	private static void appendVar(FileWriter writer, int varId) throws Exception{
		writer.append("\t\t<var id=\"v"+varId+"\"> 0 1 2 </var>\r\n");
	}
	
	private static String consumeC(String currentLine, BufferedReader reader) throws Exception{
		do{
			currentLine = reader.readLine();
		}while(!currentLine.startsWith("p"));
		
		return currentLine;
	}
}
