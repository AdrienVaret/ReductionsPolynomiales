package logs;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JPanel{
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JTable table;
	
	private ArrayList<Data> datas;
	
	private String [] header = {"Competition",  "Benchmark",   "SAT",          "Reduction", 
								"Initial file", "I_Variables", "I_Clauses",    "I_UClauses", 
								"I_BClauses",   "I_TClauses",  "I_LClauses",   "I_Ratio_CX", "Final file",   
								"F_Variables",  "F_Clauses",   "F_UClauses",   "F_BClauses",   
								"F_TClauses",   "F_LClauses",  "F_Ratio_CX",   "Solver",      
								"Initial time", "Final time",  "Ratio"};
	private String [] [] data;
	
	public Table(ArrayList<Data> datas) {
		this.datas = datas;
		setData();
		
		DefaultTableModel model = new DefaultTableModel(data,header);
		JTable table = new JTable(model);
		
        table.setPreferredScrollableViewportSize(new Dimension(1000,800));
        table.setFillsViewportHeight(true);

        //JScrollPane js=new JScrollPane(table);
        JScrollPane js = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       // js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setSize(1200, 900);
        js.setVisible(true);
        add(js);
	}
	
	public void setData() {
		data = new String [datas.size()][header.length];
		int i = 0;
		for (Data d : datas) {
			
			data [i][0]  = d.getCompetitionYear();
			data [i][1]  = d.getBenchmarkName();
			data [i][2]  = d.getSatisfiability();
			data [i][3]  = d.getReductionName();
			
			data [i][4]  = d.getInitialFile();
			data [i][5]  = Integer.toString(d.getInitialNbVariables());
			data [i][6]  = Integer.toString(d.getInitialNbClauses());
			data [i][7]  = Integer.toString(d.getInitialNbUnaryClauses());
			data [i][8]  = Integer.toString(d.getInitialNbBinaryClauses());
			data [i][9]  = Integer.toString(d.getInitialNbTernaryClauses());
			data [i][10] = Integer.toString(d.getInitialNbLongClauses());
			data [i][11] = Double.toString(d.getInitialRatio());
			
			data [i][12] = d.getFinalFile();
			data [i][13] = Integer.toString(d.getFinalNbVariables());
			data [i][14] = Integer.toString(d.getFinalNbClauses());
			data [i][15] = Integer.toString(d.getFinalNbUnaryClauses());
			data [i][16] = Integer.toString(d.getFinalNbBinaryClauses());
			data [i][17] = Integer.toString(d.getFinalNbTernaryClauses());
			data [i][18] = Integer.toString(d.getFinalNbLongClauses());
			data [i][19] = Double.toString(d.getFinalRatio());
		
			data [i][20] = d.getResult().getSolverName();
			data [i][21] = Double.toString(d.getResult().getInitialTime());
			data [i][22] = Double.toString(d.getResult().getFinalTime());
			data [i][23] = Double.toString(d.getResult().getRatio());
			
			i += 1;
		}
		
	}
}
