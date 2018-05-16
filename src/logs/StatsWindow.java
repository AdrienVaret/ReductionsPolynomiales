package logs;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class StatsWindow {
	
	public JFrame frame;
	public ArrayList<Data> datas;
	public ArrayList<Data> initialsData;
	/**   
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					boolean exists = true;
					ArrayList<Data> datas = Reader.getData(new File("results.data"));
					StatsWindow window = new StatsWindow(datas, datas);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StatsWindow(ArrayList<Data> datas, ArrayList<Data> initialsData) {
		this.datas = datas;
		this.initialsData = initialsData;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Polynomials Reductions Statistics");
		frame.setBounds(100, 100, 1000, 800);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(dim);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JMenuBar menuBar = new JMenuBar();
		JMenuItem menuFilter = new JMenuItem("Filter");
		JMenuItem menuSort   = new JMenuItem("Sort");
		JMenuItem menuOpenFile   = new JMenuItem("Open file");
	
		
		
		menuFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterWindow window = new FilterWindow(StatsWindow.this);
				window.frame.setVisible(true);
			}
		});
		
		menuSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortWindow window = new SortWindow(StatsWindow.this);
				window.frame.setVisible(true);
			}
		});
		
		menuOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(fc);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            System.out.println(file.getName());
		            ArrayList<Data> datas = Reader.getData(file);
		            StatsWindow window = new StatsWindow(datas, datas);
		            window.frame.setVisible(true);
		            StatsWindow.this.frame.setVisible(false);
		        }
			}
		});
		
		menuBar.add(menuSort);
		menuBar.add(menuFilter);
		menuBar.add(menuOpenFile);
		
		frame.setJMenuBar(menuBar);
		
		Table table = new Table(datas);
		frame.getContentPane().add(table, "cell 0 0,grow");
		
		JButton btnExport = new JButton("Export to array LaTeX");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToLaTeX();
			}
		});
		table.add(btnExport);
		
	}

	public void exportToLaTeX() {
		
		int nbLignes = 0;
		
		try {
			FileWriter writer = new FileWriter(new File("array.txt"));
			int i = 101;
			int j = 1;
			DecimalFormat df = new DecimalFormat("#.###");
			for (Data d : datas) {
				
				if (i == 101) {
					writer.write("\\begin{center}" + "\n");
					writer.write("\\begin{tabular}{|c|c|c|c|c|c|}" + "\n");
					writer.write("\\hline" + "\n");
					writer.write("\\textbf{Numéro test} & \\textbf{Solver Init} & \\textbf{Solver Fin} & \\textbf{Initial Time} & \\textbf{Final Time} & \\textbf{Ratio} \\\\" + "\n");
					writer.write("\\hline" + "\n");
					nbLignes += 5;
				}
				
				double ratio = d.getResult().getInitialTime() / d.getResult().getFinalTime();
				writer.write("ID\\_" + i + "&" + d.getResult().getSolverName() + "&" + 
			                               d.getResult().getFinalSolverName()  + "&" +
			                               d.getResult().getInitialTime()      + "&" +
			                               d.getResult().getFinalTime()        + "&" +
			                               df.format(ratio) + "\\\\" + "\n");
				writer.write("\\hline" + "\n");
				i += 1;
				j += 1;
				nbLignes += 2;
				
				if (j == 32) {
					j = 1;
					writer.write("\\end{tabular}" + "\n");
					writer.write("\\end{center}" + "\n");
					writer.write("\\newpage" + "\n");
					writer.write("\\begin{center}" + "\n");
					writer.write("\\begin{tabular}{|c|c|c|c|c|c|}" + "\n");
					writer.write("\\hline" + "\n");
					writer.write("\\textbf{Numéro test} & \\textbf{Solver Init} & \\textbf{Solver Fin} & \\textbf{Initial Time} & \\textbf{Final Time} & \\textbf{Ratio} \\\\" + "\n");
					writer.write("\\hline" + "\n");
					nbLignes += 8;
				}
			}
			
			System.out.println("Second tableau : " + nbLignes);
			
			writer.write("\n" + "\\poubelle{2eme tableau}" + "\n");
			
			writer.write("\\begin{tabular}{| c | c | c | c | c | c | c | c | c |}" + "\n");
			writer.write("\\hline" + "\n");
			writer.write("\\textbf{Numéro test} & \\textbf{Initial File} & \\textbf{V1} & \\textbf{V2} & \\textbf{V3} & \\textbf{V4} & \\textbf{V5} & \\textbf{V6} & \\textbf{Ratio}\\\\" + "\n");
			writer.write("\\hline" + "\n");
			
			nbLignes += 5;
			
			i = 0;
			int min = i+101, max;
			String filename = datas.get(0).getInitialFile();
			for (Data d : datas) {
				if (d.getInitialFile().equals(filename)) {
					i++;
				} else {
					max = (i-1) + 101;
					double ratio = d.getResult().getInitialTime() / d.getResult().getFinalTime();
					writer.write("ID\\_" + min + "-> ID\\_" + max + "& " + filename + "&" + d.getInitialNbVariables() + 
							"& " + d.getInitialNbClauses() +  "& " + d.getInitialNbUnaryClauses() + "& " + 
							d.getInitialNbBinaryClauses() + "& " +  d.getInitialNbTernaryClauses() + 
							"& " + d.getInitialNbLongClauses() + "&" + df.format(ratio) + "\\\\\n" + 
							"\\hline" + "\n");
					min = i + 101;
					filename = datas.get(i).getInitialFile();
					i ++;
					nbLignes ++;
				}
			}
			
			System.out.println("3eme tableau " + nbLignes);
			
			writer.write("\n" + "\\poubelle{3eme tableau}" + "\n");
			
			writer.write("\\begin{tabular}{| c | c | c | c | c | c | c | c | c |}" + "\n");
			writer.write("\\hline" + "\n");
			writer.write("\\textbf{Numéro test} & \\textbf{Final File} & \\textbf{V1'} & \\textbf{V2'} & \\textbf{V3'} & \\textbf{V4'} & \\textbf{V5'} & \\textbf{V6'} & \\textbf{Ratio}\\\\" + "\n");
			writer.write("\\hline" + "\n");
	
			i = 0;
			min = i+101;
			filename = datas.get(0).getFinalFile();
			for (Data d : datas) {
				if (d.getFinalFile().equals(filename)) {
					i++;
				} else {
					max = (i-1) + 101;
					double ratio = d.getResult().getInitialTime() / d.getResult().getFinalTime();
					writer.write("ID\\_" + min + "-> ID\\_" + max + "& " + filename + "&" + d.getFinalNbVariables() + 
							"& " + d.getFinalNbClauses() +  "& " + d.getFinalNbUnaryClauses() + "& " + 
							d.getFinalNbBinaryClauses() + "& " +  d.getFinalNbTernaryClauses() + 
							"& " + d.getFinalNbLongClauses() + "&" + df.format(ratio) + "\\\\\n" + 
							"\\hline" + "\n");
					min = i + 101;
					filename = datas.get(i).getFinalFile();
					i ++;
				}
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
