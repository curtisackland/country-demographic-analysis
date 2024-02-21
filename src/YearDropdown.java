/**
 * Class represents the viewer drop down on the main UI and is an observer to the analysis drop down.
 * This class is also a singleton where only two instances can be made, one for start year and one for end year.
 * @authors Calvin Nguyen, Evan Goldrick, Curtis Ackland
 */
public class YearDropdown extends DropdownMenu{
	
	private static YearDropdown Instances[] = null;
	
	/**
	 * Constructor initializes values of variables
	 */
	private YearDropdown() {
		
		numOptions = 61;
		
		options = new String[numOptions];
		
		options[0] = "Select Year...";

        int year = 1962;

        //for loop assigns each element of options[] to a String value from the dates 1962 to 2013
        for (int i = 1 ; i < numOptions ; i++) {
            options[i] = String.valueOf(year);
            year++;
        }
		
	}
	
	
	/**
	 * returns either of the unique instances and creates both of them if they haven't been created before.
	 * @param type of year drop down requested. This can be either "start" or "end".
	 * @return the chosen instance of YearDropdown
	 */
	public static YearDropdown getInstance(String type) {
		
		if (Instances == null) {
			Instances = new YearDropdown[2];
			Instances[0] = new YearDropdown();
			Instances[1] = new YearDropdown();
		}
		
		if (type.equals("start")) {
				return Instances[0];
		}
		else {
			return Instances[1];
		}
		
	}
	
}