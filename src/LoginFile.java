
/**
 * The loginFile class reads and processes the data within the login file, ready to be used by the validator.
 * @author Calvin Nguyen
*/

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LoginFile {

	// the file
	private RandomAccessFile LOGIN_FILE;

	// Data
	private String FileData[][];

	/**
	 * constructor of the class that processes and formats data within the file as a
	 * 2D array
	 * 
	 * @throws IOException
	 */
	public LoginFile() throws IOException {

		try {

			LOGIN_FILE = new RandomAccessFile("loginFile.txt", "r");

		}

		// If there is no login file
		catch (FileNotFoundException e) {

			new Error("No Login File containing credentials");
			throw new RuntimeException(e);

		}

		// if there was an error reading the login file
		catch (IOException e) {

			new Error("Error reading Login File");
			throw new RuntimeException(e);

		}

		int LineNum = 0;

		// counts number of lines
		while (LOGIN_FILE.readLine() != null) {

			LineNum++;

		}

		// if the file is formatted incorrectly
		if (LineNum % 2 != 0) {

			new Error("Login File not formatted correctly");
			throw new RuntimeException("Login File not formatted correctly");

		}

		FileData = new String[2][LineNum / 2];

		// reset file pointer to beginning
		LOGIN_FILE.seek(0);

		// assigns usernames to row 0 of array and passwords to row 1
		for (int i = 0; i < LineNum / 2; i++) {

			FileData[0][i] = LOGIN_FILE.readLine();

			FileData[1][i] = LOGIN_FILE.readLine();

		}

		LOGIN_FILE.close();
	}

	/**
	 * Returns the File Data
	 * 
	 * @return 2D array containing valid credentials
	 * @throws IOException
	 */
	public String[][] getFileData() throws IOException {

		return FileData;

	}

}
