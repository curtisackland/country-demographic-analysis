/**
 * Class represents the country drop down on the main UI and is an observer to
 * the analysis drop down. This class is also a singleton.
 * 
 * @author Calvin Nguyen
 */
public class CountryDropdown extends DropdownMenu {

	private static CountryDropdown uniqueInstance = null;

	/**
	 * Constructor initializes values of variables
	 */
	private CountryDropdown() {

		numOptions = 6;

		options = new String[6];

		// List of options
		options[0] = "Select Country...";
		options[1] = "Canada";
		options[2] = "USA";
		options[3] = "Russia";
		options[4] = "Sri Lanka";
		options[5] = "Egypt";

	}

	/**
	 * returns the unique instance and creates one if it hasn't been created before.
	 * 
	 * @return the unique instance
	 */
	public static CountryDropdown getInstance() {

		if (uniqueInstance == null) {
			uniqueInstance = new CountryDropdown();
		}

		return uniqueInstance;
	}

}
