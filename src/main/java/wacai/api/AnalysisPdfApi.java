package wacai.api;

import wacai.dafy.DafyLayout;
import wacai.dafy.DafyPdfDoc;


public class AnalysisPdfApi {
	
	private static AnalysisPdfApi uniqueInstance = null;
	public static AnalysisPdfApi getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new AnalysisPdfApi();
		}
		return uniqueInstance;
	}

	public String parsePdfDoc(String path, String type) {
		if (type.equals("dafy")) {
			//DafyPdfDoc doc = new DafyPdfDoc(path);
			DafyLayout doc = new DafyLayout(path);
			String str = doc.parsePageText();
			return str;
		} 
		return "";
	}
	
}
