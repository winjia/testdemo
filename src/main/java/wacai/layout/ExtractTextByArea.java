package wacai.layout;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class ExtractTextByArea {
	
	private ExtractByArea stripper = null;
	
	public ExtractTextByArea() {
		try {
			this.stripper = new ExtractByArea();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTextByArea(PDDocument doc, int pageNo, Rectangle rect) {
        this.stripper.addRegion( "rect", rect );
        this.stripper.setRegion(rect);
        PDPage docPage = doc.getPage(pageNo);
        try {
        	stripper.setSortByPosition(true);
			stripper.extractRegions( docPage );
		} catch (IOException e) {
			e.printStackTrace();
		}
        return stripper.getTextForRegion("rect");
        //return stripper.getText();
	}


}
