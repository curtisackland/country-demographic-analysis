
/**
 * This is a subclass of Calculation where it takes data from object
 * DataRetreival and finds the average Forest Area from the float array The data
 * is sent to DisplayData
 * 
 * @author David Lin, Evan Goldrick
 *
 */
public class AvgForestArea extends Calculation {
	private int[] yearArray;
	private float[] areaArray;
	private float average;

	@Override
	public void calculate(DataRetrieval data) {
		yearArray = data.getYears()[0];
		areaArray = data.getData()[0];

		int total = 0;
		int count = 0;
		for (int i = 0; i < areaArray.length; i++) {
			if (yearArray[i] != -1 && areaArray[i] != -1) {
				total += areaArray[i];
				count += 1;
			}
		}
		
		if (count == 0)
			average = -1;
		else
			average = total / count;

		String dataToSend = Float.toString(average);
		DisplayData.setAvg("Avg. Forest Area", dataToSend);
	}
}