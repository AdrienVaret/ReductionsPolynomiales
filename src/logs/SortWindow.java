package logs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import logs.Lister.SortType;
import logs.Lister.Sorter;
import javax.swing.JButton;

public class SortWindow {

	public JFrame frame;

	JRadioButton  btnCompetition        = new JRadioButton("By Competition");
	JToggleButton tglCompetition        = new JToggleButton("ASCENDANT");
	JRadioButton  btnBenchmark          = new JRadioButton("By benchmark");
	JToggleButton tglBenchmark          = new JToggleButton("ASCENDANT");
	JRadioButton  btnSatisfiability = new JRadioButton("By satisfiability");
	JRadioButton  btnInitialFile        = new JRadioButton("By Initial File");
	JToggleButton tglSatisfiability      = new JToggleButton("ASCENDANT");
	JToggleButton tglInitialFile        = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalFile          = new JRadioButton("By Final File");
	JToggleButton tglFinalFile          = new JToggleButton("ASCENDANT");
	JRadioButton  btnReduction          = new JRadioButton("By Reduction");
	JToggleButton tglReduction          = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialVariables  = new JRadioButton("By Initial Nb Vars");
	JToggleButton tglInitialVariables   = new JToggleButton("ASCENDANT");
	JRadioButton  btnSolver             = new JRadioButton("By Solver Name");
	JToggleButton tglSolver             = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalVariables      = new JRadioButton("By Final Nb Vars");
	JToggleButton tglFinalVariables     = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialClauses     = new JRadioButton("By Initial Clauses");
	JToggleButton tglInitialClauses     = new JToggleButton("ASCENDANT");
	JRadioButton  btnTimeRatio          = new JRadioButton("By Time Ratio");
	JToggleButton tglTimeRatio          = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalClauses       = new JRadioButton("By Final Clauses");
	JToggleButton tglFinalClauses       = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialUClauses    = new JRadioButton("By Initial U Clauses");
	JToggleButton tglInitialUClauses    = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalUClauses      = new JRadioButton("By Final U Clauses");
	JRadioButton  btnFinalBClauses      = new JRadioButton("By Final B Clauses");
	JToggleButton tglFinalUClauses      = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialBClauses    = new JRadioButton("By Initial B Clauses");	
	JToggleButton tglInitialBClauses    = new JToggleButton("ASCENDANT");
	JToggleButton tglFinalBClauses       = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialTClauses    = new JRadioButton("By Inital T Clauses");
	JToggleButton tglInitialTClauses    = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalTClauses      = new JRadioButton("By Final T Clauses");
	JToggleButton tglFinalTClauses      = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialLClauses    = new JRadioButton("By Initial L Clauses");
	JToggleButton tglInitialLClauses    = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalLClauses      = new JRadioButton("By Final L Clauses");
	JToggleButton tglFinalLClauses      = new JToggleButton("ASCENDANT");
	JRadioButton  btnInitialTime        = new JRadioButton("By Initial Time");
	JToggleButton tglInitialTime        = new JToggleButton("ASCENDANT");
	JRadioButton  btnFinalTime          = new JRadioButton("By Final Time");
	JToggleButton tglFinalTime          = new JToggleButton("ASCENDANT");
	JButton       btnSort               = new JButton("Sort Datas");

	private StatsWindow oldFrame;

	/**
	 * Create the application.
	 */
	public SortWindow(StatsWindow oldFrame) {
		this.oldFrame = oldFrame;
		initialize();
	}

	public void sort() {
		
		ArrayList<Data> newDatas = oldFrame.datas;
		
		if (btnCompetition.isSelected()) {
			if (tglCompetition.isSelected()) 
				Lister.sort(newDatas, Sorter.COMPETITION, SortType.ASCENDANT);
			else 
				Lister.sort(newDatas, Sorter.COMPETITION, SortType.DESCENDANT);
		}
		
		if (btnBenchmark.isSelected()) {
			if (tglBenchmark.isSelected())
				Lister.sort(newDatas, Sorter.BENCHMARK, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.BENCHMARK, SortType.DESCENDANT);
		}
		
		if (btnSatisfiability.isSelected()) {
			if (tglSatisfiability.isSelected())
				Lister.sort(newDatas, Sorter.SAT, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.SAT, SortType.DESCENDANT);
		}
		
		if (btnInitialFile.isSelected()) {
			if (tglInitialFile.isSelected())
				Lister.sort(newDatas, Sorter.I_FILE, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_FILE, SortType.DESCENDANT);
		}
		
		if (btnFinalFile.isSelected()) {
			if (tglFinalFile.isSelected())
				Lister.sort(newDatas, Sorter.F_FILE, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_FILE, SortType.DESCENDANT);
		}
		
		if (btnReduction.isSelected()) {
			if (tglReduction.isSelected())
				Lister.sort(newDatas, Sorter.REDUCTION, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.REDUCTION, SortType.DESCENDANT);
		}
		
		if (btnInitialVariables.isSelected()) {
			if (tglInitialVariables.isSelected())
				Lister.sort(newDatas, Sorter.I_VARS, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_VARS, SortType.DESCENDANT);
		}
		
		if (btnSolver.isSelected()) {
			if (tglSolver.isSelected())
				Lister.sort(newDatas, Sorter.SOLVER_NAME, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.SOLVER_NAME, SortType.DESCENDANT);
		}
		
		if (btnFinalVariables.isSelected()) {
			if (tglFinalVariables.isSelected())
				Lister.sort(newDatas, Sorter.F_VARS, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_VARS, SortType.DESCENDANT);
		}
		
		if (btnInitialClauses.isSelected()) {
			if (tglInitialClauses.isSelected())
				Lister.sort(newDatas, Sorter.I_CLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_CLAUSES, SortType.DESCENDANT);
		}
		
		if (btnTimeRatio.isSelected()) {
			if (tglTimeRatio.isSelected())
				Lister.sort(newDatas, Sorter.RATIO_TIME, SortType.ASCENDANT);
			else 
				Lister.sort(newDatas, Sorter.RATIO_TIME, SortType.DESCENDANT);
		}
		
		if (btnFinalClauses.isSelected()) {
			if (tglFinalClauses.isSelected())
				Lister.sort(newDatas, Sorter.F_CLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_CLAUSES, SortType.DESCENDANT);
		}
		
		if (btnInitialBClauses.isSelected()) {
			if (tglInitialBClauses.isSelected())
				Lister.sort(newDatas, Sorter.I_BCLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_BCLAUSES, SortType.DESCENDANT);
		}
		
		if (btnFinalBClauses.isSelected()) {
			if (tglFinalBClauses.isSelected())
				Lister.sort(newDatas, Sorter.F_BCLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_BCLAUSES, SortType.DESCENDANT);
		}
		
		if (btnInitialTClauses.isSelected()) {
			if (tglInitialTClauses.isSelected())
				Lister.sort(newDatas, Sorter.I_TCLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_TCLAUSES, SortType.DESCENDANT);
		}
		
		if (btnFinalTClauses.isSelected()) {
			if (tglFinalTClauses.isSelected())
				Lister.sort(newDatas, Sorter.F_TCLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_TCLAUSES, SortType.DESCENDANT);
		}
		
		if (btnInitialLClauses.isSelected()) {
			if (tglInitialLClauses.isSelected())
				Lister.sort(newDatas, Sorter.I_LCLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_LCLAUSES, SortType.DESCENDANT);
		}
		
		if (btnFinalLClauses.isSelected()) {
			if (tglFinalLClauses.isSelected())
				Lister.sort(newDatas, Sorter.F_LCLAUSES, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_LCLAUSES, SortType.DESCENDANT);
		}
		
		if (btnInitialTime.isSelected()) {
			if (tglInitialTime.isSelected())
				Lister.sort(newDatas, Sorter.I_TIME, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.I_TIME, SortType.DESCENDANT);
		}
		
		if (btnFinalTime.isSelected()) {
			if (tglFinalTime.isSelected())
				Lister.sort(newDatas, Sorter.F_TIME, SortType.ASCENDANT);
			else
				Lister.sort(newDatas, Sorter.F_TIME, SortType.DESCENDANT);
		}
		
		StatsWindow window = new StatsWindow(newDatas, oldFrame.initialsData);
		window.frame.setVisible(true);
		this.frame.setVisible(false);
		oldFrame.frame.setVisible(false);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[150][150.00][150.00][150.00][150.0][150.00]", "[][][][][][][][][][][]"));
		
		JLabel lblSortDatas = new JLabel("Sort Datas");
		frame.getContentPane().add(lblSortDatas, "cell 2 0 2 1,alignx center");
		
		frame.getContentPane().add(btnCompetition, "cell 0 1");		
		frame.getContentPane().add(tglCompetition, "cell 1 1,growx");
		frame.getContentPane().add(btnBenchmark, "cell 2 1");
		frame.getContentPane().add(tglBenchmark, "cell 3 1,growx");
		frame.getContentPane().add(btnSatisfiability, "cell 4 1");
		frame.getContentPane().add(tglSatisfiability, "cell 5 1,growx");
		frame.getContentPane().add(btnInitialFile, "cell 0 2");
		frame.getContentPane().add(tglInitialFile, "cell 1 2,growx");
		frame.getContentPane().add(btnFinalFile, "cell 2 2");
		frame.getContentPane().add(tglFinalFile, "cell 3 2,growx");
		frame.getContentPane().add(btnReduction, "cell 4 2");
		frame.getContentPane().add(tglReduction, "cell 5 2,growx");
		frame.getContentPane().add(btnInitialVariables, "cell 0 3");
		frame.getContentPane().add(tglInitialVariables, "cell 1 3,growx");
		frame.getContentPane().add(btnSolver, "cell 2 3");
		frame.getContentPane().add(tglSolver, "cell 3 3,growx");
		frame.getContentPane().add(btnFinalVariables, "cell 4 3");
		frame.getContentPane().add(tglFinalVariables, "cell 5 3,growx");
		frame.getContentPane().add(btnInitialClauses, "cell 0 4");
		frame.getContentPane().add(tglInitialClauses, "cell 1 4,growx");
		frame.getContentPane().add(btnTimeRatio, "cell 2 4");
		frame.getContentPane().add(tglTimeRatio, "cell 3 4,growx");
		frame.getContentPane().add(btnFinalClauses, "cell 4 4");
		frame.getContentPane().add(tglFinalClauses, "cell 5 4,growx");
		frame.getContentPane().add(btnInitialUClauses, "cell 0 5");
		frame.getContentPane().add(tglInitialUClauses, "cell 1 5,growx");
		
		
		
		frame.getContentPane().add(tglInitialBClauses, "cell 1 6,growx");
		frame.getContentPane().add(tglFinalUClauses, "cell 5 5,growx");
		frame.getContentPane().add(btnFinalUClauses, "cell 4 5");
		frame.getContentPane().add(btnInitialBClauses, "cell 0 6");	
		frame.getContentPane().add(btnFinalBClauses, "cell 4 6");
		frame.getContentPane().add(tglFinalBClauses, "cell 5 6,growx");
		frame.getContentPane().add(btnInitialTClauses, "cell 0 7");
		frame.getContentPane().add(tglInitialTClauses, "cell 1 7,growx");
		frame.getContentPane().add(btnFinalTClauses, "cell 4 7");
		frame.getContentPane().add(tglFinalTClauses, "cell 5 7,growx");
		frame.getContentPane().add(btnInitialLClauses, "cell 0 8");
		frame.getContentPane().add(tglInitialLClauses, "cell 1 8,growx");
		frame.getContentPane().add(btnFinalLClauses, "cell 4 8");
		frame.getContentPane().add(tglFinalLClauses, "cell 5 8,growx");
		frame.getContentPane().add(btnInitialTime, "cell 0 9");
		frame.getContentPane().add(tglInitialTime, "cell 1 9,growx");
		frame.getContentPane().add(btnFinalTime, "cell 4 9");
		frame.getContentPane().add(tglFinalTime, "cell 5 9,growx");
		frame.getContentPane().add(btnSort, "cell 2 10 2 1,growx");
		
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sort();
			}
		});
	}

}
