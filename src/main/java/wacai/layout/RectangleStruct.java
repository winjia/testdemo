package wacai.layout;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class RectangleStruct {
	private int width;
	private int height;
	private int x;
	private int y;
	
	public RectangleStruct(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public RectangleStruct(Rectangle2D box) {
		this.x = (int)box.getX();
		this.y = (int)box.getY();
		this.width = (int)box.getWidth();
		this.height = (int)box.getHeight();
		if (this.width == 0) {
			this.y -= 2;
			this.height += 5;
			//
			
			
		}
		if (this.height == 0) {
			this.x -= 2;
			this.width += 5;
		}
	}
	
	public Rectangle getRect(int offset){
		int my = this.y + offset;
		return new Rectangle(this.x, -my, this.width, -this.height);
	}
	
	public int getTopY() {
		if (this.height == 0) {
			return this.y;
		}
		return (this.y+this.height-1);
		//return (this.y-this.height - 1);
	}
	
	public int getRightX() {
		if (this.width == 0) {
			return this.x;
		}
		return (this.x+this.width-1);
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
	
}
