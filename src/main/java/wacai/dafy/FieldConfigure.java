package wacai.dafy;

import java.util.ArrayList;

import wacai.main.base.Utils;

public class FieldConfigure {
	private ArrayList<LineFieldStruct> docConfigure;
	private int lineNum = 0;
	
	public FieldConfigure() {
		this.docConfigure = new ArrayList<LineFieldStruct>();
	}
	
	public void parseCnfFile(String path) {
		ArrayList<String> textList = Utils.getTextFromFile(path);
		this.lineNum = textList.size();
		System.out.println("line = " + this.lineNum);
		for (int i = 0; i < this.lineNum; i++) {
			String text = textList.get(i);
			if (text.startsWith("#")) {
				continue;
			}
			String[] strs = text.split("=");
			if (strs.length != 2) {
				System.out.println("");
				continue;
			}
			//left
			String[] leftstrs = strs[0].split(";");
			//right
			String[] rightstrs = strs[1].split(";");
			LineFieldStruct elem = new LineFieldStruct(leftstrs, rightstrs);
			this.docConfigure.add(elem);
		}
	}
	
	public boolean containFields(int idx, String text){
		if (idx > this.lineNum) {
			System.out.println(idx+" overflow the max line limit " + this.lineNum+"!");
			return false;
		}
		LineFieldStruct elem = this.getXLine(idx);
		return elem.isContainFields(text);
	}
	
	public int getLineNum() {
		return this.lineNum;
	}
	
	public LineFieldStruct getXLine(int idx) {
		if (idx > this.lineNum) {
			System.out.println(idx+" overflow the max line limit " + this.lineNum+"!");
			return null;
		}
		return this.docConfigure.get(idx);
	}
	
	public String getXyField(int yline, int xfield) {
		if (yline > this.lineNum) {
			System.out.println(yline+" overflow the max line limit " + this.lineNum+"!");
			return null;
		}
		LineFieldStruct elem = this.docConfigure.get(yline);
		if (xfield > elem.getFieldNum()) {
			System.out.println(yline+" overflow the max filed num " + elem.getFieldNum()+"!");
			return null;
		}
		return elem.getField(xfield);
	}
	
//	public String[] getLineFields(int yline) {
//		
//	}
	
	
	
}
