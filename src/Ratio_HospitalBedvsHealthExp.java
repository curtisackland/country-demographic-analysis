
/**
 * This is a subclass of Calculation where it takes data from object DataRetreival and finds the ratios between Hospital beds and Current Health Expenditure storing as a string in an array
 * The array of years is also converted to string and they are sent to DisplayData
 * @author David Lin, Evan Goldrick
 *
 */
public class Ratio_HospitalBedvsHealthExp extends Calculation {
	private int[] HospitalBedYears;
	private float[] HospitalBedArray;

	private int[] HealthExpYears;
	private float[] HealthExpArray;

	private int sizeArray;
	private String[] ratioArray;

	@Override
	public void calculate(DataRetrieval data) {
		HospitalBedYears = data.getYears()[0];
		HospitalBedArray = data.getData()[0];

		HealthExpYears = data.getYears()[1];
		HealthExpArray = data.getData()[1];
		
		// Turns HealthExpArray from per capita to per 1000 people
		for (int i=0; i<sizeArray;i++) {
			if (HealthExpArray[i]!=-1) {
				HealthExpArray[i]*=1000;
			}
		}
		
		sizeArray = HospitalBedArray.length;
		ratioArray = new String[sizeArray];
		String[] yearsArray = new String[sizeArray];
		
		for (int i=0; i<sizeArray; i++) {
			if (HospitalBedYears[i]!=-1 && HospitalBedArray[i]!=-1 && HealthExpYears[i]!=-1 && HealthExpArray[i]!=-1) { 
				ratioArray[i]=Float.toString(HospitalBedArray[i]/HealthExpArray[i]);
				yearsArray[i]=Integer.toString(HospitalBedYears[i]);
			} else {
				ratioArray[i] = "-1";
				yearsArray[i] = "-1";
			}
		}

		DisplayData.setRatios("Ratio of Hospital beds and Current Health Expenditure", yearsArray, ratioArray);
	}
	
}
