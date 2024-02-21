/**
 * Class is an abstract parent class for the viewer, country, and year dropdown. Serves as a template for the observer classes.
 * @author Calvin Nguyen
 */

import javax.swing.JComboBox;

public abstract class DropdownMenu extends JComboBox<String> {
	
	protected int numOptions ;
	
	protected String[] options;
	
	protected int currentOption;
	
	/**
	 * Resets the drop down
	 */
	public void clear() {
		
		currentOption = 0;
		
	}
	
	/**
	 * Sets the option of the dropdown
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