import java.io.IOException;

public class FileAdapter extends ReadFile {

	private LoginFile loginFile = new LoginFile();
	private AnalysisFile analysisFile = new AnalysisFile();

	public FileAdapter() throws IOException {

	}

	public String[][] getFileData(String Request) throws IOException {
		if (Request.equals("login")) {
			return loginFile.getFileData();
		} else {
			return analysisFile.getFileData();
		}
	}

}
