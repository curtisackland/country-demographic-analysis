/**
 * Class represents the viewer drop down on the main UI and is an observer to the analysis drop down. This class is also a singleton.
 * @author Calvin Nguyen
 */
public class ViewerDropdown extends DropdownMenu {
	
	private static ViewerDropdown uniqueInstance = null;
	
	/**
	 * Constructor initializes values of variables
	 */
	private ViewerDropdown() {
		
		numOptions = 6;
		
		options = new String[6];
		
		//List of options
		options[0] = "Select Viewer...";
		options[1] = "Pie Chart";
		options[2] = "Line Chart";		
		options[3] = "Bar Chart";
		options[4] = "Scatter Chart";		
		options[5] = "Report";

		
	}
	
	/**
	 * returns the unique instance and creates one if it hasn't been created before.
	 * @return the unique instance
	 */
	public static ViewerDropdown getInstance() {
		
		if (uniqueInstance == null) {
			uniqueInstance = new ViewerDropdown();
		}
		
		return uniqueInstance;
	}
	
}
