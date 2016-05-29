package wacai.layout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.fontbox.util.BoundingBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType3Font;
import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;


public class TextPosLocation extends PDFTextStripper{
	private BufferedImage image;
    private AffineTransform flipAT;
    private AffineTransform rotateAT;
    static final int SCALE = 4;
    //private Graphics2D g2d;
    private final PDDocument document;

    
	public TextPosLocation(PDDocument document) throws IOException {
		super();
		this.document = document;
	}
	
	private void stripPage(int page) throws IOException
    {
//        PDFRenderer pdfRenderer = new PDFRenderer(document);
//        image = pdfRenderer.renderImage(page, SCALE);
//        
//        PDPage pdPage = document.getPage(page);
//        PDRectangle cropBox = pdPage.getCropBox();
//
//        // flip y-axis
//        flipAT = new AffineTransform();
//        flipAT.translate(0, pdPage.getBBox().getHeight());
//        flipAT.scale(1, -1);
//
//        // page may be rotated
//        rotateAT = new AffineTransform();
//        int rotation = pdPage.getRotation();
//        if (rotation != 0)
//        {
//            PDRectangle mediaBox = pdPage.getMediaBox();
//            switch (rotation)
//            {
//                case 90:
//                    rotateAT.translate(mediaBox.getHeight(), 0);
//                    break;
//                case 270:
//                    rotateAT.translate(0, mediaBox.getWidth());
//                    break;
//                case 180:
//                    rotateAT.translate(mediaBox.getWidth(), mediaBox.getHeight());
//                    break;
//                default:
//                    break;
//            }
//            rotateAT.rotate(Math.toRadians(rotation));
//        }

//        g2d = image.createGraphics();
//        g2d.setStroke(new BasicStroke(0.1f));
//        g2d.scale(SCALE, SCALE);
//
//        setStartPage(page + 1);
//        setEndPage(page + 1);
//
        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        writeText(document, dummy);
//        
//        // beads in green
//        g2d.setStroke(new BasicStroke(0.4f));
//        List<PDThreadBead> pageArticles = pdPage.getThreadBeads();
//        for (PDThreadBead bead : pageArticles)
//        {
//            PDRectangle r = bead.getRectangle();
//            GeneralPath p = r.transform(Matrix.getTranslateInstance(-cropBox.getLowerLeftX(), cropBox.getLowerLeftY()));
//
//            Shape s = flipAT.createTransformedShape(p);
//            s = rotateAT.createTransformedShape(s);
//            g2d.setColor(Color.green);
//            g2d.draw(s);
//        }
//
//        g2d.dispose();
//
//        String imageFilename = filename;
//        int pt = imageFilename.lastIndexOf('.');
//        imageFilename = imageFilename.substring(0, pt) + "-marked-" + (page + 1) + ".png";
//        ImageIO.write(image, "png", new File(imageFilename));
    }

    /**
     * Override the default functionality of PDFTextStripper.
     */
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
    	System.out.println("size = "  +textPositions.size());
        for (TextPosition text : textPositions)
        {
            System.out.println("String[" + text.getXDirAdj() + ","
                    + text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale="
                    + text.getXScale() + " height=" + text.getHeightDir() + " space="
                    + text.getWidthOfSpace() + " width="
                    + text.getWidthDirAdj() + "]" + text.getUnicode());

            // in red:
            // show rectangles with the "height" (not a real height, but used for text extraction 
            // heuristics, it is 1/2 of the bounding box height and starts at y=0)
           /* Rectangle2D.Float rect = new Rectangle2D.Float(
                    text.getXDirAdj(),
                    (text.getYDirAdj() - text.getHeightDir()),
                    text.getWidthDirAdj(),
                    text.getHeightDir());
            g2d.setColor(Color.red);
            g2d.draw(rect);

            // in blue:
            // show rectangle with the real vertical bounds, based on the font bounding box y values
            // usually, the height is identical to what you see when marking text in Adobe Reader
            PDFont font = text.getFont();
            BoundingBox bbox = font.getBoundingBox();

            // advance width, bbox height (glyph space)
            float xadvance = font.getWidth(text.getCharacterCodes()[0]); // todo: should iterate all chars
            rect = new Rectangle2D.Float(0, bbox.getLowerLeftY(), xadvance, bbox.getHeight());
            
            // glyph space -> user space
            // note: text.getTextMatrix() is *not* the Text Matrix, it's the Text Rendering Matrix
            AffineTransform at = text.getTextMatrix().createAffineTransform();
            if (font instanceof PDType3Font)
            {
                // bbox and font matrix are unscaled
                at.concatenate(font.getFontMatrix().createAffineTransform());
            }
            else
            {
                // bbox and font matrix are already scaled to 1000
                at.scale(1/1000f, 1/1000f);
            }
            Shape s = at.createTransformedShape(rect);

            s = flipAT.createTransformedShape(s);
            s = rotateAT.createTransformedShape(s);

            g2d.setColor(Color.blue);
            g2d.draw(s);*/
        }
    }
    
    @Override
    protected void processTextPosition( TextPosition text )
    {
//        Iterator<String> regionIter = regionArea.keySet().iterator();
//        while( regionIter.hasNext() )
//        {
//            String region = regionIter.next();
//            Rectangle2D rect = regionArea.get( region );
//            if( rect.contains( text.getX(), text.getY() ) )
//            {
//                charactersByArticle = regionCharacterList.get( region );
//                super.processTextPosition( text );
//            }
//        }
    }
    
    
    public static void main(String[] args) throws IOException {
    	 PDDocument document = null;
         try
         {
             document = PDDocument.load(new File("/Users/wangjj/Downloads/report/tmp/one.pdf"));

             TextPosLocation stripper = new TextPosLocation(document);
             stripper.setSortByPosition(true);

             for (int page = 0; page < document.getNumberOfPages(); ++page)
             {
                 stripper.stripPage(page);
             }
         }
         finally
         {
             if (document != null)
             {
                 document.close();
             }
         }
	}

}
