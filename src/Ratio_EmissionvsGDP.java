
/**
 * This is a subclass of Calculation where it takes data from object DataRetreival and finds the ratios between the CO2 Emmisions and GDP per capita storing as a string in an array
 * The array of years is also converted to string and they are sent to DisplayData
 * @author David Lin, Evan Goldrick
 *
 */
public class Ratio_EmissionvsGDP extends Calculation {
	private float[] emissionArray;
	private int[] emissionYears;
	
	private float[] GDPArray;
	private int[] GDPYears;

	private int sizeArray;
	private String[] ratioArray;

	@Override
	public void calculate(DataRetrieval data) {
		emissionYears = data.getYears()[0];
		emissionArray = data.getData()[0];

		GDPYears = data.getYears()[1];
		GDPArray = data.getData()[1];
		
		sizeArray = emissionArray.length;
		ratioArray = new String[sizeArray];
		String[] yearsArray = new String[sizeArray];
		
		for (int i=0; i<sizeArray; i++) {
			if (emissionYears[i]!=-1 && emissionArray[i]!=-1 && GDPYears[i]!=-1 && GDPArray[i]!=-1) {
				ratioArray[i]=Float.toString(emissionArray[i]/GDPArray[i]);
				yearsArray[i]=Integer.toString(emissionYears[i]);
			} else {
				ratioArray[i] = "-1";
				yearsArray[i] = "-1";
			}
		}

		DisplayData.setRatios("Ratio of CO2 Emmisions and GDP per capita", yearsArray, ratioArray);
	}
	
}
