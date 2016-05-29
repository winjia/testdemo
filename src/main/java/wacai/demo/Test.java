package wacai.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import wacai.layout.CharElem;

public class Test {

	
	public static void sortCharByXY(ArrayList<CharElem> list){
	    Collections.sort(list, new Comparator<CharElem>() {
	        public int compare(CharElem arg0, CharElem arg1) {
	            float tmp1 = arg1.getX() - arg0.getX();
	            float tmp2  = arg1.getY() - arg0.getY();
	            if (Math.abs(tmp2)<=3 && tmp1>0) {
	            	return -1;
				} else if (Math.abs(tmp2)<=3 && tmp1<0) {
					return 1;
				} else if (tmp2 > 3) {
					return 1;
				} else if (tmp2 < -3) {
					return -1;
				}
	            return 0;
	        }
	    });
	}
	
	public static void main(String[] args) {
//		ArrayList<CharElem> list = new ArrayList<CharElem>();
//		list.add(new CharElem("A", 23,265));
//		list.add(new CharElem("B", 203,263));
//		list.add(new CharElem("C", 89,266));
//		list.add(new CharElem("D", 103,266));
//		Test.sortCharByXY(list);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getAchar());
//		}

		String str = "大（1）：:你哈";
		str = str.replaceAll("[\\pP\\pN‘’“”]", "");
		System.out.println(str);
		str = "销售点地址";
		String str1 = "销售点";
		if (str.contains(str1)) {
			System.out.println("==============");
		}
	}

}
