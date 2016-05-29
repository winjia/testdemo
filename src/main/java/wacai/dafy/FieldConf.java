package wacai.dafy;

import java.util.ArrayList;

import wacai.main.base.Utils;

public class FieldConf {
	
	private int fieldNum = 0;
	private ArrayList<FieldNameStruct> fieldList = null;
	
	public FieldConf(){
		this.fieldList = new ArrayList<FieldNameStruct>();
	}
	
	public void parseCnfFile(String path) {
		//ArrayList<String> textList = Utils.getTextFromFile(path);
		ArrayList<String> textList = new Utils().readPropertiesFile(path);
		this.fieldNum = textList.size();
		//System.out.println("line = " + this.fieldNum);
		for (int i = 0; i < this.fieldNum; i++) {
			String text = textList.get(i);
			if (text.startsWith("#")) {
				continue;
			}
			String[] strs = text.split("=");
			if (strs.length != 2) {
				System.out.println("[ERROR]: The configure format of this line is error!");
				System.out.println("Text: "+text);
				continue;
			}
			String[] leftstrs = strs[0].split(",");
			int y = 0, x = 0, cellNo = 0;
			y = Integer.parseInt(leftstrs[0]);
			if (leftstrs.length == 3) {
				cellNo = Integer.parseInt(leftstrs[0]);
				y = Integer.parseInt(leftstrs[1]);
				x = Integer.parseInt(leftstrs[2]);
			} else if (leftstrs.length == 2){
				cellNo = 0;
				y = Integer.parseInt(leftstrs[0]);
				x = Integer.parseInt(leftstrs[1]);
			}
			FieldNameStruct elem = new FieldNameStruct(strs[1], x, y, cellNo);
			this.fieldList.add(elem);
		}
	}
	
	public int getFieldNum() {
		return this.fieldNum;
	}
	
	public FieldNameStruct getXField(int idx) {
		if (idx > this.fieldNum) {
			System.out.println("[ERROR]: "+idx+" overflow the max line limit " + this.fieldNum+"!");
			return null;
		}
		return this.fieldList.get(idx);
	}

}
