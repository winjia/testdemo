package wacai.layout;

public class CharElem {
	private String achar;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public CharElem(String achar, int x, int y, int width, int height) {
		this.achar = achar;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public String getAchar() {
		return achar;
	}

	public void setAchar(String achar) {
		this.achar = achar;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
