import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
// Ask about what needs to get sent to display data (and who is doing it)

public class DataRetrieval {
	private int[][] years;
	private float[][] data;

	/**
	 * 
	 * @param settings array of settings on the mainUI {analysis, country, start
	 *                 year, end year}
	 */
	public void getData(String[] settings) {
		// TODO OPTIONAL use csv file to convert from full country name to three letter
		String[] analysisMethods = { "CO2 Emission vs. Energy Use vs. Air Pollution", "Air Pollution vs. Forest Area",
				"Ratio of CO2 Emmisions and GDP per capita", "Avg. Forest Area",
				"Avg. Government Expenditure on Education", "Ratio of Hospital beds and Current Health Expenditure",
				"Current Health expenditure per capita vs. Infant Mortality Rate",
				"Ratio of Government Expenditure on Education vs. Current Health Expenditure" };

		String[][] analysisMethodsForURL = { { "EN.ATM.CO2E.PC", "EG.USE.PCAP.KG.OE", "EN.ATM.PM25.MC.M3" },
				{ "EN.ATM.PM25.MC.M3", "AG.LND.FRST.ZS" }, { "EN.ATM.CO2E.PC", "NY.GDP.MKTP.KD.ZG" },
				{ "AG.LND.FRST.ZS" }, { "EN.ATM.CO2E.PC" }, { "SH.MED.BEDS.ZS", "SH.XPD.CHEX.PC.CD" },
				{ "SH.XPD.CHEX.PC.CD", "SH.DYN.MORT" }, { "SE.XPD.TOTL.GB.ZS", "SH.XPD.CHEX.GD.ZS" } };

		String countryLetterCode = "";
		int selectedSeries = 0;

		for (int i = 0; i < analysisMethods.length; ++i) {
			if (analysisMethods[i].equals(settings[0])) {
				selectedSeries = i;
				break;
			}
		}

		if (settings[0].equals("Select Analysis")) {
			new Error("No analysis method is chosen");
			return;
		}
		if (settings[1].toLowerCase().equals("canada")) {
			countryLetterCode = "can";
		} else if (settings[1].toLowerCase().equals("usa")) {
			countryLetterCode = "usa";
		} else if (settings[1].toLowerCase().equals("russia")) {
			countryLetterCode = "rus";
		} else if (settings[1].toLowerCase().equals("sri lanka")) {
			countryLetterCode = "lka";
		} else if (settings[1].toLowerCase().equals("egypt")) {
			countryLetterCode = "egy";
		}
		
		if (settings[2].equals("Select Year...")) {
			new Error("No start year is chosen");
			return;
		} else if (settings[3].equals("Select Year...")) {
			new Error("No end year is chosen");
			return;
		}

		years = new int[analysisMethodsForURL[selectedSeries].length][];
		data = new float[analysisMethodsForURL[selectedSeries].length][];

		for (int seriesNumber = 0; seriesNumber < analysisMethodsForURL[selectedSeries].length; ++seriesNumber) {
			// Api request string
			String urlString = String.format(
					"http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s:%s&format=json", countryLetterCode,
					analysisMethodsForURL[selectedSeries][seriesNumber], settings[2], settings[3]);
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				if (conn.getResponseCode() == 200) { // Check for good response code
					String inline = "";
					Scanner sc = new Scanner(url.openStream());
					while (sc.hasNext()) {
						inline += sc.nextLine();
					}
					sc.close();

					JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
					int numOfResults = jsonArray.get(1).getAsJsonArray().size(); //See how many datapoints
					years[seriesNumber] = new int[numOfResults];
					data[seriesNumber] = new float[numOfResults];

					for (int i = 0; i < numOfResults; ++i) {
						try {
							years[seriesNumber][i] = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject()
									.get("date").getAsInt(); // Years of the data
							data[seriesNumber][i] = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject()
									.get("value").getAsFloat(); // Value of the data

						} catch (UnsupportedOperationException e) { // Invalid data
							years[seriesNumber][i] = -1;
							data[seriesNumber][i] = -1;
						}
					}

				} else {
					new Error(String.format("WORLD BANK API ERROR. Response code: %d", conn.getResponseCode())); // Bad response code
				}
			} catch (IOException e) { // Internet error
				new Error("WORLD BANK API ERROR.");
				e.printStackTrace();
			}
		}

		if (selectedSeries == 0) {
			(new CO2_Energy_Pollution()).calculate(this);
		} else if (selectedSeries == 1) {
			(new Pollution_Forest()).calculate(this);
		} else if (selectedSeries == 2) {
			(new Ratio_EmissionvsGDP()).calculate(this);
		} else if (selectedSeries == 3) {
			(new AvgForestArea()).calculate(this);
		} else if (selectedSeries == 4) {
			(new AvgEduGDP()).calculate(this);
		} else if (selectedSeries == 5) {
			(new Ratio_HospitalBedvsHealthExp()).calculate(this);
			// multiply beds by 1000
		} else if (selectedSeries == 6) {
			(new HealthExpvsMortality()).calculate(this);
		} else if (selectedSeries == 7) {
			(new Ratio_EduExpvsHealthExp()).calculate(this);
		}

	}

	/**
	 * Get the matrix of years. Invalid years are -1
	 * @return integer matrix of years
	 */
	public int[][] getYears() {
		return years;
	}

	/**
	 * Get the matrix of data. Invalid data is -1 (as well as the year)
	 * @return float matrix of data points
	 */
	public float[][] getData() {
		return data;
	}

}