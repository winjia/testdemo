package wacai.dafy;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.alibaba.fastjson.JSON;

import wacai.main.base.Utils;

public class DafyPdfDoc {
	
	private PDDocument doc = null;
	private PDFTextStripperByArea stripper = null;
	private Rectangle pageArea = new Rectangle( 20, 27, 590, 730 );
	private int pageNum = 0;
	
	
	public DafyPdfDoc(){
		
	}
	
	public DafyPdfDoc(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("\""+path+"\" no exist!");
				System.exit(1);
			}
			this.doc = PDDocument.load(file);
			this.stripper = new PDFTextStripperByArea();
			this.stripper.setSortByPosition(true);
			this.pageNum = this.doc.getNumberOfPages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//
	public int getPageNum() {
		return this.pageNum;
	}
	//
	public String extractTextFromPage(int pageNo) {
		this.stripper.addRegion("area", this.pageArea);
		PDPage page = this.doc.getPage(pageNo);
		try {
			this.stripper.extractRegions(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.stripper.getTextForRegion("area");
	}
	//
	public String parsePageText(){
		FieldConf fieldConfVar = new FieldConf();
		//fieldConfVar.parseCnfFile("src/main/resources/dafy.cnf");
		fieldConfVar.parseCnfFile("/dafy.cnf");
		
		String text = this.extractTextFromPage(0);
		//System.out.println(text);
		String[] lines = text.split("\n");
		int len = fieldConfVar.getFieldNum();
		TableElements table = new TableElements();
		for (int i = 0; i < len; i++) {
			FieldNameStruct fieldName = fieldConfVar.getXField(i);
			int xline = fieldName.getX();
			int yline = fieldName.getY();
			String tmpstr = "";
			tmpstr = lines[yline-1];
			String fieldValue = this.getFieldValue(tmpstr, xline, yline);
			table.fillTableField(fieldName.getFieldname(), fieldValue);
		}
		String jsonStr = JSON.toJSONString(table);
		//System.out.println(jsonStr);
		return jsonStr;
	}
	
	private String getFieldValue(String text, int x, int y){
		String[] strs = text.split(" ");
		if (y==12 && strs.length==1) {
			boolean res = Utils.isNumeric(strs[0]);
			if (res && x==1) {
				return strs[0];
			} else if(!res && x==2)
			{
				return strs[0];
			} else {
				return "";
			}
		} else if (y==23 && strs.length==8) {
			return "";
			
		}else if (strs.length < x) {
			System.out.println("[ERROR]: offset overflow the length of text!");
			System.out.println("[ERROR]: text = " + text + "; offset = " + x);
			return "";
		} 
		return strs[x-1];
	}
	
	
	public static void main(String[] args) {
		DafyPdfDoc var = new DafyPdfDoc("/Users/wangjj/Downloads/report/10299097002_102_WCW_10299097002.pdf");
		
		System.out.println("page: " + var.getPageNum());
		var.parsePageText();
		
		
	}

}
