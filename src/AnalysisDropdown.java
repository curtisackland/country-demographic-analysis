/**
 * Class that represents the analysis drop down menu. This class is the subject of the other drop downs within the observer design pattern. 
 * @author Calvin Nguyen
 */
public class AnalysisDropdown extends Subject{
	
	private int numOptions ;
	
	private String[] options;
	
	private int currentOption;
	
	/**
	 * Constructor initializes values of variables
	 */
	public AnalysisDropdown() {
		
		numOptions = 9;
		
		options = new String[9];
		
		//List of options
		options[0] = "Select Analysis...";
		options[1] = "CO2 Emission vs. Energy Use vs. Air Pollution";
		options[2] = "Air Pollution vs. Forest Area";		
		options[3] = "Ratio of CO2 Emmisions and GDP per capita";
		options[4] = "Avg. Forest Area";		
		options[5] = "Avg. Government Expenditure on Education";
		options[6] = "Ratio of Hospital beds and Current Health Expenditure";		
		options[7] = "Current Health expenditure per capita vs. Infant Mortality Rate";
		options[8] = "Ratio of Government Expenditure on Education vs. Current Health Expenditure";		
		
		//Array for observers are notified
		dropDownArray = new DropdownMenu[4];
		
		dropDownArray[0] = ViewerDropdown.getInstance();
		dropDownArray[1] = CountryDropdown.getInstance();
		dropDownArray[2] = YearDropdown.getInstance("start");
		dropDownArray[3] = YearDropdown.getInstance("end");
		
		
	}
	
	/**
	 * resets the other dropdowns on the Main UI
	 */
	public void clear() {
		for (int i = 0 ; i < 4; i++) {
			dropDownArray[i].clear();
		}
	}
	
	/**
	 * Sets the option of the analysis dropdown
	 * @param index of the newly picked option
	 */
	public void setOption (int index) {
		currentOption = index;
	}
	
	/**
	 * Gets the index of the current option picked
	 * @return the index of the current option picked
	 */
	public int getCurrentOptionIndex () {
		return currentOption;
	}
	
	/**
	 * Gets the option name of a passed index
	 * @param index of the option stored in the option array
	 * @return String of the selected option
	 */
	public String getOption(int index) {
		return options[index];
	}
	
	/**
	 * Gets the number of options available 
	 * @return number of options
	 */
	public int getNumOptions() {
		return numOptions;
	}
	
}
