package wacai.dafy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.alibaba.fastjson.JSON;

import wacai.layout.CharElem;
import wacai.layout.ExtractTextByArea;
import wacai.layout.RectangleStruct;
import wacai.layout.TableCellLayout;
import wacai.layout.TextBlock;

public class DafyLayout {
	
	TableCellLayout tableLayout = null;
	ExtractTextByArea extractor = null;
	ArrayList<RectangleStruct> cellList = null;
	private String path = "";
	
	public DafyLayout(String path){
		this.tableLayout = new TableCellLayout();
		this.extractor = new ExtractTextByArea();
		this.path = path;
	}
	
	
	public String parsePageText(){
		File file = new File(path);
		PDDocument doc = null;
		try {
			doc = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cellList = this.tableLayout.getTableCellLayout(doc);
		//System.out.println(this.cellList.size());
		int len = this.cellList.size();
		int offset = this.calcPageOffsetY(this.cellList, len);
		//System.out.println(offset);
		ArrayList<String> textList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			RectangleStruct elem = this.cellList.get(i);
			String text = this.extractor.getTextByArea(doc, 0, elem.getRect(-offset));//(-23-774));
			//System.out.println(text);
			//ArrayList<CharElem> charlist = this.extractor.getCharList();
			//
			textList.add(text);
		}
		FieldConf fieldConfVar = new FieldConf();
		fieldConfVar.parseCnfFile("/dafy_layout.cnf");
		len = fieldConfVar.getFieldNum();
		TableElements table = new TableElements();
		for (int i = 0; i < len; i++) {
			FieldNameStruct fieldName = fieldConfVar.getXField(i);
			int cellNo = fieldName.getCellNo();
            int xline = fieldName.getX();
            int yline = fieldName.getY();
            String fieldValue = this.getFieldValue(textList.get(cellNo-1), xline, yline, cellNo);
			table.fillTableField(fieldName.getFieldname(), fieldValue);
			
		}
		String jsonStr = JSON.toJSONString(table);
		System.out.println(jsonStr);
		return jsonStr;
	}
	
	//计算整个页面的偏移量
	private int calcPageOffsetY(ArrayList<RectangleStruct> cellList, int len){
		//int len = cellList.size();
		int y1 = cellList.get(0).getY();
		int y2 = cellList.get(len-1).getTopY();
		//System.out.println(cellList.get(0).getTopY());
		//System.out.println(cellList.get(len-1).getTopY());
		return (y1+y2);
	}
	
	private String getFieldValue(String text, int x, int y, int cellNo){
		String[] texts = text.split("\n");
		int len = texts.length;
		int idx = 1;
		String str = "";
		for (int i = 0; i < len; i++) {
			if (texts[i].length() == 0) {
				continue;
			}
			if (idx == y) {
				str = texts[i];
				break;
			}
			idx++;
		}
		String[] strs = str.split(" ");
//		for (int i = 0; i < strs.length; i++) {
//			System.out.print(strs[i]+"=");
//		}
//		System.out.println();
		if (cellNo==40 && strs.length<10) {
			return "";
		}
		if (strs.length < x) {
			return "";
		}
		if (strs.length==2 && x==1 && strs[0].equals("")) {
			return strs[1];
		}
		return strs[x-1];
	}
	
	public static void main(String[] args) {
		DafyLayout layout = new DafyLayout("/Users/wangjj/Downloads/report/11205594001_102_WCW_11205594001.pdf");
		layout.parsePageText();
	}

}
