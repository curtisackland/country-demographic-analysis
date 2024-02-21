
/**
 * This is a subclass of Calculation where it takes data from object
 * DataRetreival and finds the average Government Expenditure on Education from
 * the float array The data is sent to DisplayData
 * 
 * @author David Lin, Evan Goldrick
 *
 */
public class AvgEduGDP extends Calculation {
	private int[] yearArray;
	private float[] EduGDPArray;
	private float average;

	@Override
	public void calculate(DataRetrieval data) {
		yearArray = data.getYears()[0];
		EduGDPArray = data.getData()[0];

		int total = 0;
		int count = 0;
		for (int i = 0; i < EduGDPArray.length; i++) {
			if (yearArray[i] != -1 && EduGDPArray[i] != -1) {
				total += EduGDPArray[i];
				count += 1;
			}
		}

		if (count == 0)
			average = -1;
		else
			average = total / count;

		String dataToSend = Float.toString(average);
		DisplayData.setAvg("Avg. Government Expenditure on Education", dataToSend);
	}

}
