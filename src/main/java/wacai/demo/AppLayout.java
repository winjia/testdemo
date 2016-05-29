package wacai.demo;

//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.rendering.PDFRenderer;




import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;

import wacai.layout.DocPdfRenderer;
import wacai.layout.Intersection;
import wacai.layout.RectangleStruct;

public class AppLayout {
	
	private final int tolerance = 3;
	
	
	private static void sortByX(ArrayList<RectangleStruct> list){
		Collections.sort(list, new Comparator<RectangleStruct>() {
	        public int compare(RectangleStruct arg0, RectangleStruct arg1) {
	        	float tmp = arg1.getX() - arg0.getX();
	        	if (tmp > 0) {
					return -1;
				} else if (tmp < 0) {
					return 1;
				} else {
					return 0;
				}
	        }
	    });
	}
	
	
	private static void sortByY(ArrayList<RectangleStruct> list){
		Collections.sort(list, new Comparator<RectangleStruct>() {
	        public int compare(RectangleStruct arg0, RectangleStruct arg1) {
	        	float tmp = arg1.getY() - arg0.getY();
	        	if (tmp > 0) {
					return -1;
				} else if (tmp < 0) {
					return 1;
				} else {
					return 0;
				}
	        }
	    });
	}
	
	private static void intersectionSortByX(ArrayList<Intersection> list){
		Collections.sort(list, new Comparator<Intersection>() {
	        public int compare(Intersection arg0, Intersection arg1) {
	        	float tmp = arg1.getX() - arg0.getX();
	        	if (tmp > 0) {
					return -1;
				} else if (tmp < 0) {
					return 1;
				} else {
					return 0;
				}
	        }
	    });
	}
	
	private static void mergeVerticalLine(ArrayList<RectangleStruct> fillrects, ArrayList<RectangleStruct> vrects){
		int len1 = fillrects.size();
		int len2 = vrects.size();
		ArrayList<RectangleStruct> tmpList = new ArrayList<RectangleStruct>();
		for (int i = 0; i < len1; i++) {
			int lx = fillrects.get(i).getX();
			int rx = lx + fillrects.get(i).getWidth() - 1;
			int y1 = fillrects.get(i).getY();
			int y2 = fillrects.get(i).getTopY();
			boolean flg = false;
			for (int j = 0; j < len2; j++) {
				RectangleStruct elem = vrects.get(j);
				if (Math.abs(lx-elem.getX())>2 && Math.abs(rx-elem.getX())>2) {
					continue;
				}
				if (elem.getY()>=y1 && Math.abs(elem.getY()-2)<=y2) {
					elem.setY(y1);
					int len = (elem.getY()<=y2)?y2:(elem.getTopY()-y1+1);
					elem.setHeight(elem.getTopY()-y1+1);
					flg = true;
				} else if (Math.abs(y1-elem.getTopY())<=2) {
					elem.setHeight(y2-elem.getY()+1);
					flg = true;
				} else if((elem.getHeight()) > (y2-y1+1)){
					flg  = true;
				} else {
//					System.out.println(lx+"="+rx+"="+elem.getX());
//					System.out.println(y1+"="+y2+"="+elem.getY()+"="+elem.getBottomY());
//					System.out.println("==================");
				}
			}
			if (!flg) {
				int x = fillrects.get(i).getX();
				int y = fillrects.get(i).getY();
				int width = 0;
				int height = fillrects.get(i).getHeight();
				tmpList.add(new RectangleStruct(x, y, width, height));
			}
		}
		vrects.addAll(tmpList);
	}
	
	private static void mergeHorizonLine(ArrayList<RectangleStruct> fillrects, ArrayList<RectangleStruct> hrects){
		int len1 = fillrects.size();
		int len2 = hrects.size();
		ArrayList<RectangleStruct> tmpList = new ArrayList<RectangleStruct>();
		for (int i = 0; i < len1; i++) {
			int lx = fillrects.get(i).getX();
			int rx = lx + fillrects.get(i).getWidth() - 1;
			int y1 = fillrects.get(i).getY();
			int y2 = y1 + fillrects.get(i).getHeight() - 1;
			boolean flg = false;
			for (int j = 0; j < len2; j++) {
				RectangleStruct elem = hrects.get(j);
				if (Math.abs(y1-elem.getY())>2 && Math.abs(y2-elem.getY())>2) {
					continue;
				}
				if (elem.getX()>=lx && Math.abs(elem.getX()-2)<=rx) {
					elem.setX(lx);
					//int len = (elem.getX()>rx)?(elem.getRightX()-lx+1):;
					elem.setWidth(elem.getRightX()-lx+1);
					flg = true;
				} else if (Math.abs(lx-elem.getRightX())<=2) {
					elem.setWidth(rx-elem.getX()+1);
					flg = true;
				} else {
					flg = true;
					System.out.println("--------------");
				}
			}
			if (!flg) {
				int x = fillrects.get(i).getX();
				int y = fillrects.get(i).getY();
				int width = fillrects.get(i).getWidth();
				int height = 0;
				tmpList.add(new RectangleStruct(x, y, width, height));
				//System.out.println(fillrects.get(i).getHeight());
			}
		}
		hrects.addAll(tmpList);
	}
	
	public static void filterHorizonLine(ArrayList<RectangleStruct> hrects) {
		int len = hrects.size();
		ArrayList<RectangleStruct> delList = new ArrayList<RectangleStruct>();
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (i>=j) {
					continue;
				}
				int dist = Math.abs(hrects.get(i).getY()-hrects.get(j).getY());
				if (dist >= 2) {
					continue;
				}
				if (hrects.get(i).getWidth() < hrects.get(j).getWidth()) {
					//System.out.println("dist = " + dist);
					delList.add(hrects.get(i));
				} else {
					delList.add(hrects.get(j));
				}
			}
		}
		for (RectangleStruct elem1: delList) {
			for (RectangleStruct elem2: hrects) {
				if (elem1.equals(elem2)) {
					hrects.remove(elem2);
					break;
				}
			}
		}
	}
	
	public static void filterVerticalLine(ArrayList<RectangleStruct> vrects) {
		int len = vrects.size();
		ArrayList<RectangleStruct> delList = new ArrayList<RectangleStruct>();
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (i>=j) {
					continue;
				}
				int dist = Math.abs(vrects.get(i).getX()-vrects.get(j).getX());
				if (dist >= 2) {
					continue;
				}
				if (vrects.get(i).getHeight() < vrects.get(j).getHeight()) {
					//System.out.println("dist = " + dist);
					delList.add(vrects.get(i));
				} else {
					delList.add(vrects.get(j));
				}
			}
		}
		for (RectangleStruct elem1: delList) {
			for (RectangleStruct elem2: vrects) {
				if (elem1.equals(elem2)) {
					vrects.remove(elem2);
					break;
				}
			}
		}
	}
	
	public static ArrayList<Intersection> getLineIntersection(ArrayList<RectangleStruct> hrects, ArrayList<RectangleStruct> vrects) {
		sortByX(vrects);
		sortByY(hrects);
		int hlen = hrects.size();
		int vlen = vrects.size();
		ArrayList<Intersection> nodeList = new ArrayList<Intersection>();
		for (int i = 0; i < hlen; i++) {
			for (int j = 0; j < vlen; j++) {
				Intersection node = getIntersection(hrects.get(i), vrects.get(j));
				if (node == null) {
					continue;
				}
				nodeList.add(node);
			}
		}
		return nodeList;
	}
	
	public static Intersection getIntersection(RectangleStruct hline, RectangleStruct vline) {
		int hlx = hline.getX();
		int hrx = hline.getRightX();
		int hy = hline.getY();
		int vx = vline.getX();
		int vty = vline.getY();
		int vby = vline.getTopY();
		boolean hflg = false;
		boolean vflg = false;
		if (hlx<=vx && vx<=hrx) {
			hflg = true;
		}
		if (vty<=hy && hy<=vby) {
			vflg = true;
		}
		if (hflg && vflg) {
			return new Intersection(vx, hy);
		}
		return null;
	}
	
	public static List<Map.Entry<Integer, ArrayList<Intersection>>> removeAloneNode(ArrayList<Intersection> nodelist) {
		HashMap<Integer, ArrayList<Intersection>> nodeColMap = new HashMap<Integer, ArrayList<Intersection>>();
		for (Intersection elem: nodelist) {
			int x = elem.getX();
			if (nodeColMap.containsKey(x)) {
				ArrayList<Intersection> value = nodeColMap.get(x);
				value.add(elem);
				nodeColMap.put(x, value);
			} else {
				ArrayList<Intersection> value = new ArrayList<Intersection>();
				value.add(elem);
				nodeColMap.put(x, value);
			}
		}
		HashMap<Integer, ArrayList<Intersection>> nodeRowMap = new HashMap<Integer, ArrayList<Intersection>>();
		for (Map.Entry<Integer, ArrayList<Intersection>> elem: nodeColMap.entrySet()) {
			int n = elem.getValue().size();
			if (n == 1) {
				continue;
			}
			ArrayList<Intersection> value = elem.getValue();
			for (Intersection elem2 : value) {
				int y = elem2.getY();
				if (nodeRowMap.containsKey(y)) {
					ArrayList<Intersection> value2 = nodeRowMap.get(y);
					value2.add(elem2);
					nodeRowMap.put(y, value2);
				} else {
					ArrayList<Intersection> value2 = new ArrayList<Intersection>();
					value2.add(elem2);
					nodeRowMap.put(y, value2);
				}
			}
		}
		ArrayList<Integer> delKeyList = new ArrayList<Integer>();
		for (Map.Entry<Integer, ArrayList<Intersection>> elem : nodeRowMap.entrySet()) {
			if (elem.getValue().size() == 1) {
				delKeyList.add(elem.getKey());
			}
		}
		for (Integer elem : delKeyList) {
			nodeRowMap.remove(elem);
		}
		List<Map.Entry<Integer, ArrayList<Intersection>>> list = new ArrayList<Map.Entry<Integer,ArrayList<Intersection>>>(nodeRowMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, ArrayList<Intersection>>>() {
			public int compare(Map.Entry<Integer, ArrayList<Intersection>> o1, Map.Entry<Integer, ArrayList<Intersection>> o2){
				return (o2.getKey().intValue() - o1.getKey().intValue());
			}
		});
		int len = list.size();
		for (int i = 0; i < len; i++) {
			ArrayList<Intersection> value = list.get(i).getValue();
			intersectionSortByX(value);
//			for (int j = 0; j < value.size(); j++) {
//				System.out.print(value.get(j).getX() + " ");
//			}
//			System.out.println("-=-=-=-=-=-=--==-");
		}
//		System.out.println("====================");
//		for (int i = 0; i < len; i++) {
//			System.out.println(list.get(i).getKey().intValue());
//			int lnn = list.get(i).getValue().size();
//			ArrayList<Intersection> ls = list.get(i).getValue();
//			for (int j = 0; j < lnn; j++) {
//				System.out.println(ls.get(j).getX());
//			}
//			//System.out.println(list.get(i).getValue().size());
//			System.out.println("---");
//		}
		return list;
	}
	//getTableCells
	public static ArrayList<RectangleStruct> getTableCells(List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist){
		ArrayList<RectangleStruct> cellList = new ArrayList<RectangleStruct>();
		int len = nodelist.size();
		for (int i = 0; i < len; i++) {
			ArrayList<Intersection> rownodes1 = nodelist.get(i).getValue();
			int len1 = rownodes1.size();
			ArrayList<RectangleStruct> rowlist = getRowCell(i, rownodes1, len1, nodelist, len);
			cellList.addAll(rowlist);
		}
		System.out.println("nrect = " + cellList.size());
		return cellList;
	}
	
	public static ArrayList<RectangleStruct> getRowCell(int row, ArrayList<Intersection> rownodes1, int len1, List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist, int len) {
		ArrayList<RectangleStruct> rowcelllist = new ArrayList<RectangleStruct>();
		for (int i = 0; i < len1-1; i++) {
			//boolean flag = false;
			for (int j = i+1; j < len1; j++) {
				int lx = rownodes1.get(i).getX();
				int rx = rownodes1.get(j).getX();
				int ty = rownodes1.get(i).getY();
				int by = 0;
				int w = rx - lx + 1;
				RectangleStruct rect = new RectangleStruct(rownodes1.get(i).getX(), rownodes1.get(i).getY(), w, 0);
				for (int k = row+1; k < len; k++) {
					boolean flg = false;
					boolean lflg = false;
					boolean rflg = false;
					int lidx = 0;
					ArrayList<Intersection> rownodes2 = nodelist.get(k).getValue();
					int len2 = rownodes2.size();
					for (int l = 0; l < len2; l++) {
						if (lx == rownodes2.get(l).getX()) {
							lidx = l;
							lflg = true;
						}else if (rx == rownodes2.get(l).getX()) {
							rflg = true;
						}
						if (rflg && lflg) {
							flg = true;
							by = rownodes2.get(lidx).getY();
							break;
						}
					}
					if (flg) {
						break;
					}
				}
				if (by != 0) {
					rect.setHeight(by-ty+1);
					//return rect;
					rowcelllist.add(rect);
					break;
				}
			}
		}
		return rowcelllist;
	}

	public static void main(String[] args) throws IOException {
		File file = new File("/Users/wangjj/Downloads/report/1/2.pdf");
		PDDocument doc = PDDocument.load(file);
		DocPdfRenderer renderer = new DocPdfRenderer(doc);
		renderer.renderImage(0);
		ArrayList<RectangleStruct> fillrect = renderer.getFillpathRect();
		ArrayList<RectangleStruct> hrect = renderer.getHlineRect();
		ArrayList<RectangleStruct> vrect = renderer.getVlineRect();
		System.out.println(fillrect.size());
		System.out.println(hrect.size());
		System.out.println(vrect.size());
		//
		//AppLayout.sortByX(vrect);
		//AppLayout.sortByY(hrect);
		System.out.println("hrect1: " + hrect.size());
		System.out.println("vrect1: " + vrect.size());
		AppLayout.mergeVerticalLine(fillrect, vrect);
		AppLayout.mergeHorizonLine(fillrect, hrect);
		//AppLayout.filterHorizonLine(hrect);
		//AppLayout.filterVerticalLine(vrect);
		System.out.println("hrect2: " + hrect.size());
		System.out.println("vrect2: " + vrect.size());
		//for (RectangleStruct elem : fillrect) {
		//	System.out.println(elem.getX()+"="+elem.getY()+"="+elem.getHeight()+"="+elem.getWidth());
		//}
		
		//
		PDDocument doc1= new PDDocument();
        try
        {
            // a valid PDF document requires at least one page
            PDPage blankPage = new PDPage();
            PDPageTree allPages = doc.getDocumentCatalog().getPages();
            PDPage page1 = doc.getPage(0);
            //COSDictionary pageDict = page1.getCOSDictionary();
            doc1.addPage(blankPage);
            PDPageContentStream contentStream = new PDPageContentStream(doc1, blankPage);
            contentStream.setStrokingColor(66, 177, 230);
            for (int i = 0; i < hrect.size(); i++) {
            	contentStream.drawLine(hrect.get(i).getX(), hrect.get(i).getTopY(), hrect.get(i).getRightX(), hrect.get(i).getY());
            	System.out.println(hrect.get(i).getTopY());
            	//System.out.println(hrect.get(i).getY() + "===" + hrect.get(i).getBottomY());
			}
            for (int i = 0; i < vrect.size(); i++) {
            	contentStream.drawLine(vrect.get(i).getX(), vrect.get(i).getTopY(), vrect.get(i).getRightX(), vrect.get(i).getY());
            	//contentStream.drawLine(vrect.get(i).getX(), vrect.get(i).getY(), vrect.get(i).getRightX(), vrect.get(i).getTopY());
            	//System.out.println(vrect.get(i).getY() + "===" + vrect.get(i).getTopY());
			}
            //
            ArrayList<Intersection> nodes = AppLayout.getLineIntersection(hrect, vrect);
            List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist = AppLayout.removeAloneNode(nodes);
            ArrayList<RectangleStruct> cellList = AppLayout.getTableCells(nodelist);
            contentStream.setStrokingColor(255, 0, 0);
            int count = 0;
            System.out.println(nodelist.size());
//            for (int i = 0; i < nodelist.size(); i++) {
//            	
//            	ArrayList<Intersection> value = nodelist.get(i).getValue();
//            	count += value.size();
//            	for (Intersection elem : value) {
//            		//contentStream.drawLine(nodes.get(i).getX(), nodes.get(i).getY(), nodes.get(i).getX(), nodes.get(i).getY()+1);
//            		contentStream.drawLine(elem.getX(), elem.getY(), elem.getX(), elem.getY()+1);
//				}
//			}
            //for (int i = 0; i < 35; i++) {
//            for (int i = 0; i < cellList.size(); i++) {
//				int x1 = cellList.get(i).getX();
//				int y1 = cellList.get(i).getY();
//				int x2 = cellList.get(i).getRightX();
//				int y2 = cellList.get(i).getTopY();
//				contentStream.drawLine(x1, y1, x2, y2);
//				//System.out.println("("+x1+","+y1+"),("+x2+","+y2+")");
//			}
            System.out.println("size = " + count);
            //
            contentStream.close();
            doc1.save("/Users/wangjj/Downloads/report/lines.pdf");
        }
        finally
        {
            doc.close();
        }
		
	}

}


/*
PDDocument doc = PDDocument.load( file );
List<PDPage> allPages = doc.getDocumentCatalog().getAllPages();
PDPage page = allPages.get(0);
COSDictionary pageDict = page.getCOSDictionary();
COSDictionary newPageDict = pageDict; new COSDictionary(pageDict);
newPageDict.removeItem(COSName.ANNOTS);
PDPage newPage = new PDPage(newPageDict);
doc.addPage(newPage);
doc.save( outfile );
 */

