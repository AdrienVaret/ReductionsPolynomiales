package logs;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;

import logs.Lister.Filter;
import logs.Lister.SortType;
import logs.Lister.Sorter;

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
		JMenuItem menuStat   = new JMenuItem("Statistics");
	
		
		
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
		
		menuBar.add(menuSort);
		menuBar.add(menuFilter);
		menuBar.add(menuStat);
		
		frame.setJMenuBar(menuBar);
		
		Table table = new Table(datas);
		frame.getContentPane().add(table, "cell 0 0,grow");
		
	}

}
