package wacai.layout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;

public class TableCellLayout {
	private final int tolerance = 3;
	
	private void sortByX(ArrayList<RectangleStruct> list){
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

	private void sortByY(ArrayList<RectangleStruct> list){
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

	private void intersectionSortByX(ArrayList<Intersection> list){
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

	private void mergeVerticalLine(ArrayList<RectangleStruct> fillrects, ArrayList<RectangleStruct> vrects){
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
//						System.out.println(lx+"="+rx+"="+elem.getX());
//						System.out.println(y1+"="+y2+"="+elem.getY()+"="+elem.getBottomY());
//						System.out.println("==================");
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

	private void mergeHorizonLine(ArrayList<RectangleStruct> fillrects, ArrayList<RectangleStruct> hrects){
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
					//System.out.println("--------------");
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

	private void filterHorizonLine(ArrayList<RectangleStruct> hrects) {
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

	private void filterVerticalLine(ArrayList<RectangleStruct> vrects) {
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

	private ArrayList<Intersection> getLineIntersection(ArrayList<RectangleStruct> hrects, ArrayList<RectangleStruct> vrects) {
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

	private Intersection getIntersection(RectangleStruct hline, RectangleStruct vline) {
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

	private List<Map.Entry<Integer, ArrayList<Intersection>>> removeAloneNode(ArrayList<Intersection> nodelist) {
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
		}
		return list;
	}
	//getTableCells
	private ArrayList<RectangleStruct> getTableCells(List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist){
		ArrayList<RectangleStruct> cellList = new ArrayList<RectangleStruct>();
		int len = nodelist.size();
		for (int i = 0; i < len; i++) {
			ArrayList<Intersection> rownodes1 = nodelist.get(i).getValue();
			int len1 = rownodes1.size();
			ArrayList<RectangleStruct> rowlist = this.getRowCell(i, rownodes1, len1, nodelist, len);
			cellList.addAll(rowlist);
		}
		//System.out.println("nrect = " + cellList.size());
		return cellList;
	}

	private ArrayList<RectangleStruct> getRowCell(int row, ArrayList<Intersection> rownodes1, int len1, List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist, int len) {
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
	
	//获取整个页面的单元格坐标列表
	public ArrayList<RectangleStruct> getTableCellLayout(String path) {
		File file = new File(path);
		PDDocument doc = null;
		DocPdfRenderer renderer = null;
		try {
			doc = PDDocument.load(file);
			renderer = new DocPdfRenderer(doc);
			renderer.renderImage(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (renderer == null) {
			return new ArrayList<RectangleStruct>();
		}
		ArrayList<RectangleStruct> fillrect = renderer.getFillpathRect();
		ArrayList<RectangleStruct> hrect = renderer.getHlineRect();
		ArrayList<RectangleStruct> vrect = renderer.getVlineRect();
		this.mergeVerticalLine(fillrect, vrect);
		this.mergeHorizonLine(fillrect, hrect);
		this.filterHorizonLine(hrect);
		this.filterVerticalLine(vrect);
		ArrayList<Intersection> nodes = this.getLineIntersection(hrect, vrect);
        List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist = this.removeAloneNode(nodes);
        ArrayList<RectangleStruct> cellList = this.getTableCells(nodelist);
		return cellList;
	}
	
	public ArrayList<RectangleStruct> getTableCellLayout(PDDocument doc) {
		
		DocPdfRenderer renderer = null;
		try {
			renderer = new DocPdfRenderer(doc);
			renderer.renderImage(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (renderer == null) {
			return new ArrayList<RectangleStruct>();
		}
		ArrayList<RectangleStruct> fillrect = renderer.getFillpathRect();
		ArrayList<RectangleStruct> hrect = renderer.getHlineRect();
		ArrayList<RectangleStruct> vrect = renderer.getVlineRect();
		this.mergeVerticalLine(fillrect, vrect);
		this.mergeHorizonLine(fillrect, hrect);
		this.filterHorizonLine(hrect);
		this.filterVerticalLine(vrect);
		ArrayList<Intersection> nodes = this.getLineIntersection(hrect, vrect);
        List<Map.Entry<Integer, ArrayList<Intersection>>> nodelist = this.removeAloneNode(nodes);
        ArrayList<RectangleStruct> cellList = this.getTableCells(nodelist);
		return cellList;
	}

}
