
/**
 * This is a subclass of Calculation where it takes data from object DataRetreival and finds the ratios between the Government Expenditure on Education and Current Health Expenditure storing as a string in an array
 * The array of years is also converted to string and they are sent to DisplayData
 * @author David Lin, Evan Goldrick
 *
 */
public class Ratio_EduExpvsHealthExp extends Calculation{
	private int[] EduExpYears;
	private float[] EduExpArray;

	private int[] HealthExpYears;
	private float[] HealthExpArray;

	private int sizeArray;
	private String[] ratioArray;

	@Override
	public void calculate(DataRetrieval data) {
		EduExpYears = data.getYears()[0];
		EduExpArray = data.getData()[0];

		HealthExpYears = data.getYears()[1];
		HealthExpArray = data.getData()[1];
		
		sizeArray = EduExpArray.length;
		ratioArray = new String[sizeArray];
		String[] yearsArray = new String[sizeArray];
		
		for (int i=0; i<sizeArray; i++) {
			if (EduExpYears[i]!=-1 && EduExpArray[i]!=-1 && HealthExpYears[i]!=-1 && HealthExpArray[i]!=-1) {
				ratioArray[i]=Float.toString(EduExpArray[i]/HealthExpArray[i]);
				yearsArray[i]=Integer.toString(EduExpYears[i]);
			} else {
				ratioArray[i] = "-1";
				yearsArray[i] = "-1";
			}
		}

		DisplayData.setRatios("Ratio of Government Expenditure on Education vs. Current Health Expenditure", yearsArray, ratioArray);
	}

}
