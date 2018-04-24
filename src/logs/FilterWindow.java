package logs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import logs.Lister.Filter;

import javax.swing.JButton;
import java.awt.Color;

public class FilterWindow {

	//private ArrayList<Data> oldDatas;
	
	private StatsWindow oldFrame;
	
	public JFrame frame;
	private JTextField fCompetition;
	private JTextField fBenchMark;
	private JTextField fSatisfiability;
	private JTextField fInitialFile;
	private JTextField fFinalFile;
	private JTextField fReduction;
	private JCheckBox bInitialVariables;
	private JTextField iNbVarsMin;
	private JCheckBox bFinalVariables;
	private JTextField fNbVarsMin;
	private JCheckBox bSolverName;
	private JTextField fSolverName;
	private JCheckBox bInitialClauses;
	private JCheckBox bFinalClauses;
	private JTextField iNbClausesMin;
	private JTextField fNbClausesMin;
	private JCheckBox bInitialUClauses;
	private JCheckBox bFinalUClauses;
	private JCheckBox bInitialBClauses;
	private JCheckBox bFinalBClauses;
	private JCheckBox bInitialTClauses;
	private JCheckBox bFinalTClauses;
	private JCheckBox bInitialLClauses;
	private JCheckBox bFinalLClauses;
	private JCheckBox bInitialTime;
	private JCheckBox bFinalTime;
	private JCheckBox bRatioTime;
	private JTextField iUClausesMin;
	private JTextField iBClausesMin;
	private JTextField iTClausesMin;
	private JTextField iLClausesMin;
	private JTextField iTimeMin;
	private JTextField iRatioTimeMin;
	private JTextField fNbUClausesMin;
	private JTextField fNbBClausesMin;
	private JTextField fNbTClausesMin;
	private JTextField fNbLClausesMin;
	private JTextField fTimeMin;
	private JButton btnApplyFilters;
	private JTextField iNbVarsMax;
	private JTextField iNbClausesMax;
	private JTextField iUClausesMax;
	private JTextField iBClausesMax;
	private JTextField iTClausesMax;
	private JTextField iLClausesMax;
	private JTextField iTimeMax;
	private JTextField iRatioTimeMax;
	private JTextField fNbVarsMax;
	private JTextField fNbClausesMax;
	private JTextField fNbUClausesMax;
	private JTextField fNbBClausesMax;
	private JTextField fNbTClausesMax;
	private JTextField fNbLClausesMax;
	private JTextField fTimeMax;
	private JLabel lblMin;
	private JLabel lblMaxValue;
	private JLabel label;
	private JLabel label_1;
	private JCheckBox bInitialRatioClauses;
	private JTextField iRatioClausesMin;
	private JTextField iRatioClausesMax;
	private JCheckBox bFinalRatioClauses;
	private JTextField fRatioClausesMin;
	private JTextField fRatioClausesMax;
	private JCheckBox bCompetition = new JCheckBox("Competition");
	private JCheckBox bBenchmark = new JCheckBox("Benchmark");
	private JCheckBox bSatisfiability = new JCheckBox("Satisfiability");
	private JCheckBox bInitialFile = new JCheckBox("Initial File");
	private JCheckBox bReduction = new JCheckBox("Reduction");
	private JCheckBox bFinalFile = new JCheckBox("Final File");
	private JButton btnDefaultList;
	private JLabel lblInstancesTypes;
	private JCheckBox chckbxSat;
	private JCheckBox chckbxCsp;

	/**
	 * Create the application.
	 */
	public FilterWindow(StatsWindow oldFrame) {
		this.oldFrame = oldFrame;
		initialize();
		chckbxSat.setSelected(true);
		chckbxCsp.setSelected(true);
	}

	
	public void filter() {
		
		ArrayList<Data> newDatas = oldFrame.datas;
		
		if (chckbxCsp.isSelected() && !chckbxSat.isSelected())
			newDatas = Lister.filter(newDatas, Filter.INST_CSP, null, null);
		
		if (chckbxSat.isSelected() && !chckbxCsp.isSelected())
			newDatas = Lister.filter(newDatas, Filter.INST_SAT, null, null);
		
		if (bCompetition.isSelected())
			newDatas = Lister.filter(newDatas, Filter.COMPETITION, fCompetition.getText(), null);
	
		if (bBenchmark.isSelected())
			newDatas = Lister.filter(newDatas, Filter.BENCHMARK, fBenchMark.getText(), null);
		
		if (bSatisfiability.isSelected())
			newDatas = Lister.filter(newDatas, Filter.SAT, fSatisfiability.getText(), null);
		
		if (bInitialFile.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_FILE, fInitialFile.getText(), null);
		
		if (bFinalFile.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_FILE, fFinalFile.getText(), null);
		
		if (bReduction.isSelected())
			newDatas = Lister.filter(newDatas, Filter.REDUCTION, fReduction.getText(), null);
		
		
		if (bInitialVariables.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_VARS, iNbVarsMin.getText(), iNbVarsMax.getText());
		
		if (bFinalVariables.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_VARS, fNbVarsMin.getText(), fNbVarsMax.getText());
		
		
		
		if (bInitialClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_CLAUSES, iNbClausesMin.getText(), iNbClausesMax.getText());
	
		if (bFinalClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_CLAUSES, fNbClausesMin.getText(), fNbClausesMax.getText());
		
		
		
		if (bInitialUClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_UCLAUSES, iUClausesMin.getText(), iUClausesMax.getText());
		
		if (bFinalUClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_UCLAUSES, fNbUClausesMin.getText(), fNbUClausesMax.getText());
		
		
		
		if (bInitialBClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_BCLAUSES, iBClausesMin.getText(), iBClausesMax.getText());
		
		if (bFinalBClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_BCLAUSES, fNbBClausesMin.getText(), fNbBClausesMax.getText());
		
		
		
		if (bInitialTClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_TCLAUSES, iTClausesMin.getText(), iTClausesMax.getText());
		
		if (bFinalTClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_TCLAUSES, fNbTClausesMin.getText(), fNbTClausesMax.getText());
		
		
		
		if (bInitialLClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_LCLAUSES, iLClausesMin.getText(), iLClausesMax.getText());
		
		if (bFinalLClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_LCLAUSES, fNbLClausesMin.getText(), fNbLClausesMax.getText());
		
		
		
		
		if (bInitialRatioClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_RATIO_CX, iRatioClausesMin.getText(), iRatioClausesMax.getText());
		
		if (bFinalRatioClauses.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_RATIO_CX, fRatioClausesMin.getText(), fRatioClausesMax.getText());
		
		
		
		
		if (bInitialTime.isSelected())
			newDatas = Lister.filter(newDatas, Filter.I_TIME, iTimeMin.getText(), iTimeMax.getText());
		
		if (bFinalTime.isSelected())
			newDatas = Lister.filter(newDatas, Filter.F_TIME, fTimeMin.getText(), fTimeMax.getText());
		
		
		
		if (bRatioTime.isSelected())
			newDatas = Lister.filter(newDatas, Filter.RATIO_TIME, iRatioTimeMin.getText(), iRatioTimeMax.getText());
		
		if (bSolverName.isSelected())
			newDatas = Lister.filter(newDatas, Filter.SOLVER_NAME, fSolverName.getText(), null);
		
		StatsWindow window = new StatsWindow(newDatas, oldFrame.initialsData);
		window.frame.setVisible(true);
		
		oldFrame.frame.setVisible(false);
		this.frame.setVisible(false);
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.GRAY);
		frame.setBounds(100, 100, 1200, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[150.00][150.00,grow][150.00,grow][150.00][150.00,grow][150.00][150.00,grow][150.00,grow]", "[][][][][][][][][][][][][][][]"));
		
		JLabel lblApplyFilters = new JLabel("Filters List");
		frame.getContentPane().add(lblApplyFilters, "cell 3 0 2 1,alignx center");
		
		lblMin = new JLabel("min value");
		frame.getContentPane().add(lblMin, "cell 1 1,alignx center");
		
		lblMaxValue = new JLabel("max value");
		frame.getContentPane().add(lblMaxValue, "cell 2 1,alignx center");
		
		label = new JLabel("min value");
		frame.getContentPane().add(label, "cell 6 1,alignx center");
		
		label_1 = new JLabel("max value");
		frame.getContentPane().add(label_1, "cell 7 1,alignx center");
		
		
		frame.getContentPane().add(bCompetition, "flowx,cell 0 2");
		
		fCompetition = new JTextField();
		frame.getContentPane().add(fCompetition, "cell 1 2,growx");
		fCompetition.setColumns(10);
		
		
		frame.getContentPane().add(bBenchmark, "flowx,cell 3 2");
		
		fBenchMark = new JTextField();
		frame.getContentPane().add(fBenchMark, "cell 4 2,growx");
		fBenchMark.setColumns(10);
		
		
		frame.getContentPane().add(bSatisfiability, "flowx,cell 5 2");
		
		fSatisfiability = new JTextField();
		frame.getContentPane().add(fSatisfiability, "cell 6 2,growx");
		fSatisfiability.setColumns(10);
		
		
		frame.getContentPane().add(bInitialFile, "flowx,cell 0 3");
		
		fInitialFile = new JTextField();
		frame.getContentPane().add(fInitialFile, "cell 1 3,growx");
		fInitialFile.setColumns(10);
		
		
		frame.getContentPane().add(bFinalFile, "flowx,cell 3 3");
		
		fFinalFile = new JTextField();
		frame.getContentPane().add(fFinalFile, "cell 4 3,growx");
		fFinalFile.setColumns(10);
		
		
		frame.getContentPane().add(bReduction, "flowx,cell 5 3");
		
		fReduction = new JTextField();
		frame.getContentPane().add(fReduction, "cell 6 3,growx");
		fReduction.setColumns(10);
		
		bInitialVariables = new JCheckBox("Initial nb Variables");
		frame.getContentPane().add(bInitialVariables, "cell 0 4");
		
		iNbVarsMin = new JTextField();
		frame.getContentPane().add(iNbVarsMin, "cell 1 4,growx");
		iNbVarsMin.setColumns(10);
		
		iNbVarsMax = new JTextField();
		frame.getContentPane().add(iNbVarsMax, "cell 2 4,growx");
		iNbVarsMax.setColumns(10);
		
		bSolverName = new JCheckBox("Solver Name");
		frame.getContentPane().add(bSolverName, "cell 3 4");
		
		fSolverName = new JTextField();
		frame.getContentPane().add(fSolverName, "cell 4 4,growx");
		fSolverName.setColumns(10);
		
		bFinalVariables = new JCheckBox("Final nb Variables");
		frame.getContentPane().add(bFinalVariables, "cell 5 4");
		
		fNbVarsMin = new JTextField();
		frame.getContentPane().add(fNbVarsMin, "cell 6 4,growx");
		fNbVarsMin.setColumns(10);
		
		fNbVarsMax = new JTextField();
		fNbVarsMax.setColumns(10);
		frame.getContentPane().add(fNbVarsMax, "cell 7 4,growx");
		
		bInitialClauses = new JCheckBox("Initial nb Clauses");
		frame.getContentPane().add(bInitialClauses, "cell 0 5");
		
		iNbClausesMin = new JTextField();
		frame.getContentPane().add(iNbClausesMin, "cell 1 5,growx");
		iNbClausesMin.setColumns(10);
		
		iNbClausesMax = new JTextField();
		frame.getContentPane().add(iNbClausesMax, "cell 2 5,growx");
		iNbClausesMax.setColumns(10);
		
		lblInstancesTypes = new JLabel("Initial Instances Types");
		frame.getContentPane().add(lblInstancesTypes, "cell 3 5 2 1,alignx center");
		
		bFinalClauses = new JCheckBox("Final nb Clauses");
		frame.getContentPane().add(bFinalClauses, "cell 5 5");
		
		fNbClausesMin = new JTextField();
		frame.getContentPane().add(fNbClausesMin, "cell 6 5,growx");
		fNbClausesMin.setColumns(10);
		
		fNbClausesMax = new JTextField();
		fNbClausesMax.setColumns(10);
		frame.getContentPane().add(fNbClausesMax, "cell 7 5,growx");
		
		bInitialUClauses = new JCheckBox("Initial nb U Clauses");
		frame.getContentPane().add(bInitialUClauses, "cell 0 6");
		
		iUClausesMin = new JTextField();
		frame.getContentPane().add(iUClausesMin, "cell 1 6,growx");
		iUClausesMin.setColumns(10);
		
		iUClausesMax = new JTextField();
		iUClausesMax.setColumns(10);
		frame.getContentPane().add(iUClausesMax, "cell 2 6,growx");
		
		chckbxSat = new JCheckBox("SAT");
		frame.getContentPane().add(chckbxSat, "cell 3 6");
		
		chckbxCsp = new JCheckBox("CSP");
		frame.getContentPane().add(chckbxCsp, "cell 4 6");
		
		bFinalUClauses = new JCheckBox("Final nb U Clauses");
		frame.getContentPane().add(bFinalUClauses, "cell 5 6");
		
		fNbUClausesMin = new JTextField();
		frame.getContentPane().add(fNbUClausesMin, "cell 6 6,growx");
		fNbUClausesMin.setColumns(10);
		
		fNbUClausesMax = new JTextField();
		fNbUClausesMax.setColumns(10);
		frame.getContentPane().add(fNbUClausesMax, "cell 7 6,growx");
		
		bInitialBClauses = new JCheckBox("Initial nb B Clauses");
		frame.getContentPane().add(bInitialBClauses, "cell 0 7");
		
		iBClausesMin = new JTextField();
		frame.getContentPane().add(iBClausesMin, "cell 1 7,growx");
		iBClausesMin.setColumns(10);
		
		iBClausesMax = new JTextField();
		iBClausesMax.setColumns(10);
		frame.getContentPane().add(iBClausesMax, "cell 2 7,growx");
		
		bFinalBClauses = new JCheckBox("Final nb B Clauses");
		frame.getContentPane().add(bFinalBClauses, "cell 5 7");
		
		fNbBClausesMin = new JTextField();
		frame.getContentPane().add(fNbBClausesMin, "cell 6 7,growx");
		fNbBClausesMin.setColumns(10);
		
		fNbBClausesMax = new JTextField();
		fNbBClausesMax.setColumns(10);
		frame.getContentPane().add(fNbBClausesMax, "cell 7 7,growx");
		
		bInitialTClauses = new JCheckBox("Initial nb T Clauses");
		frame.getContentPane().add(bInitialTClauses, "cell 0 8");
		
		iTClausesMin = new JTextField();
		frame.getContentPane().add(iTClausesMin, "cell 1 8,growx");
		iTClausesMin.setColumns(10);
		
		iTClausesMax = new JTextField();
		iTClausesMax.setColumns(10);
		frame.getContentPane().add(iTClausesMax, "cell 2 8,growx");
		
		bFinalTClauses = new JCheckBox("Final nb T Clauses");
		frame.getContentPane().add(bFinalTClauses, "cell 5 8");
		
		fNbTClausesMin = new JTextField();
		frame.getContentPane().add(fNbTClausesMin, "cell 6 8,growx");
		fNbTClausesMin.setColumns(10);
		
		fNbTClausesMax = new JTextField();
		fNbTClausesMax.setColumns(10);
		frame.getContentPane().add(fNbTClausesMax, "cell 7 8,growx");
		
		bInitialLClauses = new JCheckBox("Initial nb L Clauses");
		frame.getContentPane().add(bInitialLClauses, "cell 0 9");
		
		iLClausesMin = new JTextField();
		frame.getContentPane().add(iLClausesMin, "cell 1 9,growx");
		iLClausesMin.setColumns(10);
		
		iLClausesMax = new JTextField();
		iLClausesMax.setColumns(10);
		frame.getContentPane().add(iLClausesMax, "cell 2 9,growx");
		
		bFinalLClauses = new JCheckBox("Final nb L Clauses");
		frame.getContentPane().add(bFinalLClauses, "cell 5 9");
		
		fNbLClausesMin = new JTextField();
		frame.getContentPane().add(fNbLClausesMin, "cell 6 9,growx");
		fNbLClausesMin.setColumns(10);
		
		fNbLClausesMax = new JTextField();
		fNbLClausesMax.setColumns(10);
		frame.getContentPane().add(fNbLClausesMax, "cell 7 9,growx");
		
		bInitialRatioClauses = new JCheckBox("Initial C/X Ratio");
		frame.getContentPane().add(bInitialRatioClauses, "cell 0 10");
		
		iRatioClausesMin = new JTextField();
		frame.getContentPane().add(iRatioClausesMin, "cell 1 10,growx");
		iRatioClausesMin.setColumns(10);
		
		iRatioClausesMax = new JTextField();
		frame.getContentPane().add(iRatioClausesMax, "cell 2 10,growx");
		iRatioClausesMax.setColumns(10);
		
		bFinalRatioClauses = new JCheckBox("Final C/X Ratio");
		frame.getContentPane().add(bFinalRatioClauses, "cell 5 10");
		
		fRatioClausesMin = new JTextField();
		frame.getContentPane().add(fRatioClausesMin, "cell 6 10,growx");
		fRatioClausesMin.setColumns(10);
		
		fRatioClausesMax = new JTextField();
		frame.getContentPane().add(fRatioClausesMax, "cell 7 10,growx");
		fRatioClausesMax.setColumns(10);
		
		bInitialTime = new JCheckBox("Initial Time");
		frame.getContentPane().add(bInitialTime, "cell 0 11");
		
		iTimeMin = new JTextField();
		frame.getContentPane().add(iTimeMin, "cell 1 11,growx");
		iTimeMin.setColumns(10);
		
		iTimeMax = new JTextField();
		iTimeMax.setColumns(10);
		frame.getContentPane().add(iTimeMax, "cell 2 11,growx");
		
		bFinalTime = new JCheckBox("Final Time");
		frame.getContentPane().add(bFinalTime, "cell 5 11");
		
		fTimeMin = new JTextField();
		frame.getContentPane().add(fTimeMin, "cell 6 11,growx");
		fTimeMin.setColumns(10);
		
		fTimeMax = new JTextField();
		fTimeMax.setColumns(10);
		frame.getContentPane().add(fTimeMax, "cell 7 11,growx");
		
		bRatioTime = new JCheckBox("Time Ratio");
		frame.getContentPane().add(bRatioTime, "cell 0 12");
		
		iRatioTimeMin = new JTextField();
		frame.getContentPane().add(iRatioTimeMin, "cell 1 12,growx");
		iRatioTimeMin.setColumns(10);
		
		iRatioTimeMax = new JTextField();
		iRatioTimeMax.setColumns(10);
		frame.getContentPane().add(iRatioTimeMax, "cell 2 12,growx");
		
		btnApplyFilters = new JButton("Apply Filters");
		
		btnApplyFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter();
			}
		});
		
		frame.getContentPane().add(btnApplyFilters, "cell 3 13,growx");
		
		btnDefaultList = new JButton("Default List");
		frame.getContentPane().add(btnDefaultList, "cell 4 13,growx");
		
		btnDefaultList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatsWindow window= new StatsWindow(oldFrame.initialsData, oldFrame.initialsData);
				FilterWindow.this.frame.setVisible(false);
				FilterWindow.this.oldFrame.frame.setVisible(false);
				window.frame.setVisible(true);
			}
		});
	}

}
