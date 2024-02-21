import java.io.IOException;

/**
 * Class verifies if the credentials the user entered is valid and gives access
 * to main UI based on verification results.*
 * 
 * @author Calvin Nguyen
 */
public class CredentialValidator {

	/**
	 * Verifies the entered credentials by comparing with data from login file.
	 * Activates main UI if credentials are valid.
	 * 
	 * @param Username contains the inputed Username
	 * @param Password contains the inputed Password
	 * @throws IOException
	 */
	public static Boolean checkCredentials(String Username, String Password) throws IOException {

		FileAdapter FileData = new FileAdapter();

		// call the getFileData method and make it equal to a created array
		String Data[][] = FileData.getFileData("login");

		for (int i = 0; i < Data[0].length; i++) {

			if (Data[0][i].equals(Username)) {

				if (Data[1][i].equals(Password)) {

					return true;

				}

			}

		}
		return false;

	}

}
