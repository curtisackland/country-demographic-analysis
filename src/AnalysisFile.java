import java.io.*;
import java.util.ArrayList;

/**
 * Used to read analysisFile.txt to which charts are compatible.
 * 
 * @author Evan Goldrick
 *
 */
public class AnalysisFile {
	private ArrayList<String> analysisMethods, countries, viewers;
	private int numberOfArrays = 3;

	/**
	 * Constructor which reads analysisFile.txt to find which charts are compatible.
	 * 
	 * @throws IOException
	 * 
	 */
	public AnalysisFile() throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			RandomAccessFile file = new RandomAccessFile("analysisFile.txt", "r");
			analysisMethods = new ArrayList<String>();
			countries = new ArrayList<String>();
			viewers = new ArrayList<String>();

			// Get each line from the file and store it inside lines

			String line = file.readLine();

			while (line != null || line == "") {
				lines.add(line);
				line = file.readLine();
			}

			file.close();
		} catch (FileNotFoundException e) {
			new Error("FileNotFoundException:" + e.toString());
			throw new RuntimeException(e);
		} catch (IOException e) {
			new Error("IOException:" + e.toString());
			throw e;
		}

		// Check if the file is a valid length
		if (lines.size() % numberOfArrays != 0) {
			new Error("Invalid analysis file");
			throw new RuntimeException("Invalid analysis file");
		}

		// Place each line into its dedicated array
		for (int i = 0; i < lines.size(); i += numberOfArrays) {
			analysisMethods.add(lines.get(i));
			countries.add(lines.get(i + 1));
			viewers.add(lines.get(i + 2));
		}
	}

	/**
	 * Creates a matrix of strings from 3 lists of data. This will be used to check
	 * the validity of combinations of settings.
	 * 
	 * @return matrix of strings
	 */
	public String[][] getFileData() {
		String[][] ret = new String[analysisMethods.size()][3];
		for (int i = 0; i < analysisMethods.size(); ++i) {
			ret[i][0] = analysisMethods.get(i);
			ret[i][1] = countries.get(i);
			ret[i][2] = viewers.get(i);
		}
		return ret;
	}
}
