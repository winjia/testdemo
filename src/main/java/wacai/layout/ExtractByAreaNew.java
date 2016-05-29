package wacai.layout;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class ExtractByAreaNew extends PDFTextStripperByArea{
	private ArrayList<CharElem> charlist = null;
	private Rectangle2D rect = null;
	private String textString = "";
	
	public ExtractByAreaNew() throws IOException{
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
	
	public void clearCharList() {
		this.charlist.clear();
		//System.out.println(this.textString);
		this.textString = "";
	}
	
	public String getText() {
		return this.textString;
	}
	
	
	@Override
    protected void processTextPosition( TextPosition text ){
//		if (text.getUnicode().equals(" ")) {
//			System.out.println("asfafsaf=" + text.getUnicode());
//			return;
//		}
		 if( this.rect.contains( text.getX(), text.getY() ) )
         {
			 //System.out.println(text.getUnicode());
			 this.charlist.add(new CharElem(text.getUnicode(), (int)text.getX(), (int)text.getY(), (int)text.getWidth(), (int)text.getHeight()));
			 this.textString += text.getUnicode();
         }
		
	}
}
