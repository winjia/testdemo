package wacai.layout;

import java.util.ArrayList;

public class TextBlock {
	private String text;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int charNum;
	private int isKey;
	
	public TextBlock() {
		this.text = "";
		this.startX = 0;
		this.startY = 0;
		this.endX = 0;
		this.endY = 0;
		this.charNum = 0;
		this.isKey = 0;
	}
	
	public TextBlock(ArrayList<CharElem> charlist, int startx, int endx){
		this.startX = charlist.get(startx).getX();
		this.startY = charlist.get(startx).getY();
		this.endX = charlist.get(endx).getX() + charlist.get(endx).getWidth();
		this.endY = charlist.get(endx).getY() - charlist.get(endx).getHeight();
		this.charNum = endx - startx + 1;
		this.text = "";
		for (int i = startx; i <= endx; i++) {
			this.text += charlist.get(i).getAchar();
		}
		this.isKey = 0;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public int getCharNum() {
		return charNum;
	}

	public void setCharNum(int charNum) {
		this.charNum = charNum;
	}

	public int getIsKey() {
		return isKey;
	}

	public void setIsKey(int isKey) {
		this.isKey = isKey;
	}
	
}
