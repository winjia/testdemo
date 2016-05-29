package wacai.layout;


import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.rendering.PageDrawer;
import org.apache.pdfbox.rendering.PageDrawerParameters;

public class DocPageDrawer extends PageDrawer{
	private final int tolerance = 1;
	private ArrayList<RectangleStruct>  fillRectList = null;
	private ArrayList<RectangleStruct>  hLineList = null;
	private ArrayList<RectangleStruct>  vLineList = null;

	public DocPageDrawer(PageDrawerParameters parameters) throws IOException {
		super(parameters);
		this.fillRectList = new ArrayList<RectangleStruct>();
		this.hLineList = new ArrayList<RectangleStruct>();
		this.vLineList = new ArrayList<RectangleStruct>();
	}
	
	@Override
    public void fillPath(int windingRule) throws IOException
    {
        Shape bbox = getLinePath().getBounds2D();            
        //System.out.print("fillpath:");
        //System.out.println(bbox);
        this.fillRectList.add(new RectangleStruct(bbox.getBounds2D()));
        super.fillPath(windingRule);
        getLinePath().reset();
    }
	
	@Override
	public void strokePath() throws IOException
    {
        Shape bbox = getLinePath().getBounds2D();
        //double x = bbox.getBounds2D().getX();
        //double y = bbox.getBounds2D().getY();
        double width = bbox.getBounds2D().getWidth();
        double height = bbox.getBounds2D().getHeight();
        //horizon
        if (height < this.tolerance) {
        	this.hLineList.add(new RectangleStruct(bbox.getBounds2D()));
		}
        //vertical
        if(width < this.tolerance){
        	this.vLineList.add(new RectangleStruct(bbox.getBounds2D()));
        }
        //System.out.print("stroke:");
        //System.out.println(bbox);
        getLinePath().reset();
    }
	//
	public ArrayList<RectangleStruct> getFillpathRect() {
		return this.fillRectList;
	}
	
	public ArrayList<RectangleStruct> getHlineRect() {
		return this.hLineList;
	}
	
	public ArrayList<RectangleStruct> getVlineRect() {
		return this.vLineList;
	}
	

}
