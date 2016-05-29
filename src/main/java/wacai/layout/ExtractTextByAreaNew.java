package wacai.layout;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class ExtractTextByAreaNew {
	
	private ExtractByAreaNew stripper = null;
	
	public ExtractTextByAreaNew() {
		try {
			this.stripper = new ExtractByAreaNew();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTextByArea(PDDocument doc, int pageNo, Rectangle rect) {
        stripper.addRegion( "rect", rect );
        PDPage firstPage = doc.getPage(pageNo);
        try {
        	stripper.setSortByPosition(true);
			stripper.extractRegions( firstPage );
		} catch (IOException e) {
			e.printStackTrace();
		}
        return stripper.getTextForRegion("rect");
        //return stripper.getText();
	}
	
	public ArrayList<CharElem> getCharList() {
		return this.stripper.getCharList();
	}
	
	
    

}
