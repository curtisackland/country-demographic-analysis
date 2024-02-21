
/**
 * This class represents the Main User Interface including all drop downs, buttons, and viewers
 * @author Curtis Ackland 
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.jfree.chart.ChartPanel;

public class MainUI extends JFrame implements ActionListener {

	private AnalysisDropdown analysis = new AnalysisDropdown();
	private CountryDropdown country = CountryDropdown.getInstance();
	private YearDropdown startYear = YearDropdown.getInstance("start");
	private YearDropdown endYear = YearDropdown.getInstance("end");
	private ViewerDropdown viewer = ViewerDropdown.getInstance();

	private JButton addButton = new JButton("+");
	private JButton subButton = new JButton("-");
	private JButton recalculateButton = new JButton("Recalculate");
	private ArrayList<String> viewerList = new ArrayList<String>();
	private volatile ArrayList<Component> chartList = new ArrayList<Component>();

	private volatile JPanel mainPanel;
	GridBagConstraints constraints;

	/**
	 * Constructor initializes the textFields, drop downs and buttons in the MainUI
	 */
	public MainUI() {
		super("Statistic Analysis");

		mainPanel = new JPanel(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 2, 0);

		JLabel selectSettings = new JLabel("Select settings:");
		JLabel selectAnalysis = new JLabel("Select analysis method:");
		JLabel selectCountry = new JLabel("Select country:");
		JLabel selectStartYear = new JLabel("Start year:");
		JLabel selectEndYear = new JLabel("End year:");
		JLabel selectViewer = new JLabel("Available views:");

		for (int i = 0; i < analysis.getNumOptions(); i++) {
			analysis.addItem(analysis.getOption(i));
		}

		for (int i = 0; i < country.getNumOptions(); i++) {
			country.addItem(country.getOption(i));
		}

		for (int i = 0; i < startYear.getNumOptions(); i++) {
			startYear.addItem(startYear.getOption(i));
			endYear.addItem(startYear.getOption(i));
		}

		for (int i = 0; i < viewer.getNumOptions(); i++) {
			viewer.addItem(viewer.getOption(i));
		}

		constraints.gridwidth = 2;

		constraints.gridx = 0; // Select settings text
		constraints.gridy = 0;
		mainPanel.add(selectSettings, constraints);

		constraints.gridy = 1;
		mainPanel.add(selectAnalysis, constraints); // Analysis
		constraints.gridy = 2;
		mainPanel.add(analysis, constraints);

		constraints.gridy = 3;
		mainPanel.add(selectCountry, constraints); // Country
		constraints.gridy = 4;
		mainPanel.add(country, constraints);

		constraints.gridy = 5;
		mainPanel.add(selectStartYear, constraints); // Start years
		constraints.gridy = 6;
		mainPanel.add(startYear, constraints);

		constraints.gridy = 7;
		mainPanel.add(selectEndYear, constraints); // End years
		constraints.gridy = 8;
		mainPanel.add(endYear, constraints);

		constraints.gridy = 9;
		mainPanel.add(selectViewer, constraints); // Viewers
		constraints.gridy = 10;
		mainPanel.add(viewer, constraints);

		constraints.gridwidth = 1;
		constraints.gridy = 11;
		mainPanel.add(addButton, constraints); // Add viewer button
		constraints.gridx = 1;
		mainPanel.add(subButton, constraints); // Subtract viewer button

		constraints.gridwidth = 2;
		constraints.gridy = 12;
		constraints.gridx = 0;
		mainPanel.add(recalculateButton, constraints); // Recalculate button

		analysis.addActionListener(this);
		analysis.setActionCommand("analysis");

		country.addActionListener(this);
		country.setActionCommand("country");

		startYear.addActionListener(this);
		startYear.setActionCommand("start");

		endYear.addActionListener(this);
		endYear.setActionCommand("end");

		viewer.addActionListener(this);
		viewer.setActionCommand("viewer");

		addButton.addActionListener(this);
		addButton.setActionCommand("add");

		subButton.addActionListener(this);
		subButton.setActionCommand("sub");

		recalculateButton.addActionListener(this);
		recalculateButton.setActionCommand("recalculate");

		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * When a drop down or button is used or updated, this function is sent a command 
	 * and chooses what to do with the command
	 * @param ActionEvent is the command sent when a JComponent is update or used
	 */
	public void actionPerformed(ActionEvent e) {
		if ("analysis".equals(e.getActionCommand())) {
			if(!analysis.getSelectedItem().equals(analysis.getOption(analysis.getCurrentOptionIndex()))){
				analysis.setOption(analysis.getSelectedIndex());
				analysis.clear(); // Set all drop down index's to zero
				country.setActionCommand(null);
				startYear.setActionCommand(null);
				endYear.setActionCommand(null);
				viewer.setActionCommand(null);
				country.setSelectedIndex(0);
				startYear.setSelectedIndex(0);
				endYear.setSelectedIndex(0);
				viewer.setSelectedIndex(0);
				country.setActionCommand("country");
				startYear.setActionCommand("start");
				endYear.setActionCommand("end");
				viewer.setActionCommand("viewer");
				viewerList = new ArrayList<String>();
				drawGraphs();
			}
		} else if ("country".equals(e.getActionCommand())) { // A new country is chosen
			try {
				if (ComboValidator.checkCombination(analysis.getSelectedItem().toString(),
						viewer.getSelectedItem().toString(), country.getSelectedItem().toString())) {
					country.setOption(country.getSelectedIndex());
				} else {
					country.setSelectedIndex(country.getCurrentOptionIndex());
					new Error("Incompatible country chosen");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if ("start".equals(e.getActionCommand())) { // New start year is selected
			startYear.setOption(startYear.getSelectedIndex());
		} else if ("end".equals(e.getActionCommand())) {  // New end year is selected
			endYear.setOption(endYear.getSelectedIndex());
		} else if ("viewer".equals(e.getActionCommand())) { // New viewer is selected
			if (viewer.getSelectedIndex() != 0) {
				try {
					if (ComboValidator.checkCombination(analysis.getSelectedItem().toString(),
							viewer.getSelectedItem().toString(), country.getSelectedItem().toString())) {
						viewer.setOption(viewer.getSelectedIndex());
					} else {
						viewer.setSelectedIndex(viewer.getCurrentOptionIndex());
						new Error("Incompatible viewer chosen");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if ("add".equals(e.getActionCommand())) { // Addition button is pressed
			if(viewer.getSelectedIndex() != 0) {
				Boolean addToList = true;
				for (int i = 0; i < viewerList.size(); i++) {
					for (int j = 0; j < viewer.getNumOptions() - 1; j++) {
						if (viewerList.get(i).equals(viewer.getSelectedItem().toString())) {
							addToList = false;
							break;
						}
					}
				}
			
				if (addToList) {
					viewerList.add(viewer.getSelectedItem().toString());
				} else {
					new Error("Replicate graph");
				}
			}
		} else if ("sub".equals(e.getActionCommand())) { // Subtract button is pressed
			Boolean removed = false;
			for (int i = 0; i < viewerList.size(); i++) {
				for (int j = 0; j < viewer.getNumOptions() - 1; j++) {
					if (viewerList.get(i).equals(viewer.getSelectedItem().toString())) {
						viewerList.remove(i);
						removed = true;
						drawGraphs();
						break;
					}
				}
			}
			if (removed == false) {
				new Error("Viewer does not exist");
			}
		} else if ("recalculate".equals(e.getActionCommand())) { // Recalculate button is pressed
			
			if(analysis.getSelectedIndex() == 0 && country.getSelectedIndex() == 0 && startYear.getSelectedIndex() == 0 && endYear.getSelectedIndex() == 0 && viewer.getSelectedIndex() == 0) {
				new Error("Please enter all settings");
			} else {
				DataRetrieval data = new DataRetrieval();
				String[] settings = { analysis.getSelectedItem().toString(), country.getSelectedItem().toString(),
						startYear.getSelectedItem().toString(), endYear.getSelectedItem().toString() };
				data.getData(settings);

				drawGraphs();
			}

		} else if (e.getActionCommand() == null) {

		} else {
			new Error("Unknown action please restart program.");
		}
	}

	/**
	 * Draws the viewers that the user has chosen
	 */
	private void drawGraphs() {
		for (int i = chartList.size() - 1; i >= 0; i--) { // removes all existing viewers
			mainPanel.remove(chartList.get(i));
			chartList.remove(i);
		}

		constraints.gridwidth = 1;
		constraints.gridheight = 12;

		for (int i = 0; i < viewerList.size(); i++) { // Determining which spot each graph goes in
			if (i == 0) {
				constraints.gridx = 3;
				constraints.gridy = 0;
			} else if (i == 1) {
				constraints.gridx = 4;
				constraints.gridy = 0;
			} else if (i == 2) {
				constraints.gridx = 3;
				constraints.gridy = 12;
			} else if (i == 3) {
				constraints.gridx = 4;
				constraints.gridy = 12;
			} else if (i == 4) {
				constraints.gridx = 5;
				constraints.gridy = 0;
			} else {
				new Error("Incorrect number of graphs");
			}

			if (viewerList.get(i).equals("Pie Chart")) { // Drawing each graph
				chartList.add(new ChartPanel(DisplayData.getPieChart()));
				chartList.get(i).setPreferredSize(new Dimension(400, 300));
				((JComponent) chartList.get(i)).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				chartList.get(i).setBackground(Color.white);
				mainPanel.add(chartList.get(i), constraints);
				this.add(mainPanel);
			} else if (viewerList.get(i).equals("Line Chart")) {
				chartList.add(new ChartPanel(DisplayData.getLineChart()));
				chartList.get(i).setPreferredSize(new Dimension(400, 300));
				((JComponent) chartList.get(i)).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				chartList.get(i).setBackground(Color.white);
				mainPanel.add(chartList.get(i), constraints);
				this.add(mainPanel);
			} else if (viewerList.get(i).equals("Bar Chart")) {
				chartList.add(new ChartPanel(DisplayData.getBarChart()));
				chartList.get(i).setPreferredSize(new Dimension(400, 300));
				((JComponent) chartList.get(i)).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				chartList.get(i).setBackground(Color.white);
				mainPanel.add(chartList.get(i), constraints);
				this.add(mainPanel);
			} else if (viewerList.get(i).equals("Scatter Chart")) {
				chartList.add(new ChartPanel(DisplayData.getScatterChart()));
				chartList.get(i).setPreferredSize(new Dimension(400, 300));
				((JComponent) chartList.get(i)).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				chartList.get(i).setBackground(Color.white);
				mainPanel.add(chartList.get(i), constraints);
				this.add(mainPanel);
			} else if (viewerList.get(i).equals("Report")) {
				JScrollPane outputScrollPane = new JScrollPane((JTextArea) DisplayData.getReport());
				outputScrollPane.setPreferredSize(new Dimension(400, 300));
				chartList.add(outputScrollPane);
				mainPanel.add(outputScrollPane, constraints);
				this.add(mainPanel);
			}
		}
		this.pack();
		this.repaint();
	}
}
