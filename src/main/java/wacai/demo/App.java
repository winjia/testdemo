package wacai.demo;

import wacai.api.AnalysisPdfApi;

public class App {

	public static void main(String[] args) {
		String str = AnalysisPdfApi.getInstance().parsePdfDoc("/Users/wangjj/Downloads/report/11205594001_102_WCW_11205594001.pdf", "dafy");
		System.out.println(str);
	}

}
