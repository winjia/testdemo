package wacai.dafy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.alibaba.fastjson.JSON;

import wacai.layout.CharElem;
import wacai.layout.ExtractTextByAreaNew;
import wacai.layout.RectangleStruct;
import wacai.layout.TableCellLayout;
import wacai.layout.TextBlock;

public class DafyLayoutNew {
	
	TableCellLayout tableLayout = null;
	ExtractTextByAreaNew extractor = null;
	ArrayList<RectangleStruct> cellList = null;
	private String path = "";
	
	public DafyLayoutNew(String path){
		this.tableLayout = new TableCellLayout();
		this.extractor = new ExtractTextByAreaNew();
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
		for (int i = 0; i < 1; i++) {
			RectangleStruct elem = this.cellList.get(i);
			String text = this.extractor.getTextByArea(doc, 0, elem.getRect(-offset));//(-23-774));
			//System.out.println(text);
			ArrayList<CharElem> charlist = this.extractor.getCharList();
			ArrayList<String> fieldlist = new ArrayList<String>();
			//fieldlist.add("现单位学校地址省直辖市");
			//fieldlist.add("市");
			//fieldlist.add("销售点地址");
			fieldlist.add("合同号");
			fieldlist.add("申请日期");
			ArrayList<TextBlock> blocklist = this.layoutField(charlist, fieldlist);
			ArrayList<TextBlock> blocklist2 = this.mergeFieldBlock(blocklist);
			System.out.println("after merge size = " + blocklist2.size());
			
			//System.out.println(text);
			//
			//textList.add(text);
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
		int y1 = cellList.get(0).getY();
		int y2 = cellList.get(len-1).getTopY();
		return (y1+y2);
	}
	//根据得到每个字符的坐标来划分字段名和字段值
	private ArrayList<TextBlock> layoutField(ArrayList<CharElem> charlist, ArrayList<String> fieldlist){
		this.sortCharByXY(charlist);
		int len = charlist.size();
		boolean flg = false;
		int startx = 0;
		ArrayList<TextBlock> blockList = new ArrayList<TextBlock>();
		for (int i = 0; i < len-1; i++) {
			CharElem ch1 = charlist.get(i);
			CharElem ch2 = charlist.get(i+1);
			int distX = ch2.getX() - ch1.getX() - ch1.getWidth();
			int distY = ch2.getY() - ch1.getY() - ch1.getHeight();
			//System.out.println(charlist.get(i).getAchar()+","+charlist.get(i+1).getAchar()+"="+distX+","+distY);
			//行内分割
			if (i==0) {
				startx = 0;
				flg = true;
			}else if (distX>2 && distY<4 && flg==true) {
				TextBlock block = new TextBlock(charlist, startx, i);
				int type = this.isField(block.getText(), fieldlist)?1:0;
				block.setIsKey(type);
				blockList.add(block);
				//flg = false;
				startx = i + 1;
				System.out.println(block.getText());
			}else if (distY>5 && flg==true) {
				TextBlock block = new TextBlock(charlist, startx, i);
				int type = this.isField(block.getText(), fieldlist)?1:0;
				block.setIsKey(type);
				blockList.add(block);
				//flg = false;
				startx = i + 1;
				System.out.println(block.getText());
			} 
			if (i+1 == len-1) {
				TextBlock block = new TextBlock(charlist, startx, len-1);
				int type = this.isField(block.getText(), fieldlist)?1:0;
				block.setIsKey(type);
				blockList.add(block);
				System.out.println(block.getText());
			}
		}
		System.out.println("block size = " + blockList.size());
		//
		return blockList;
	}
	
	private void makeupFieldStruct(ArrayList<TextBlock> blocklist, ArrayList<String> fields){
		int len = blocklist.size();
		boolean[] flgs = new boolean[len];
		for (int i = 0; i < len; i++) {
			flgs[i] = false;
		}
		for (int i = 0; i < fields.size(); i++) {
			String field = fields.get(i);
			for (int j = 0; j < len; j++) {
				
			}
		}
	}
	//合并字段的textblock
	private ArrayList<TextBlock> mergeFieldBlock(ArrayList<TextBlock> blocklist){
		ArrayList<TextBlock> tmplist = new ArrayList<TextBlock>();
		int len = blocklist.size();
		boolean[] flags = new boolean[len];
		for (int i = 0; i < len; i++) {
			flags[i] = false;
		}
		for (int i = 0; i < len; i++) {
			//flags[i] = false;
			for (int j = 0; j < len; j++) {
				if (i>=j) {
					continue;
				}
				int flg = this.isBlockMerge(blocklist.get(i), blocklist.get(j));
				//System.out.println(blocklist.get(i).getText()+"-=-"+blocklist.get(j).getText());
				if (flg==0) {
					continue;
				}
				tmplist.add(this.mergeBlock(blocklist.get(i), blocklist.get(j), flg));
				flags[i] = true;
				flags[j] = true;
			}
		}
		for (int i = 0; i < len; i++) {
			if (flags[i]) {
				continue;
			}
			tmplist.add(blocklist.get(i));
		}
		return tmplist;
	}
	//合并两个block
	private int isBlockMerge(TextBlock block1, TextBlock block2){
	
		if (block1.getIsKey()==0 || block2.getIsKey()==0) {
			return 0;
		}
		//横着
		int distX1 = Math.abs(block1.getEndX() -block2.getStartX());
		int distY1 = Math.abs(block1.getStartY() - block2.getStartY());
		//竖着
		int distX2 = Math.abs(block1.getStartX() -block2.getStartX());
		int distY2 = Math.abs(block1.getStartY() - block2.getEndY());
		if (distX1<8 && distY1<2) { //横着
			return 1;
		} else if (distX2<2 && distY2<10){ //竖着
			return 2;
		}
		return 0;
	}
	private TextBlock mergeBlock(TextBlock block1, TextBlock block2, int pos){
		TextBlock var = new TextBlock();
		if (pos==1) {
			var.setIsKey(1);
			var.setStartX(block1.getStartX());
			var.setStartY(block1.getStartY());
			var.setEndX(block2.getEndX());
			var.setEndY(block2.getEndY());
			var.setCharNum(block1.getCharNum() + block2.getCharNum());
			var.setText(block1.getText()+block2.getText());
		}
		if (pos==2) {
			var.setIsKey(1);
			var.setStartX(block1.getStartX());
			var.setStartY(block1.getStartY());
			var.setEndX(block2.getEndX());
			var.setEndY(block2.getEndY());
			var.setCharNum(block1.getCharNum() + block2.getCharNum());
			var.setText(block1.getText()+block2.getText());
		}
		return var;
	}
	
	//判断是否为字段名
	private boolean isField(String name, ArrayList<String> namelist){
		int len = namelist.size();
		for (int i = 0; i < len; i++) {
			boolean flg = this.isFieldNameSimilar(name, namelist.get(i));
			if (flg) {
				return true;
			}
		}
		return false;
	}
	//name2 标注字段名
	private boolean isFieldNameSimilar(String name1, String name2){
		String tmpstr = name1.replaceAll("[\\pP\\pN\\pZ‘’“”]", "");
		//System.out.println("cmp="+name2+";="+tmpstr);
		if (name2.contains(tmpstr)) {
			return true;
		}
		
		return false;
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
	
	//将字符根据坐标按行排列
	private void sortCharByXY(ArrayList<CharElem> list){
		Collections.sort(list, new Comparator<CharElem>() {
	        public int compare(CharElem arg0, CharElem arg1) {
	            float tmp1 = arg1.getX() - arg0.getX();
	            float tmp2  = arg1.getY() - arg0.getY();
	            if (Math.abs(tmp2)<=3 && tmp1>0) {
	            	return -1;
				} else if (Math.abs(tmp2)<=3 && tmp1<0) {
					return 1;
				} else if (tmp2 > 3) {
					return -1;
				} else if (tmp2 < -3) {
					return 1;
				}
	            return 0;
	        }
	    });
	}
	
	
	public static void main(String[] args) {
		DafyLayoutNew layout = new DafyLayoutNew("/Users/wangjj/Downloads/report/11205594001_102_WCW_11205594001.pdf");
		layout.parsePageText();
	}

}
