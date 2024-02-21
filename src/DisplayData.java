
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

/**
 * Class to create the various charts which will be displayed
 * 
 * @author Curtis Ackland, Evan Goldrick, David Lin
 *
 */
public class DisplayData extends JFrame {
	private static String analysisType;
	private static String[][] years;
	private static String[][] data;

	private static final String[] analysisMethods = { "CO2 Emission vs. Energy Use vs. Air Pollution",
			"Air Pollution vs. Forest Area", "Ratio of CO2 Emmisions and GDP per capita", "Avg. Forest Area",
			"Avg. Government Expenditure on Education", "Ratio of Hospital beds and Current Health Expenditure",
			"Current Health expenditure per capita vs. Infant Mortality Rate",
			"Ratio of Government Expenditure on Education vs. Current Health Expenditure" };

	private static final String[][] analysisSeries = { { "CO2 Emission", "Energy Use", "Air Pollution" },
			{ "Air Pollution", "Forest Area" }, { "CO2 Emmisions and GDP per capita" }, { "Avg. Forest Area" },
			{ "Avg. Gov. Exp. on Education" }, { "Hosp. beds and Curr. Health Exp." },
			{ "Current Health expenditure per capita", "Infant Mortality Rate" },
			{ "Gov. Exp. on Edu. vs. Cur. Health Exp." } };

	private static String[] seriesMethods;

	/**
	 * Set single data point is store in first position {{here}}
	 * 
	 * @param type Type of analysis being performed
	 * @param avg  Value to be stored
	 */
	public static void setAvg(String type, String avg) {
		analysisType = type;
		years = null;
		data = new String[1][1];
		data[0][0] = avg;
	}

	/**
	 * Set array of data points, stored in the first array position {here}
	 * 
	 * @param type       Type of analysis being performed
	 * @param yearsArray Array of years for each data point
	 * @param ratios     Array of values for each data point
	 */
	public static void setRatios(String type, String[] yearsArray, String[] ratios) {
		// Single series of points is stored in first array {here}
		analysisType = type;
		years = new String[1][];
		years[0] = yearsArray;
		data = new String[1][];
		data[0] = ratios;
	}

	/**
	 * Set matrix of data points, stored directly in the variable
	 * 
	 * @param type       Type of analysis being performed
	 * @param yearsArray Matrix of years for each data point
	 * @param dataArray  Matrix of values for each data point
	 */
	public static void setData(String type, String[][] yearsArray, String[][] dataArray) {
		// Entire matrix is stored directly in the variable
		analysisType = type;
		years = yearsArray;
		data = dataArray;
	}

	/**
	 * Create a pie chart based on the current data
	 * 
	 * @return Pie Chart
	 */
	public static JFreeChart getPieChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		// If average has no data
		if (Float.parseFloat(data[0][0]) == -1) {
			new Error("No data for this range.");
			return null;
		}

		if ("Avg. Forest Area".equals(analysisType)) {
			dataset.setValue("Forest Area" + "", Float.parseFloat(data[0][0]));
			dataset.setValue("Other", 100 - Float.parseFloat(data[0][0]));
		} else if ("Avg. Government Expenditure on Education".equals(analysisType)) {
			dataset.setValue("Government Expenditure on Education", Float.parseFloat(data[0][0]));
			dataset.setValue("Other", 100 - Float.parseFloat(data[0][0]));
		}

		// Change chart theme to make the plot background white, and then set the chart
		// theme back to what it was.
		StandardChartTheme theme = new StandardChartTheme("test");
		theme.setPlotBackgroundPaint(Color.white);
		ChartTheme temp = ChartFactory.getChartTheme();
		ChartFactory.setChartTheme(theme);
		JFreeChart pieChart = ChartFactory.createPieChart(analysisType, dataset);
		ChartFactory.setChartTheme(temp);

		return pieChart;
	}

	/**
	 * Create a line chart based on the current data
	 * 
	 * @return Line chart
	 */
	public static JFreeChart getLineChart() {
		// Determine analysis method
		seriesMethods = new String[0];
		for (int i = 0; i < analysisMethods.length; i++) {
			if (analysisType.equals(analysisMethods[i])) {
				seriesMethods = analysisSeries[i];
			}
		}

		TimeSeries[] series = new TimeSeries[years.length];

		for (int i = 0; i < years.length; i++) {
			series[i] = new TimeSeries(seriesMethods[i]);
			for (int j = 0; j < years[i].length; j++) {
				if (Integer.parseInt(years[i][j]) != -1) {
					series[i].add(new Year(Integer.parseInt(years[i][j])), Float.parseFloat(data[i][j]));
				}
			}
		}

		TimeSeriesCollection[] dataset = new TimeSeriesCollection[series.length];
		for (int i = 0; i < years.length; i++) {
			dataset[i] = new TimeSeriesCollection();
			dataset[i].addSeries(series[i]);
		}

		XYPlot plot = new XYPlot();
		XYItemRenderer[] itemRenderer = new XYItemRenderer[series.length];

		for (int i = 0; i < series.length; i++) {
			itemRenderer[i] = new XYLineAndShapeRenderer(true, true);
		}

		for (int i = 0; i < series.length; i++) {
			plot.setDataset(i, dataset[i]);
			plot.setRenderer(i, itemRenderer[i]);
			plot.setRangeAxis(i, new NumberAxis(seriesMethods[i]));
		}

		plot.setDomainAxis(new DateAxis("Year"));

		for (int i = 0; i < series.length; i++) {
			plot.mapDatasetToRangeAxis(i, i);
		}

		JFreeChart lineChart = new JFreeChart(analysisType, new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		return lineChart;
	}

	/**
	 * Create bar chart based on the current data
	 * 
	 * @return Bar chart
	 */
	public static JFreeChart getBarChart() {
		// Determine analysis method
		seriesMethods = new String[0];
		for (int i = 0; i < analysisMethods.length; i++) {
			if (analysisType.equals(analysisMethods[i])) {
				seriesMethods = analysisSeries[i];
			}
		}

		// Adjust data to look like its in the same scale
		float[][] adjustedData = new float[data.length][data[0].length];
		float[] maxValues = new float[data.length];
		float maxValueFirstSeries = Float.NEGATIVE_INFINITY;

		for (int i = 0; i < data[0].length; ++i) {
			maxValueFirstSeries = Math.max(Float.parseFloat(data[0][i]), maxValueFirstSeries);
		}

		for (int i = 0; i < data.length; ++i) {
			maxValues[i] = Float.NEGATIVE_INFINITY;
			for (int j = 0; j < data[i].length; ++j) {
				if (Float.parseFloat(data[i][j]) != -1) {
					maxValues[i] = Math.max(Float.parseFloat(data[i][j]), maxValues[i]);
				}
			}
		}

		for (int i = 0; i < data.length; ++i) {
			for (int j = 0; j < data[i].length; ++j) {
				if (Float.parseFloat(data[i][j]) != -1) {
					adjustedData[i][j] = Float.parseFloat(data[i][j]) * maxValueFirstSeries / maxValues[i];
				}
			}
		}

		TimeSeries[] series = new TimeSeries[years.length];

		for (int i = 0; i < years.length; i++) {
			series[i] = new TimeSeries(seriesMethods[i]);
			for (int j = 0; j < years[i].length; j++) {
				if (Integer.parseInt(years[i][j]) != -1) {
					series[i].add(new Year(Integer.parseInt(years[i][j])), adjustedData[i][j]);
				}
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for (int i = 0; i < years.length; i++) {
			dataset.addSeries(series[i]);
		}

		XYPlot plot = new XYPlot();
		ClusteredXYBarRenderer[] itemRenderer = new ClusteredXYBarRenderer[series.length];

		for (int i = 0; i < series.length; i++) {
			itemRenderer[i] = new ClusteredXYBarRenderer();
		}

		plot.setDataset(dataset);
		plot.setRenderer(itemRenderer[0]);
		for (int i = 0; i < series.length; i++) {
			ValueAxis numAxis = new NumberAxis(seriesMethods[i]);
			if (maxValues[i] > 0) {
				numAxis.setRange(new Range(0, maxValues[i] + Math.min(1, maxValues[i] / 100)));
			}
			plot.setRangeAxis(i, numAxis);
		}

		plot.setDomainAxis(new DateAxis("Year"));

		JFreeChart barChart = new JFreeChart(analysisType, new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		return barChart;

	}

	/**
	 * Create a Scatter chart based on the current data
	 * 
	 * @return Scatter chart
	 */
	public static JFreeChart getScatterChart() {

		seriesMethods = new String[0];
		for (int i = 0; i < analysisMethods.length; i++) {
			if (analysisType.equals(analysisMethods[i])) {
				seriesMethods = analysisSeries[i];
			}
		}

		TimeSeries[] series = new TimeSeries[years.length];

		for (int i = 0; i < years.length; i++) {
			series[i] = new TimeSeries(seriesMethods[i]);
			for (int j = 0; j < years[i].length; j++) {
				if (Integer.parseInt(years[i][j]) != -1) {
					series[i].add(new Year(Integer.parseInt(years[i][j])), Float.parseFloat(data[i][j]));
				}
			}
		}

		TimeSeriesCollection[] dataset = new TimeSeriesCollection[series.length];
		for (int i = 0; i < years.length; i++) {
			dataset[i] = new TimeSeriesCollection();
			dataset[i].addSeries(series[i]);
		}

		XYPlot plot = new XYPlot();
		XYItemRenderer[] itemRenderer = new XYItemRenderer[series.length];

		for (int i = 0; i < series.length; i++) {
			itemRenderer[i] = new XYLineAndShapeRenderer(false, true);
		}

		for (int i = 0; i < series.length; i++) {
			plot.setDataset(i, dataset[i]);
			plot.setRenderer(i, itemRenderer[i]);
			plot.setRangeAxis(i, new NumberAxis(seriesMethods[i]));
		}

		plot.setDomainAxis(new DateAxis("Year"));

		for (int i = 0; i < series.length; i++) {
			plot.mapDatasetToRangeAxis(i, i);
		}

		JFreeChart scatterChart = new JFreeChart(analysisType, new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		return scatterChart;
	}

	/**
	 * Create a report based on the current data
	 * 
	 * @return A text area filled out with a report of the data
	 */
	public static JTextArea getReport() {
		JTextArea report;
		String reportMessage = analysisType + "\n";

		seriesMethods = new String[0];
		for (int i = 0; i < analysisMethods.length; i++) {
			if (analysisType.equals(analysisMethods[i])) {
				seriesMethods = analysisSeries[i];
			}
		}
		seriesMethods = new String[0];
		for (int i = 0; i < analysisMethods.length; i++) {
			if (analysisType.equals(analysisMethods[i])) {
				seriesMethods = analysisSeries[i];
			}
		}
		if (years == null) {
			report = new JTextArea(2, 30);
			reportMessage = reportMessage + "\tThe average is: " + data[0][0];
		} else {
			int count = 0;
			for (int i = 0; i < years[0].length; i++) {
				Boolean yearAdded = true;
				for (int j = 0; j < seriesMethods.length; j++) {
					if (Integer.parseInt(years[j][i]) != -1 && yearAdded) {
						reportMessage = reportMessage + "Year " + years[j][i] + ":\n";
						yearAdded = false;
					}
				}
				if (!yearAdded) {
					for (int j = 0; j < seriesMethods.length; j++) {
						if (Integer.parseInt(years[j][i]) != -1) {
							reportMessage = reportMessage + "\t" + seriesMethods[j] + " => " + data[j][i] + "\n";
						} else {
							reportMessage = reportMessage + "\t" + seriesMethods[j] + " => N/A\n";
						}
					}
					count += seriesMethods.length + 1;
				}
				yearAdded = true;
			}
			report = new JTextArea(count + 1, 30);
		}

		report.setEditable(false);
		report.setPreferredSize(new Dimension(400, 300));
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		report.setText(reportMessage);

		return report;
	}
}
