import java.io.IOException;

/**
 * Used to check if the analysis, viewer and country are a valid combination.
 * 
 * @author Evan Goldrick
 *
 */
public class ComboValidator {

	/**
	 * Used to check if the analysis, viewer and country are a valid combination as
	 * specified in analysisFile.txt.
	 * 
	 * @param analysis Analysis type
	 * @param viewer   Selected viewer
	 * @param country  Selected country
	 * @return return true if the selected parameters are compatible to create a
	 *         graph.
	 * @throws IOException
	 */
	public static boolean checkCombination(String analysis, String viewer, String country) throws IOException {

		String data[][] = (new FileAdapter()).getFileData("analysis");

		for (int i = 0; i < data.length; ++i) { // Find analysis
			if (analysis.equals(data[i][0])) {
				for (String v : data[i][2].split(",")) { // Find viewer
					if (viewer.equals(v) || viewer.equals("Select Viewer...")) {
						for (String c : data[i][1].split(",")) { // Find country
							if (country.equals(c) || country.equals("Select Country...")) {
								return true;
							}
						}
					}
				}
				break; // Save some time by assuming each analysis method only appears once
			}
		}
		return false;
	}
}
