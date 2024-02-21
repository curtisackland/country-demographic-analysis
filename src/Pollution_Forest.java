
/**
 * This is a subclass of Calculation where it takes data from object DataRetreival and converts the data into 2D string arrays to be sent to DisplayData
 * @author David Lin
 *
 */
public class Pollution_Forest extends Calculation {
	private String[][] yearsArray;
	private String[][] dataArray;
	
	@Override
	public void calculate(DataRetrieval data) {
		yearsArray = new String[data.getYears().length][data.getYears()[0].length];
		dataArray = new String[data.getData().length][data.getData()[0].length];
		
		for (int i=0; i<data.getYears().length;i++) {
			for (int j=0; j<data.getYears()[i].length;j++) {
				yearsArray[i][j] = Integer.toString(data.getYears()[i][j]);
				dataArray[i][j] = Float.toString(data.getData()[i][j]);
			}
		}
		
		DisplayData.setData("Air Pollution vs. Forest Area",yearsArray,dataArray);
	}
}
