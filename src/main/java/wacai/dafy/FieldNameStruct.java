package wacai.dafy;

public class FieldNameStruct {
	private String fieldname;
	private int x;
	private int y;
	private int cellNo;
	
	public FieldNameStruct(String fieldname, int x, int y) {
		this.fieldname = fieldname;
		this.x = x;
		this.y = y;
	}
	public FieldNameStruct(String fieldname, int x, int y, int cellNo) {
		this.fieldname = fieldname;
		this.x = x;
		this.y = y;
		this.cellNo = cellNo;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
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
	public int getCellNo() {
		return cellNo;
	}
	public void setCellNo(int cellNo) {
		this.cellNo = cellNo;
	}
	
}
