package wacai.layout;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.PageDrawer;
import org.apache.pdfbox.rendering.PageDrawerParameters;


public class DocPdfRenderer extends PDFRenderer{
	
	private DocPageDrawer pageDrawer = null;

	public DocPdfRenderer(PDDocument document) {
		super(document);
	}
	
	@Override
    protected PageDrawer createPageDrawer(PageDrawerParameters parameters) throws IOException
    {
		this.pageDrawer = new DocPageDrawer(parameters);
        return this.pageDrawer;
    }
	
	public ArrayList<RectangleStruct> getFillpathRect() {
		return this.pageDrawer.getFillpathRect();
	}
	
	public ArrayList<RectangleStruct> getHlineRect() {
		return this.pageDrawer.getHlineRect();
	}
	
	public ArrayList<RectangleStruct> getVlineRect() {
		return this.pageDrawer.getVlineRect();
	}

}
