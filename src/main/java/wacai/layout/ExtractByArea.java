package wacai.layout;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class ExtractByArea extends PDFTextStripperByArea{
	private ArrayList<CharElem> charlist = null;
	private Rectangle2D rect = null;
	private String textString = "";
	
	public ExtractByArea() throws IOException{
		super();
		charlist = new ArrayList<CharElem>();
		this.textString = "";
	}
	
	public void setRegion(Rectangle2D rect) {
		this.rect = rect;
	}
	
	public ArrayList<CharElem> getCharList() {
		//System.out.println(this.charlist.size());
		return this.charlist;
	}
	
	
	public String getText() {
		return this.textString;
	}
	
}
