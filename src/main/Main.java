package main;

import java.io.File;

public class Main {

	public static void displayUsage() {
		System.out.println("Invalid command.");
		System.out.println("USAGE: java -jar Converter $PIn $POut inputFile outputFile");
		System.out.println("$P = { SAT | 3SAT | 3COL | CSP | CLIQUE }");
	}
	
	public static void main (String [] args) {
		if (args.length == 4) {
			String inputFormat = args[0];
			String outputFormat = args[1];
			File inputFile = new File(args[2]);
			File outputFile = new File (args[3]);
			
			if (inputFormat.equals("SAT") && outputFormat.equals("3SAT")) {
				
			} else if (inputFormat.equals("3COL") && outputFormat.equals("SAT")) {
				
			} else if (inputFormat.equals("3SAT") && outputFormat.equals("3COL")) {
				
			} else {
				displayUsage();
			}
			
		} else {
			displayUsage();
		}
	}
}
