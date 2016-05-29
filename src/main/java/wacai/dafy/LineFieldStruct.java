package wacai.dafy;

import java.util.ArrayList;

public class LineFieldStruct {
	private ArrayList<String> fields;
	private int fieldnum = 0;
	private int lineRangeStart = 0;
	private int lineRangeEnd = 0;
	
	public LineFieldStruct(String[] lineRange, String[] fields) {
		this.fieldnum = fields.length;
		this.fields = new ArrayList<String>();
		for (int i = 0; i < this.fieldnum; i++) {
			this.fields.add(fields[i]);
		}
		//
		int len = lineRange.length;
		this.lineRangeStart = Integer.parseInt(lineRange[0]);
		this.lineRangeEnd = Integer.parseInt(lineRange[len-1]);
	}
	
	public String getField(int idx){
		return this.fields.get(idx);
	}
	
	public int getFieldNum() {
		return this.fieldnum;
	}
	
	public int getStartLineNo(){
		return this.lineRangeStart;
	}
	
	public int getEndLineNo(){
		return this.lineRangeEnd;
	}
	
	public boolean isContainFields(String text) {
		System.out.println("text1: " + text);
		int cnt = 0;
		System.out.print("text2: ");
		for (int i = 0; i < this.fieldnum; i++) {
			System.out.print(this.fields.get(i)+"===");
			if (text.contains(this.fields.get(i))) {
				cnt++;
			}
		}
		System.out.println();
		if (cnt == this.fieldnum) {
			return true;
		}
		return false;
	}
	
}
